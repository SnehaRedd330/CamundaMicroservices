package com.mudynamics.workflowservice.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.mudynamics.workflowservice.models.ActivityDetails;
import com.mudynamics.workflowservice.models.ClaimedTaskCount;
import com.mudynamics.workflowservice.models.ProcessDetails;
import com.mudynamics.workflowservice.models.ProcessInstanceDetails;
import com.mudynamics.workflowservice.models.StatusDetails;
import com.mudynamics.workflowservice.models.UrlDetails;
import com.mudynamics.workflowservice.models.WorkflowDetails;

@Service
public class LogDetailsService 
{

	private static Logger logger = Logger.getLogger(LogDetailsService.class.getName());
	@Autowired
	private WorkflowDetailService workflowDetailService;
	
	@Value("${camunda.engine}")
	private String camundaEngine;
	
	public List<ProcessDetails>  getProcessDetails() 
	{
		List<ProcessDetails> list=new ArrayList<ProcessDetails>();
		UrlDetails  urlDetail=workflowDetailService.checkCamundaServerEnvironmentsAvailability();
		try
		{
		
			String url=urlDetail.getUrl()+"/"+camundaEngine+"/process-definition";
			ResponseEntity<WorkflowDetails[]> response = workflowDetailService.connectToUrl(url,HttpMethod.GET,WorkflowDetails[].class,urlDetail);
			  List<WorkflowDetails> logDetails= Arrays.asList(response.getBody());
			  logDetails.stream().forEach(log->
			  {
				  String processDefId=log.getId();
			    	 String processName=log.getName();
			    	 long version=(long) log.getVersion(); 
			    	 
			    	 final String counturl=urlDetail.getUrl()+"/"+camundaEngine+"/history/process-instance/count?unfinished=true&processDefinitionId="+processDefId;
			    	 ResponseEntity<ClaimedTaskCount> response1 = workflowDetailService.connectToUrl(counturl,HttpMethod.GET,ClaimedTaskCount.class,urlDetail);
			    	 long c=response1.getBody().getCount();
			    	 if(c==0)
					    {
					    	 ProcessDetails process=new ProcessDetails(processDefId,processName,version,c,"COMPLETED");
			     			 list.add(process);
			            }	
					     else
					     {
					    	 ProcessDetails process=new ProcessDetails(processDefId,processName,version,c,"ACTIVE");
			     			 list.add(process);
					     } 
			  });
			       
		}
		
		catch(Exception e){
			if(e instanceof ResourceAccessException){
				logger.log(Level.SEVERE,"Connection refused to connect..",e);
				throw new RuntimeException("Connection refused to connect to HOST:"+urlDetail.getUrl());
			}
			else throw new RuntimeException("something went wrong in process deployment..{0}",e);
		}
		return list;
}

	@SuppressWarnings("unchecked")
	public List<ActivityDetails[]> getActivityDetails(String procInstId) 
	{
		
		UrlDetails  urlDetail=workflowDetailService.checkCamundaServerEnvironmentsAvailability();
        ResponseEntity<JSONArray> response=null;
		try
		{
	      final String  uri=urlDetail.getUrl()+"/"+camundaEngine+"/history/activity-instance?processInstanceId="+procInstId;
	  response= workflowDetailService.connectToUrl(uri,HttpMethod.GET,JSONArray.class,urlDetail);
	      
	     }
		catch(Exception e){
			if(e instanceof ResourceAccessException){
				logger.log(Level.SEVERE,"Connection refused to connect..",e);
				throw new RuntimeException("Connection refused to connect to HOST:"+urlDetail.getUrl());
			}
			else throw new RuntimeException("something went wrong in process deployment..{0}",e);
		}
		return response.getBody();
}

	@SuppressWarnings("unchecked")
	public List<ProcessInstanceDetails> getProcessInstances(String procDefId) {
	
		UrlDetails  urlDetail=workflowDetailService.checkCamundaServerEnvironmentsAvailability();
        ResponseEntity<JSONArray> response=null;
		try
		{
	      final String  uri=urlDetail.getUrl()+"/"+camundaEngine+"/history/process-instance?unfinished=true&processDefinitionId="+procDefId;
	      response= workflowDetailService.connectToUrl(uri,HttpMethod.GET,JSONArray.class,urlDetail);
	      
	     }
		catch(Exception e){
			if(e instanceof ResourceAccessException){
				logger.log(Level.SEVERE,"Connection refused to connect..",e);
				throw new RuntimeException("Connection refused to connect to HOST:"+urlDetail.getUrl());
			}
			else throw new RuntimeException("something went wrong in process deployment..{0}",e);
		}
			
		return response.getBody();
	}

	public List<StatusDetails> getStatus() 
	{
		 long commpletedProcessInstanceCount=0;
		  long activeProcessInstanceCount=0;
		  List<StatusDetails> statuslist=new ArrayList<StatusDetails>();
			UrlDetails  urlDetail=workflowDetailService.checkCamundaServerEnvironmentsAvailability();
		  try
		  {
			  
			  final String uri=urlDetail.getUrl()+"/"+camundaEngine+"/process-definition";
				ResponseEntity<WorkflowDetails[]> response = workflowDetailService.connectToUrl(uri,HttpMethod.GET,WorkflowDetails[].class,urlDetail);
				  List<WorkflowDetails> statusDetails= Arrays.asList(response.getBody());
				  for(WorkflowDetails status:statusDetails){
					  String processDefId=status.getId();
					  final String countUrl=urlDetail.getUrl()+"/"+camundaEngine+"/history/process-instance/count?unfinished=true&processDefinitionId="+processDefId;
					    ResponseEntity<ClaimedTaskCount> res = workflowDetailService.connectToUrl(countUrl,HttpMethod.GET,ClaimedTaskCount.class,urlDetail);
					  	long runningProcessInstanceCount=res.getBody().getCount();
						if(runningProcessInstanceCount==0)
							++commpletedProcessInstanceCount;
						else ++activeProcessInstanceCount;	    
				  }
				   StatusDetails commpletedProcessInstanceCountStatus=new StatusDetails("COMPLETED",commpletedProcessInstanceCount);
				    StatusDetails activeProcessInstanceCountStatus=new StatusDetails("ACTIVE",activeProcessInstanceCount);
				 	statuslist.add(commpletedProcessInstanceCountStatus);
 					statuslist.add(activeProcessInstanceCountStatus);  		  
	  }
		  catch(Exception e){
				if(e instanceof ResourceAccessException){
					logger.log(Level.SEVERE,"Connection refused to connect..",e);
					throw new RuntimeException("Connection refused to connect to HOST:"+urlDetail.getUrl());
				}
				else throw new RuntimeException("something went wrong in process deployment..{0}",e);
			}
	   return  statuslist;
	}	
}
	

