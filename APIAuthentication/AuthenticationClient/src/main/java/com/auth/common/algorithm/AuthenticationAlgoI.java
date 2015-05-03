package com.auth.common.algorithm;

import java.util.HashMap;
import java.util.Properties;

import com.auth.common.entity.UserEntity;

public interface AuthenticationAlgoI {
	public void initializeAlgo(Properties configProperties);
	public boolean isAuthTokenValid(String token);
	public String createTokenForUser(UserEntity user);
	public HashMap<String, String> fetchAPITokens(String apiKeysPath);
}
