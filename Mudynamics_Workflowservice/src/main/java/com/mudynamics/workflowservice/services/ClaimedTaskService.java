package com.mudynamics.workflowservice.services;




import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mudynamics.workflowservice.dto.CamundaServerRequestDTO;
import com.mudynamics.workflowservice.models.ClaimedDetails;
import com.mudynamics.workflowservice.models.ClaimedIds;
import com.mudynamics.workflowservice.models.ClaimedTaskCount;
import com.mudynamics.workflowservice.models.ClaimedTaskDetails;
import com.mudynamics.workflowservice.models.UrlDetails;

@Service
public class ClaimedTaskService 
{
	@Autowired
	private WorkflowDetailService workflowDetailService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${camunda.engine}")
	private String camundaEngine;
	
	private List<ClaimedIds> claimedIdsList;
	
	public List<ClaimedDetails> getClaimedTasks(
			@Size(max = 30, min = 3, message = "Assignee should be between 3 and 30 characters") String assignee)
	{
		List<ClaimedDetails> cDetails=new ArrayList<>();
		UrlDetails  urlDetail=workflowDetailService.checkCamundaServerEnvironmentsAvailability();
		//workflowDetailService.checkConnectivity(urlDetail.getUrl(),new CamundaServerRequestDTO(urlDetail.getUsername(),urlDetail.getPassword()));
		String url=urlDetail.getUrl()+"/"+camundaEngine+"/task?active=true&assignee="+assignee;
		ResponseEntity<ClaimedTaskDetails[]> response = workflowDetailService.connectToUrl(url,HttpMethod.GET,ClaimedTaskDetails[].class,urlDetail);
		  List<ClaimedTaskDetails> claimedTaskDetails= Arrays.asList(response.getBody());
		  Map<String,List<ClaimedTaskDetails>>  claimedDetails=claimedTaskDetails.stream().collect(Collectors.groupingBy(ClaimedTaskDetails::getProcessDefinitionId));
		  for(Entry<String, List<ClaimedTaskDetails>> entry :claimedDetails.entrySet())
		  {
			  claimedIdsList =new ArrayList<>();
			  List<ClaimedTaskDetails> value=entry.getValue();
			  value.stream().forEach(values->
			  { 
				  ClaimedIds claimedIds=new ClaimedIds();
				  claimedIds.setProcessInstanceId(values.getId());
				  claimedIdsList.add(claimedIds);
			  });
			  String activityName=value.get(0).getName();
			  String processDefinitionId=entry.getKey();
			  String url1=urlDetail.getUrl()+"/"+camundaEngine+"/task/count?active=true&assignee="+assignee+"&processDefinitionId="+processDefinitionId;
			  ResponseEntity<ClaimedTaskCount> response1 = workflowDetailService.connectToUrl(url1,HttpMethod.GET,ClaimedTaskCount.class,urlDetail); 
			  ClaimedDetails clDetails=new ClaimedDetails();
			  clDetails.setCount(response1.getBody().getCount());
			  clDetails.setActivityName(activityName);
			  clDetails.setAnId(claimedIdsList);
			  cDetails.add(clDetails);
		  }
	    		 return cDetails;    
	}

	
	 public void completeTask(String taskId,JSONObject payload) 
	 {
		    UrlDetails  urlDetail=workflowDetailService.checkCamundaServerEnvironmentsAvailability();
			workflowDetailService.checkConnectivity(urlDetail.getUrl(),new CamundaServerRequestDTO(urlDetail.getUsername(),urlDetail.getPassword()));
			String url=urlDetail.getUrl()+"/"+camundaEngine+"/task/"+taskId+"/complete";
			HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);
		    HttpEntity<JSONObject> entity = new HttpEntity<>(payload , headers);
		    restTemplate.postForObject(url,entity,Void.class);     
	 }
	public JSONObject getFormVariablesOfClaimedTasks(@NotBlank String taskId) {
		UrlDetails  urlDetail=workflowDetailService.checkCamundaServerEnvironmentsAvailability();
		workflowDetailService.checkConnectivity(urlDetail.getUrl(),new CamundaServerRequestDTO(urlDetail.getUsername(),urlDetail.getPassword()));
		String url=urlDetail.getUrl()+"/"+camundaEngine+"/task/"+taskId+"/form-variables";
	ResponseEntity<JSONObject> response=workflowDetailService.connectToUrl(url,HttpMethod.GET,JSONObject.class,urlDetail);
        
		
		
		return response.getBody();
	}
	
}
