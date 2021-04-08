package com.mudynamics.workflowservice.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class DeploymentDescriptor {
	@NotBlank(message=" workflow process Definition Key must not be blank..")
	private String processDefKey;
	@NotBlank(message=" workflow process name Key must not be blank..")
	private String processName;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@NotNull(message=" workflow process version must not be null..")
	private long version;
	@NotBlank(message=" workflow process xmls data must not be blank..")
	private String xmldata;
	@NotBlank(message=" server name must not be blank..")
	private String servername;
	@NotBlank(message=" server URL must not be blank..")
	private String url;
	@NotBlank(message=" workflow process svg image data must not be blank..")
	private String svgImage;
	
	@NotBlank(message="workflow current status must not be blank...")
	private String status;
	
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getProcessDefKey() {
		return processDefKey;
	}
	public void setProcessDefKey(String processDefKey) {
		this.processDefKey = processDefKey;
	}
	public long getVersion() {
		return version;
	}
	public void setVersion(long version) {
		this.version = version;
	}
	public String getXmldata() {
		return xmldata;
	}
	public void setXmldata(String xmldata) {
		this.xmldata = xmldata;
	}
	public String getServername() {
		return servername;
	}
	public void setServername(String servername) {
		this.servername = servername;
	}

	public String getSvgImage() {
		return svgImage;
	}
	public void setSvgImage(String svgImage) {
		this.svgImage = svgImage;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
		
	
}

