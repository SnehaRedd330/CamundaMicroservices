package com.mudynamics.workflowservice.services;

import com.mudynamics.workflowservice.dto.UrlDetailsDTO;

public interface UrlDetailsService {

	UrlDetailsDTO createUrl(UrlDetailsDTO urldtl);

	UrlDetailsDTO getComponentDetails();

	void deleteComponentDetails(String componentname);

	UrlDetailsDTO updateComponentUrls(UrlDetailsDTO urldtl);

}
