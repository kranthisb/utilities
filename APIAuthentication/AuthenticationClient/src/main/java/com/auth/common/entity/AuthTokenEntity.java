package com.auth.common.entity;

public class AuthTokenEntity {
	private String authToken;
	private boolean isAuthTokenValid;

	public AuthTokenEntity(){
	}
	
	public AuthTokenEntity(String authToken, boolean isAuthTokenValid){
		this.authToken = authToken;
		this.isAuthTokenValid = isAuthTokenValid;
	}
	
	public boolean getIsAuthTokenValid() {
		return isAuthTokenValid;
	}

	public void setIsAuthTokenValid(boolean isAuthTokenValid) {
		this.isAuthTokenValid = isAuthTokenValid;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}	
}

