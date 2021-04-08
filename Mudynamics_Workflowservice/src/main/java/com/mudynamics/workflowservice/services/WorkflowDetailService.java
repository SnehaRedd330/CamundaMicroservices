package com.mudynamics.workflowservice.services;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mudynamics.workflowservice.dto.AllDetailsDTO;
import com.mudynamics.workflowservice.dto.CamundaServerRequest;
import com.mudynamics.workflowservice.dto.CamundaServerRequestDTO;
import com.mudynamics.workflowservice.dto.CamundaServerStatusDTO;
import com.mudynamics.workflowservice.exceptions.DuplicateWorkflowNameWithVersionException;
import com.mudynamics.workflowservice.exceptions.RunningProcessInstanceAvailableException;
import com.mudynamics.workflowservice.exceptions.WorkflowsNotFoundException;
import com.mudynamics.workflowservice.dto.DashboardDataDTO;
import com.mudynamics.workflowservice.models.DeploymentDescriptor;
import com.mudynamics.workflowservice.models.ProcessDuplicateCheck;
import com.mudynamics.workflowservice.models.UrlDetails;
import com.mudynamics.workflowservice.models.WorkflowDetails;
import com.mudynamics.workflowservice.models.WorkflowMeatadataDetails;
import com.mudynamics.workflowservice.repositories.DashboardProcessData;
import com.mudynamics.workflowservice.repositories.UrlDetailsRepository;
import com.mudynamics.workflowservice.repositories.WorkflowDetailsPaginationRepository;
import com.mudynamics.workflowservice.repositories.WorkflowMetadataDetailsRepository;



@Service
public class WorkflowDetailService {
	
	
	@Autowired
	private WorkflowMetadataDetailsRepository metadataDetailsRepository;
	
	@Autowired
	private WorkflowDetailsPaginationRepository workflowDetailsPaginationRepository;
	
	@Autowired
	private UrlDetailsRepository urlDetailsRepository;
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${camunda.engine}")
	private String camundaEngine;
	
	private static Logger logger = Logger.getLogger(WorkflowDetailService.class.getName());
	
	
	public ProcessDuplicateCheck checkIfNameExists(String name){
		ProcessDuplicateCheck check = new ProcessDuplicateCheck();
			//WorkflowMeatadataDetails meatadataDetails =metadataDetailsRepository.findByProcessDefinitionKey(name);
		WorkflowMeatadataDetails meatadataDetails =metadataDetailsRepository.findByProcessName(name);
			if(meatadataDetails!= null)	
			check.setProcessNameExists(Boolean.TRUE);
			
			return check;
		
			}

	

	public Page<WorkflowMeatadataDetails> getAllWorkflowDetails(Integer pageNo,Integer pageSize, String processCategory){
		Pageable paging = PageRequest.of(pageNo, pageSize);
		//return metadataDetailsRepository.findByProcessCategoryIn(Arrays.asList(processCategory));
		 return workflowDetailsPaginationRepository.findAllByProcessCategory(paging,processCategory);
		
	}


	public Map<String,List<AllDetailsDTO>> refreshAndGetAllProcess(String url) {
		UrlDetails urlDetail=checkCamundaServerEnvironmentsAvailability();
		checkConnectivity(urlDetail.getUrl(),new CamundaServerRequestDTO(urlDetail.getUsername(),urlDetail.getPassword()));
		final String  uri=url+"/"+camundaEngine+"/process-definition";
		  ResponseEntity<WorkflowDetails[]> response=connectToUrl(uri,HttpMethod.GET,WorkflowDetails[].class,urlDetail);
		
		  List<WorkflowDetails> deployedProcessDetails= Arrays.asList(response.getBody());
			 JSONParser bpmnXmlparse=new JSONParser();
			 
			deployedProcessDetails.stream().forEach(process->{
		    	              if(metadataDetailsRepository.existsByProcessDefinitionId(process.getId())==Boolean.FALSE)
					        {
				    	    	         WorkflowMeatadataDetails workflowDetails=new  WorkflowMeatadataDetails();
				    	    	         workflowDetails.setProcessDefinitionId(process.getId());
				    	    	         workflowDetails.setProcessName(process.getName());
				    	    	         workflowDetails.setProcessDefinitionKey(process.getKey());
				    	    	         workflowDetails.setProcessStatus("deployed");
				    	    	         workflowDetails.setDeploymentId(process.getDeploymentId());
				    	    	         workflowDetails.setVersion(process.getVersion());
				    	    	         workflowDetails.setProcessCategory("Development");
							         final String  bpmnXmlurl=url+"/"+camundaEngine+"/process-definition/"+process.getId()+"/xml";
				 			     try {
				 			    	 String bpmnXmlResult=restTemplate.getForObject(bpmnXmlurl,String.class);
				 			    	JSONObject bpmnXmlobject = (JSONObject) bpmnXmlparse.parse(bpmnXmlResult);
									     // String xml=.toString(); 
									      workflowDetails.setXmldata(bpmnXmlobject.get("bpmn20Xml").toString());
							     
									      final String deploymentTimeurl=url+"/"+camundaEngine+"/deployment/"+process.getDeploymentId();
				                 String deploymentResult=  restTemplate.getForObject(deploymentTimeurl,String.class);
				             
				                JSONObject deploymentObject = (JSONObject) bpmnXmlparse.parse(deploymentResult);
								  String deploymentTime=deploymentObject.get("deploymentTime").toString();
								     workflowDetails.setDeploymentTime(deploymentTime);
							   }catch (Exception e){
								if(e instanceof ParseException){
									logger.log(Level.SEVERE,"Process Parse Exception...",e);
									throw new RuntimeException("Process is unable to parse..");
								}
								else if(e instanceof ResourceAccessException){
									logger.log(Level.SEVERE,"Connection refused to connect..",e);
									throw new RuntimeException("Connection refused to connect to "+url);
								}
								else {
									throw new RuntimeException("something went wrong in refreshing deployed process...");
								}
								}
								metadataDetailsRepository.saveAndFlush(workflowDetails);
					}
    	   });
			
		if(metadataDetailsRepository.findAllByProcessStatus("deployed").get().size()!=deployedProcessDetails.size()){
			metadataDetailsRepository.findAllByProcessStatus("deployed").get().stream()
			.forEach(process-> {
				boolean flag=Boolean.FALSE;
				for(WorkflowDetails details:deployedProcessDetails){
					if(details.getId().equals(process.getProcessDefinitionId()))
						flag=Boolean.TRUE;
				}
				if(flag==Boolean.FALSE)
					metadataDetailsRepository.deleteById(process.getIdentify());
			});
			}
		return metadataDetailsRepository.findAll().stream()
					.map(process -> mapper.map(process, AllDetailsDTO.class))
					.collect(Collectors.groupingBy(AllDetailsDTO::getProcessDefinitionKey));
	}

	
	public AllDetailsDTO postDeployment(DeploymentDescriptor descriptor,String userId,JsonObject deployedProcessResponse){
		AllDetailsDTO detailsDTO=new AllDetailsDTO();
		AllDetailsDTO allDetailsDTO=new AllDetailsDTO();
		JsonObject workflowDetails =null;
		if((deployedProcessResponse instanceof JsonObject) && (!deployedProcessResponse.get("deployedProcessDefinitions").isJsonNull())){
			 workflowDetails=deployedProcessResponse.get("deployedProcessDefinitions").getAsJsonObject();
			}
		
		WorkflowMeatadataDetails workflowMeatadataDetails=metadataDetailsRepository.findByProcessDefinitionKeyAndVersionAndProcessStatus(descriptor.getProcessDefKey(),descriptor.getVersion(),descriptor.getStatus()).orElseThrow(()-> new WorkflowsNotFoundException("workflow name: "+descriptor.getProcessDefKey()+" with version: "+descriptor.getVersion()+" not found in database.."));
		
		
		if(workflowDetails != null){
			detailsDTO =mapper.map(workflowMeatadataDetails,AllDetailsDTO.class);
			for (Map.Entry<String, JsonElement> entry : workflowDetails.entrySet()) {
				String  defId = entry.getKey();
				JsonObject details=entry.getValue().getAsJsonObject();
				detailsDTO.setDeploymentId(details.get("deploymentId").getAsString());
				if(!details.get("historyTimeToLive").isJsonNull())
					detailsDTO.setHistoryTimeToLive(details.get("historyTimeToLive").getAsLong());
				if(!details.get("tenantId").isJsonNull())
					detailsDTO.setTenantId(details.get("tenantId").getAsString());
				
				detailsDTO.setProcessDefinitionId(defId);
				detailsDTO.setProcessDefinitionKey(details.get("key").getAsString());
				detailsDTO.setProcessName(details.get("name").getAsString());
				detailsDTO.setVersion(details.get("version").getAsLong());
			}
			detailsDTO.setDeploymentTime(deployedProcessResponse.get("deploymentTime").toString());
			detailsDTO.setLastModifiedBy(userId);
			detailsDTO.setLastModifiedTimeStamp(new SimpleDateFormat("dd-MM-yy hh.mm aa").format(new Date()));
			detailsDTO.setProcessStatus("deployed");
			detailsDTO.setXmldata(descriptor.getXmldata());
			detailsDTO.setSvgDiagramData(descriptor.getSvgImage());
			detailsDTO.setVisibilityFlag(null);
			detailsDTO.setLockedBy(null);
			detailsDTO.setLockedTimeStamp(null);
			WorkflowMeatadataDetails metadataDetails=metadataDetailsRepository.save(mapper.map(detailsDTO,WorkflowMeatadataDetails.class));
			 allDetailsDTO=mapper.map(metadataDetails, AllDetailsDTO.class);
			
			
		}else
			metadataDetailsRepository.deleteById(workflowMeatadataDetails.getIdentify());
		
		
		return allDetailsDTO;
		}
	

	@Transactional
	public synchronized AllDetailsDTO lockEditableWorkflow(String processid, String userId, String status, long version) {
		
		
		WorkflowMeatadataDetails meatadataDetails=metadataDetailsRepository.findByProcessDefinitionKeyAndVersionAndProcessStatus(processid,version,status).orElseThrow(()-> new WorkflowsNotFoundException(processid +" not found in database.."));
		
		if(("saved".equals(meatadataDetails.getProcessStatus()) || "deploy-change".equals(meatadataDetails.getProcessStatus())) && meatadataDetails.getVisibilityFlag()==Boolean.TRUE){
			logger.info(processid+" ready to lock by "+ userId);
			meatadataDetails.setVisibilityFlag(Boolean.FALSE);
			meatadataDetails.setLockedBy(userId);
			meatadataDetails.setLockedTimeStamp(new SimpleDateFormat("dd-MM-yy hh.mm aa").format(new Date()));
		}
		
		WorkflowMeatadataDetails details=metadataDetailsRepository.save(meatadataDetails);
		
		 return mapper.map(details, AllDetailsDTO.class);
		
		}
	


	@Transactional
	public synchronized AllDetailsDTO unlockWorkflow(String processid,String userId, String status,long version){
		
		
			WorkflowMeatadataDetails meatadataDetails=metadataDetailsRepository.findByProcessDefinitionKeyAndVersionAndProcessStatus(processid,version,status).orElseThrow(()-> new WorkflowsNotFoundException(processid +" not found in database.."));
		
		if(("saved".equals(meatadataDetails.getProcessStatus())  || "deploy-change".equals(meatadataDetails.getProcessStatus())) && meatadataDetails.getVisibilityFlag()==Boolean.FALSE && userId.equals(meatadataDetails.getLockedBy())){
			logger.info(processid+" ready to unlock by "+ userId);
			meatadataDetails.setVisibilityFlag(Boolean.TRUE);
			meatadataDetails.setLockedBy(null);
			meatadataDetails.setLockedTimeStamp(null);
		}
		
			WorkflowMeatadataDetails details=metadataDetailsRepository.save(meatadataDetails);
		
		 return mapper.map(details, AllDetailsDTO.class);
		}
		

	

	public AllDetailsDTO getProcessBynameAndVersion(String processid, long version, String status) {
		if(metadataDetailsRepository.existsByProcessDefinitionKeyAndVersionAndProcessStatus(processid, version, status)){
			WorkflowMeatadataDetails details=metadataDetailsRepository.findByProcessDefinitionKeyAndVersionAndProcessStatus(processid,version,status).orElseThrow(()-> new WorkflowsNotFoundException(processid +" not found in database.."));
			return mapper.map(details, AllDetailsDTO.class);
		}
		else throw new WorkflowsNotFoundException("Workflow name: "+processid+" with version:"+version+" not fuond..");
		
	}

	@Transactional
	public AllDetailsDTO createNewProcess(String userid, @Valid AllDetailsDTO detailsDTO) {
		WorkflowMeatadataDetails details=metadataDetailsRepository.findByProcessDefinitionKey(detailsDTO.getProcessDefinitionKey());
		if(details != null)	
		new DuplicateWorkflowNameWithVersionException("Workflow Details for Process Definition key: "+detailsDTO.getProcessDefinitionKey()+" already in database..");
		
		
		WorkflowMeatadataDetails meatadataDetails =mapper.map(detailsDTO, WorkflowMeatadataDetails.class);
		WorkflowMeatadataDetails workflowMeatadataDetails=metadataDetailsRepository.save(meatadataDetails);
		
		return mapper.map(workflowMeatadataDetails, AllDetailsDTO.class);
	}
	


	public AllDetailsDTO saveExistingProcess(String userid, @Valid AllDetailsDTO allDetailsDTO) {
		
		WorkflowMeatadataDetails meatadataDetails =metadataDetailsRepository.findByProcessDefinitionKeyAndProcessStatus(allDetailsDTO.getProcessDefinitionKey(),allDetailsDTO.getProcessStatus()).orElseThrow(()-> new WorkflowsNotFoundException("Workflow Details for Process Definition key: "+allDetailsDTO.getProcessDefinitionKey()+" with status: "+allDetailsDTO.getProcessStatus()+"not found in database.."));
		meatadataDetails.setLastModifiedBy(userid);
		meatadataDetails.setLastModifiedTimeStamp(new SimpleDateFormat("dd-MM-yy hh.mm aa").format(new Date()));
		meatadataDetails.setProcessName(allDetailsDTO.getProcessName());
		
		if(allDetailsDTO.getVisibilityFlag()!=Boolean.FALSE)
		meatadataDetails.setVisibilityFlag(Boolean.TRUE);
		meatadataDetails.setVersion(0);
		meatadataDetails.setProcessStatus("saved");
		meatadataDetails.setSvgDiagramData(allDetailsDTO.getSvgDiagramData().toString());
		meatadataDetails.setXmldata(allDetailsDTO.getXmldata());
		AllDetailsDTO detailsDTO =mapper.map(metadataDetailsRepository.save(meatadataDetails), AllDetailsDTO.class);
		
		if(detailsDTO.getVisibilityFlag()==Boolean.TRUE)
			detailsDTO=lockEditableWorkflow(detailsDTO.getProcessDefinitionKey(),userid,detailsDTO.getProcessStatus(),detailsDTO.getVersion());
		
		return detailsDTO;
		
	}
	
	public AllDetailsDTO saveDeployedProcessChange(String userid,@Valid AllDetailsDTO allDetailsDTO){
		AllDetailsDTO detailsDTO = new AllDetailsDTO();
		Optional<WorkflowMeatadataDetails> optional =metadataDetailsRepository.findByProcessDefinitionKeyAndVersionAndProcessStatus(allDetailsDTO.getProcessDefinitionKey(),allDetailsDTO.getVersion(),allDetailsDTO.getProcessStatus());
		if(optional.isPresent()){
			optional.get().setLastModifiedBy(allDetailsDTO.getLastModifiedBy());
			optional.get().setLastModifiedTimeStamp(new SimpleDateFormat("dd-MM-yy hh.mm aa").format(new Date()));
			optional.get().setProcessName(allDetailsDTO.getProcessName());
			optional.get().setProcessStatus("deploy-change");
			
			if(allDetailsDTO.getVisibilityFlag()!=Boolean.FALSE)
				optional.get().setVisibilityFlag(Boolean.TRUE);
			
			optional.get().setSvgDiagramData(allDetailsDTO.getSvgDiagramData().toString());
			optional.get().setXmldata(allDetailsDTO.getXmldata());
			detailsDTO=mapper.map(metadataDetailsRepository.save(optional.get()), AllDetailsDTO.class);
			if(detailsDTO.getVisibilityFlag()==Boolean.TRUE)
				lockEditableWorkflow(detailsDTO.getProcessDefinitionKey(),userid,detailsDTO.getProcessStatus(),detailsDTO.getVersion());
				
		}else{
			
			long version=0;
			WorkflowMeatadataDetails meatadataDetails=new WorkflowMeatadataDetails();
			meatadataDetails.setCreatedBy(userid);
			meatadataDetails.setProcessCategory(allDetailsDTO.getProcessCategory());
			meatadataDetails.setProcessDefinitionKey(allDetailsDTO.getProcessDefinitionKey());
			meatadataDetails.setProcessDescription(allDetailsDTO.getProcessDescription());
			meatadataDetails.setProcessStatus("deploy-change");
			meatadataDetails.setSvgDiagramData(allDetailsDTO.getSvgDiagramData().toString());
			meatadataDetails.setVersion(allDetailsDTO.getVersion());
			meatadataDetails.setVisibilityFlag(Boolean.TRUE);
			meatadataDetails.setXmldata(allDetailsDTO.getXmldata());
			meatadataDetails.setProcessName(allDetailsDTO.getProcessName());
			if(metadataDetailsRepository.existsByProcessDefinitionKeyAndProcessStatus(allDetailsDTO.getProcessDefinitionKey(),allDetailsDTO.getProcessStatus())){
				version=metadataDetailsRepository.getMaxVersionCount(allDetailsDTO.getProcessDefinitionKey());
					
			}
			meatadataDetails.setVersion(++version);
			detailsDTO=mapper.map(metadataDetailsRepository.save(meatadataDetails),AllDetailsDTO.class);
			if(detailsDTO.getVisibilityFlag()==Boolean.TRUE)
				detailsDTO=lockEditableWorkflow(detailsDTO.getProcessDefinitionKey(),userid,detailsDTO.getProcessStatus(),detailsDTO.getVersion());
				
		}
		return detailsDTO;
	}

@Transactional
	public void deleteWorkflowByNameAndStatus(String processid, String status, long version) {
		
		WorkflowMeatadataDetails meatadataDetails =metadataDetailsRepository.findByProcessDefinitionKeyAndVersionAndProcessStatus(processid, version,status).orElseThrow(()-> new WorkflowsNotFoundException("Workflow Details for Process Definition key: "+processid+" with status: "+status+" not found in database.."));
		metadataDetailsRepository.deleteById(meatadataDetails.getIdentify());
		
	}


public CamundaServerStatusDTO checkConnectivity(String url, CamundaServerRequestDTO requestDto) {
	final String URL = url+"/"+camundaEngine+"/identity/verify";
	CamundaServerStatusDTO serverStatusDTO=new CamundaServerStatusDTO();
	try{
		CamundaServerRequest serverRequest=mapper.map(requestDto, CamundaServerRequest.class);
			 	 
			  RequestEntity<CamundaServerRequest> request = RequestEntity .post(new
			 URI(URL)) .contentType(MediaType.APPLICATION_JSON) .body(serverRequest);
			  
			  CamundaServerStatusDTO response =
			  restTemplate.postForObject(URL,request, CamundaServerStatusDTO.class);
			 
		serverStatusDTO=response;
	}catch(Exception ex){
		if(ex instanceof  ResourceAccessException){
		logger.log(Level.SEVERE,"Connection refused to connect..",ex);
		throw new RuntimeException("Connection refused to connect to URL: "+URL);
		}
		else { 
			logger.log(Level.SEVERE,"something went wrong to connect, try again..",ex);
			throw new RuntimeException("something went wrong to connect, try again");
		}
		}
	return serverStatusDTO;
}

@Transactional
public void deleteProcessDefinitionId(String processdefinitionid) {
	UrlDetails  urlDetail=checkCamundaServerEnvironmentsAvailability();
	checkConnectivity(urlDetail.getUrl(),new CamundaServerRequestDTO(urlDetail.getUsername(),urlDetail.getPassword()));
	JSONObject jsonObject=getRunningProcessInstancesCount(processdefinitionid);
	int number=(int) jsonObject.get("count");
	if(number==0){
	String url=urlDetail.getUrl()+"/"+camundaEngine+"/process-definition/"+processdefinitionid;
	ResponseEntity<Void> response =connectToUrl(url,HttpMethod.DELETE,Void.class,urlDetail); 
     logger.log(Level.INFO,"deleteProcessDefinitionId response status code : {0}",response.getStatusCode());
     if(HttpStatus.OK.equals(response.getStatusCode()))
         refreshAndGetAllProcess(urlDetail.getUrl());
	}
	else throw new RunningProcessInstanceAvailableException("Can not Delete,Active Process Instances are Available..");
     
}


public JSONObject getRunningProcessInstancesCount(String processdefinitionid) {
	UrlDetails  urlDetail=checkCamundaServerEnvironmentsAvailability();
	checkConnectivity(urlDetail.getUrl(),new CamundaServerRequestDTO(urlDetail.getUsername(),urlDetail.getPassword()));
	String url=urlDetail.getUrl()+"/"+camundaEngine+"/process-instance/count?processDefinitionId="+processdefinitionid+"&active=true";
	  ResponseEntity<JSONObject> response=connectToUrl(url,HttpMethod.GET,JSONObject.class,urlDetail);
	        return response.getBody();
}


public <T> ResponseEntity<T> connectToUrl(String url, HttpMethod method, Class<T> responseType,UrlDetails urlDetails) {
	 ResponseEntity<T> response=null;
	try{
			final List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
			
			  interceptors.add( new BasicAuthenticationInterceptor(urlDetails.getUsername(),urlDetails.getPassword()));
			     restTemplate.setInterceptors( interceptors );
			     response =restTemplate.exchange(url, method,null, responseType);
	  }catch(Exception ex){
		  if(ex instanceof  ResourceAccessException){
				logger.log(Level.SEVERE,"Connection refused to connect..",ex);
				throw new RuntimeException("Connection refused to connect to URL: "+urlDetails.getUrl());
				}
		  else if(ex instanceof HttpClientErrorException) {
			  logger.log(Level.SEVERE,"requested process not found in camunda server.",ex);
				throw new RuntimeException("requested process not found in camunda server.");
				
		  }
				else throw new RuntimeException("something went wrong, please try again..");
	     }
	return response;
}


public UrlDetails checkCamundaServerEnvironmentsAvailability(){
	UrlDetails  urlDetail=null;
	if(urlDetailsRepository.existsByComponent("CamundaServerEnvironments")){
		 urlDetail=urlDetailsRepository.findBycomponent("CamundaServerEnvironments");
	}else throw new RuntimeException("CamundaServerEnvironments details not available to proceed..");
	return urlDetail;
}


public Map<String, List<AllDetailsDTO>> getAllDeployChangeProcess() {
	Map<String, List<AllDetailsDTO>> map = new HashMap<>();
	Optional<List<WorkflowMeatadataDetails>> workflowMeatadataDetails= metadataDetailsRepository.findAllByProcessStatus("deploy-change");
if(workflowMeatadataDetails.isPresent()){
	 map= workflowMeatadataDetails.get().stream()
	.map(process -> mapper.map(process, AllDetailsDTO.class))
	.sorted(Comparator.comparing(AllDetailsDTO::getVersion).reversed())
	.collect(Collectors.groupingBy(AllDetailsDTO::getProcessDefinitionKey));
}
return map;
}



public List<DashboardDataDTO> getDashboardData(String... processCategory) {
	List<DashboardProcessData> meatadataDetails =metadataDetailsRepository.findByProcessCategoryIn(processCategory);
	List<DashboardDataDTO>dashboardDataDTOs=meatadataDetails.stream().map(details -> {
		return mapper.map(details,DashboardDataDTO.class);
	}).collect(Collectors.toList());
	return dashboardDataDTOs;
}



public Map<String, List<AllDetailsDTO>> searchProcessDefinitions( String workflowName, String[] processCategory) {
	
	Map<String, List<AllDetailsDTO>> map = new HashMap<>();
	//return metadataDetailsRepository.searchProcess(workflowName,processCategory);
	List<WorkflowMeatadataDetails> meatadataDetails=metadataDetailsRepository.searchProcess(workflowName,processCategory);
		 map= meatadataDetails.stream()
		.map(process -> mapper.map(process, AllDetailsDTO.class))
		.sorted(Comparator.comparing(AllDetailsDTO::getVersion).reversed())
		.collect(Collectors.groupingBy(AllDetailsDTO::getProcessDefinitionKey));
	return map;
	}
	
	
	//.collect(Collectors.groupingBy(AllDetailsDTO::getProcessDefinitionKey))

}


	


