package com.auth.server.controller;


import java.util.HashMap;

import javax.annotation.security.PermitAll;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.auth.common.constants.HTTPHeaderNames;
import com.auth.common.entity.APITokenEntity;
import com.auth.common.entity.AuthEntity;
import com.auth.common.entity.AuthTokenEntity;
import com.auth.server.service.AuthService;


@Component
@Path("/")
public class AuthenticationController {
	final static Logger log = Logger.getLogger(AuthenticationController.class);
	
	@Context org.jboss.resteasy.spi.HttpResponse response;
	@Context ServletContext servletContext;
	
	private AuthService authService;
	
	@PermitAll
	@POST @Path("/login") 
	public void login(AuthEntity authEntity) throws Exception {
		init();
		String authToken = authService.login(authEntity.getUserName(), authEntity.getPassword());
		//TOOD : Save this token in DB
		response.getOutputHeaders().putSingle(HTTPHeaderNames.AUTH_TOKEN_HEADER, authToken);
	}
	
	@POST @Path("/authenticate") 
	@Produces("application/json")
	public AuthTokenEntity authenticate(AuthTokenEntity authTokenEntity) throws Exception {
		init();
		boolean isAuthTokenValid = authService.isUserAuthTokenValid(authTokenEntity.getAuthToken()); 
		authTokenEntity.setIsAuthTokenValid(isAuthTokenValid);
		return authTokenEntity;
	}
	
	@POST @Path("/validateapitoken") 
	@Produces("application/json")
	public APITokenEntity validateAPIToken(APITokenEntity apiTokenEntity) throws Exception {
		@SuppressWarnings("unchecked")
		HashMap<String, String> apiTokenMap = (HashMap<String, String>) (servletContext.getAttribute(
				HTTPHeaderNames.ALL_API_TOKENS));		
		String originalAPIToken = apiTokenMap.get(apiTokenEntity.getAppName());
		boolean isAPITokenValid = originalAPIToken.equals(apiTokenEntity.getApiToken());
		apiTokenEntity.setIsApiTokenValid(isAPITokenValid);
		return apiTokenEntity;
	}
	
	@GET
	@Path("/test") 
	@Produces(MediaType.APPLICATION_JSON)
    public String demoGetMethod() {
        return "Successful";
    }
	
	private void init(){
		authService = (AuthService)servletContext.getAttribute(HTTPHeaderNames.AUTH_SERVICE_INSTANCE);
	}
}