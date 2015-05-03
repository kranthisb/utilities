package com.auth.client.filters;

import java.io.IOException;

import javax.annotation.Priority;
import javax.servlet.ServletContext;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ServerResponse;

import com.auth.client.service.AuthClient;
import com.auth.common.constants.HTTPHeaderNames;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticateFilter implements ContainerRequestFilter {
	final static Logger log = Logger.getLogger(AuthenticateFilter.class);	
	private static final ServerResponse ACCESS_DENIED = new ServerResponse("Need to authenticate", 401, new Headers<Object>());;
	@Context ServletContext servletContext;

	public void filter(ContainerRequestContext requestContext) throws IOException {
		String path = requestContext.getUriInfo().getPath();
		if ( !path.startsWith( "/login" ) ) {		
			String authToken = requestContext.getHeaderString( HTTPHeaderNames.AUTH_TOKEN_HEADER );
			String apiToken = requestContext.getHeaderString( HTTPHeaderNames.API_TOKEN_HEADER );
			String appName = requestContext.getHeaderString( HTTPHeaderNames.APP_NAME_HEADER );
			if(authToken != null && !authToken.isEmpty()){ 
				if ( !new AuthClient().isUserAuthTokenValid(authToken ) ) {
					requestContext.abortWith(ACCESS_DENIED);
				}
			}else if(apiToken != null && !apiToken.isEmpty() && appName != null && !appName.isEmpty()){
				if ( !new AuthClient().isAPITokenValid(apiToken, appName ) ) {
					requestContext.abortWith(ACCESS_DENIED);
				}
			}else{
				requestContext.abortWith(ACCESS_DENIED);
				return;
			}			
		}
		return;
	}
}
