package com.auth.common.service;

import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import com.auth.common.algorithm.AuthenticationAlgoI;
import com.auth.common.algorithm.MACAlgo;
import com.auth.common.constants.HTTPHeaderNames;
import com.auth.common.entity.UserEntity;

public final class TokenService {

	final static Logger log = Logger.getLogger(TokenService.class);
	AuthenticationAlgoI authenticationAlgo;
	
	public TokenService(ServletContext servletContext){
		Properties configProperties = (Properties)(servletContext.getAttribute("configProperties"));
		authenticationAlgo = new MACAlgo();
		authenticationAlgo.initializeAlgo(configProperties);
		HashMap<String, String> apiTokenMap = 
				authenticationAlgo.fetchAPITokens(configProperties.getProperty("apikeys.props.path"));
		servletContext.setAttribute(HTTPHeaderNames.ALL_API_TOKENS, apiTokenMap);
	}

	public boolean isAuthTokenValid(String token) {
		return authenticationAlgo.isAuthTokenValid(token);
	}

	public String createTokenForUser(UserEntity user) {
		return authenticationAlgo.createTokenForUser(user);
	}
	
}