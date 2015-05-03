package com.auth.server.interceptor;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import com.auth.common.constants.HTTPHeaderNames;


 
@Provider
public class ResponseInterceptor implements ContainerResponseFilter {

	/**
	 * In order to allow certain specific custom HTTP headers to be accepted, 
	 * the header name “Access-Control-Allow-Headers” follow by the value of custom headers with “,” as the separator 
	 * must be added as part of the custom headers value. 
	 * This is the way to inform the browser or REST client of the custom headers allowed. - 
	 * */
	
	public void filter(ContainerRequestContext requestContext,
			ContainerResponseContext responseContext) throws IOException {
		// TODO Auto-generated method stub
		
		responseContext.getHeaders().add( "Access-Control-Allow-Origin", "*" );    // You may further limit certain client IPs with Access-Control-Allow-Origin instead of '*'
		responseContext.getHeaders().add( "Access-Control-Allow-Credentials", "true" );
		responseContext.getHeaders().add( "Access-Control-Allow-Methods", "GET, POST, DELETE, PUT" );
		responseContext.getHeaders().add( "Access-Control-Allow-Headers", HTTPHeaderNames.SERVICE_KEY + ", " + HTTPHeaderNames.AUTH_TOKEN_HEADER );

	}

}
