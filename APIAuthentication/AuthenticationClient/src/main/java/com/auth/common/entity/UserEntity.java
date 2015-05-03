package com.auth.common.entity;

public class UserEntity {
	private String username;
	private long expiresTimeStamp;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public long getExpiresTimeStamp() {
		return expiresTimeStamp;
	}
	public void setExpiresTimeStamp(long expiresTimeStamp) {
		this.expiresTimeStamp = expiresTimeStamp;
	}	
}
