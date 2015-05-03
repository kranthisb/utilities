package com.auth.server.service;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.auth.common.entity.UserEntity;
import com.auth.common.service.TokenService;

public class AuthService {
	final static Logger log = Logger.getLogger(AuthService.class);
	private static final long THIRTY_MINS = 1000 * 60 * 30;
	private TokenService tokenService;
	
	public AuthService(TokenService tokenService){
		this.tokenService = tokenService;
	}
	
	public boolean isUserAuthTokenValid(String authToken ) {		
		return tokenService.isAuthTokenValid(authToken);
	}

	public String login(String username, String password ) {
		boolean isAuthSuccessful = false;
		String authToken = null;
		// TODO : Validate username/password. Hardcoding it for now.
		isAuthSuccessful = true;
		if(isAuthSuccessful){ 
			authToken = generateAuthTokenForUser(username);
		}		
		return authToken;
	}

	private String generateAuthTokenForUser(String userName) {
		UserEntity user = new UserEntity();
		user.setUsername(userName);
		user.setExpiresTimeStamp(System.currentTimeMillis() + THIRTY_MINS);
		return tokenService.createTokenForUser(user);
	}
	
	
}
