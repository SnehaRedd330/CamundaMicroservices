package com.mudynamics.workflowservice;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mudynamics.workflowservice.models.UrlDetails;
import com.mudynamics.workflowservice.repositories.UrlDetailsRepository;
import com.mudynamics.workflowservice.services.EventOrchestrationService;







@Component
public class CamundaProcessInvoker {

	@Autowired
	private EventOrchestrationService eventOrchestrationService;
	
	@Autowired
	private UrlDetailsRepository detailsRepository;
	
	@Value("${camunda.engine}")
	private String camundaEngine;
	
	public void invokeProcess(String topic, ConsumerRecord<Integer, String> data) {

		// call to get List of workflow
		String workflowName = eventOrchestrationService.getWorkflowByTopic(topic);
		
			try {
				startCamundaProcess(data.value(), workflowName);

			} catch (Exception exception) {
				System.out.println("WFD-MS Error occured in invoking camunda Process " + exception);
			}
	}

	/**
	 * This method triggers camunda process workflow for given topicContent and
	 * processName.
	 * 
	 */
	private  void startCamundaProcess(String topicContent, String processName) {

		HttpURLConnection conn = null;
		URL url;
		String content = JSONValue.escape(topicContent);

		String input = "  {\r\n  \"variables\": {\r\n      \"payload\": {\r\n        \"value\": \"" + content
				+ "  \",\r\n \"type\": \"String\",\r\n\"valueInfo\": {\r\n \"objectTypeName\": \"my.own.BeanWithModeAndMetaProps\",\r\n\"serializationDataFormat\": \"application/json\"\r\n  } }  }} ";
		
		 UrlDetails urlDetail=new UrlDetails();
			if(detailsRepository.existsByComponent("CamundaServerEnvironments")){
				urlDetail=detailsRepository.findBycomponent("CamundaServerEnvironments");
			}else throw new RuntimeException("CamundaServerEnvironments details not available to proceed..");
		String camundaUrl = urlDetail.getUrl()+"/"+camundaEngine+"/engine/default/process-definition/key/";
		String camundaUrlStr = camundaUrl + processName.trim() + "/submit-form";

		try {

			url = new URL(camundaUrlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException(
						"Failed : HTTP error code : " + conn.getResponseCode() + conn.getResponseMessage());
			}

		} catch (Exception exception) {
			throw new RuntimeException("Error occured in starting Camunda Process: " + exception.getLocalizedMessage());
		}

		finally {
			if (null != conn) {
				conn.disconnect();

			}
		}

	}
}
