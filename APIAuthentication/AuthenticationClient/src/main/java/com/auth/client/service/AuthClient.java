package com.auth.client.service;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.auth.client.filters.ExtServiceCallFilter;
import com.auth.common.entity.APITokenEntity;
import com.auth.common.entity.AuthEntity;
import com.auth.common.entity.AuthTokenEntity;

public class AuthClient {
	final static Logger log = Logger.getLogger(AuthClient.class);

	public String login(String userName, String password){
		AuthEntity authEntity = new AuthEntity();
		authEntity.setPassword(password);
		authEntity.setUserName(userName);
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://localhost:8080/authenticationservice-1/login/");
		log.debug("Before sending request..");
		Response response = target.request().post(Entity.entity(authEntity, "application/json"));
		log.debug("After receiving response..");
		List<Object> authTokenList = response.getHeaders().get("X-Auth-Token");
		if(authTokenList != null){
			return authTokenList.get(0).toString();
		}
		response.close(); 
		return null;
	}

	public boolean isUserAuthTokenValid(String authToken){
		AuthTokenEntity authTokenEntity = new AuthTokenEntity(authToken,false);
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://localhost:8080/authenticationservice-1/authenticate/");
		Response response = target.request().post(Entity.entity(authTokenEntity, "application/json"));
		AuthTokenEntity value = response.readEntity(AuthTokenEntity.class);
		response.close(); 
		return value.getIsAuthTokenValid();
	}

	public boolean isAPITokenValid(String apiKey, String appName){
		APITokenEntity apiKeyEntity = new APITokenEntity(apiKey,appName, false);
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://localhost:8080/authenticationservice-1/validateapitoken/");
		Response response = target.request().post(Entity.entity(apiKeyEntity, "application/json"));
		APITokenEntity value = response.readEntity(APITokenEntity.class);
		response.close(); 
		return value.getIsApiTokenValid();		
	}

	public String test(String userName, String password){
		AuthEntity authEntity = new AuthEntity(userName,password);
		ResteasyClient client = new ResteasyClientBuilder().build().register(ExtServiceCallFilter.class);
		ResteasyWebTarget target = client.target("http://localhost:8080/authenticationservice-1/login/");
		Response response = target.request().post(Entity.entity(authEntity, "application/json"));
		//Read output in string format
		List<Object> authTokenList = response.getHeaders().get("X-Auth-Token");
		String value = null;
		if(authTokenList != null){
			value = authTokenList.get(0).toString();
		}
		response.close(); 
		return value;
	}
}
