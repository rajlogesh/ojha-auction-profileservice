package com.auction.portal.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProfileServiceUtil {

	public HttpHeaders getHttpHeaders() {
		logger.debug("Generating headers for API Call");
		HttpHeaders httpHeaders = new HttpHeaders();
		List<MediaType> acceptableMediaTypes = new ArrayList<>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		httpHeaders.setAccept(acceptableMediaTypes);
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		return httpHeaders;
	}

	public String getResponseId() {
		long responseId = System.currentTimeMillis();
		logger.info("Response ID : {}", responseId);
		return String.valueOf(responseId);
	}

//	public Object convertFromXMLToObject() {
//		try {
//			JAXBContext context = JAXBContext.newInstance(CompanyServiceErrors.class);
//			Unmarshaller unmarshaller = context.createUnmarshaller();
//			InputStream stream = this.getClass().getResourceAsStream(COMPANY_SERVICE_ERROR_XML_FILE_PATH);
//			return unmarshaller.unmarshal(stream);
//		}catch (Exception e) {
//			logger.error("Exception occurred during unmarshalling the error xml file : {}",e);
//			return null;
//		}
//	}
}
