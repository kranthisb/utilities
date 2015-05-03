package com.auth.client.filters;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

import com.auth.common.constants.HTTPHeaderNames;

@Provider
public class ExtServiceCallFilter implements ClientRequestFilter  {

	final static Logger log = Logger.getLogger(ExtServiceCallFilter.class);
	
	@Context ServletContext servletContext;
	
	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {
		HashMap<String, String> allAPITokensMap = (HashMap<String, String>) (servletContext.getAttribute(HTTPHeaderNames.ALL_API_TOKENS));
		
		log.debug("In ExtServiceCallInterceptor : "+allAPITokensMap.get("feedmanager.apikey"));
		
		requestContext.getHeaders().add(HTTPHeaderNames.API_TOKEN_HEADER,allAPITokensMap.get("feedmanager.apikey"));
		requestContext.getHeaders().add(HTTPHeaderNames.APP_NAME_HEADER,"feedmanager.apikey");
	}	
}
