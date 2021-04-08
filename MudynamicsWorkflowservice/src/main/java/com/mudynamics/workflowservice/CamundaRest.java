package com.mudynamics.workflowservice;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mudynamics.workflowservice.models.DeploymentDescriptor;







@Service
public class CamundaRest {

	
	private static Logger logger = Logger.getLogger(CamundaRest.class.getName());
	 
	@Value("${camunda.engine}")
	private String camundaEngine;
	
	 public CamundaRest() {
		super();
	}

		
		public JsonObject postDeployment(DeploymentDescriptor descriptor) throws ParseException, ClientProtocolException, IOException{
			HttpResponse response=null;
			try{
			HttpPost uploadFile = new HttpPost(descriptor.getUrl()+"/"+camundaEngine+"/deployment/create");
			logger.log(Level.INFO,"deployment server URL created: {0}",uploadFile.getURI());
			MultipartEntityBuilder builder = MultipartEntityBuilder.create(); 
			builder.addTextBody("deployment-name", descriptor.getProcessName(), ContentType.TEXT_PLAIN);
			builder.addTextBody("enable-duplicate-filtering", "true", ContentType.TEXT_PLAIN);
			builder.addTextBody("deploy-changed-only", "true", ContentType.TEXT_PLAIN);
			/*
			 * if(files != null) { for(MultipartFile uploadedFile : files) {
			 * //builder.addBinaryBody("file", uploadedFile.getBytes()); InputStream is =
			 * new ByteArrayInputStream(uploadedFile.getBytes()); builder.addBinaryBody(
			 * "file", is, ContentType.APPLICATION_OCTET_STREAM, "file" ); } }
			 */
			InputStream is = new ByteArrayInputStream(descriptor.getXmldata().getBytes());
			
				builder.addBinaryBody(
				    "file",
				    is,
				    ContentType.APPLICATION_OCTET_STREAM,
				    descriptor.getProcessName()+".bpmn"
				);

			HttpEntity multipart = builder.build();
			uploadFile.setEntity(multipart);
			
			CloseableHttpClient httpClient = HttpClients.createDefault();
			 response = httpClient.execute(uploadFile);
		
			}catch(Exception e){
				if(e instanceof ResourceAccessException){
					logger.log(Level.SEVERE,"Connection refused to connect..",e);
					throw new RuntimeException("Connection refused to connect to URL:"+descriptor.getUrl());
				}
				else throw new RuntimeException("something went wrong in process deployment..{0}",e);
			}
			JsonObject jsonObject =logResponse(response);
			if(response.getStatusLine().getStatusCode()!= 200){
				throw new RuntimeException(jsonObject.get("type")+" occoured, reason: REQUESTED BPMN IS NOT VALID..");
			}
			
			 return jsonObject;
		}
		 public static JsonObject logResponse(HttpResponse response) throws IOException, ParseException {
			 StringBuilder result = new StringBuilder();
			
			    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			    String line;
			    while ((line = reader.readLine()) != null) {
			    	result.append(line);
			    }
			    logger.log(Level.INFO,"process deployment response: {0}",result.toString());
			    JsonObject jsonObject = (JsonObject) new JsonParser().parse(result.toString());
				return jsonObject;
			     
			  }
}
		

