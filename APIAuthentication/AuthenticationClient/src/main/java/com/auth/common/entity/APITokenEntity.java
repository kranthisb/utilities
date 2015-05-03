package com.auth.common.entity;

public class APITokenEntity {
	private String apiToken;
	private String appName;
	private boolean isApiTokenValid;

	public APITokenEntity(){}

	public APITokenEntity(String apiToken, String appName, boolean isApiTokenValid){
		this.apiToken = apiToken;
		this.appName = appName;
		this.isApiTokenValid = isApiTokenValid;
	}

	public boolean getIsApiTokenValid() {
		return isApiTokenValid;
	}

	public void setIsApiTokenValid(boolean isApiTokenValid) {
		this.isApiTokenValid = isApiTokenValid;
	}


	public String getApiToken() {
		return apiToken;
	}
	public void setApiToken(String apiToken) {
		this.apiToken = apiToken;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
}
