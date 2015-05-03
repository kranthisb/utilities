package com.auth.common.algorithm;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.Logger;

import com.auth.common.constants.HTTPHeaderNames;
import com.auth.common.entity.UserEntity;
import com.auth.common.util.AuthServiceUtil;

public class MACAlgo implements AuthenticationAlgoI {

	final static Logger log = Logger.getLogger(MACAlgo.class);

	private static final String HMAC_ALGO = "HmacSHA256";
	private static final String SEPARATOR = ".";
	private static final String SEPARATOR_SPLITTER = "\\.";
	
	private Mac hmac;
	
	@Override
	public void initializeAlgo(Properties configProperties) {
		String secretKey = fetchSecretKey(configProperties.getProperty("secretkeys.props.path"));
		setHmac(secretKey);
	}
	
	public void setHmac(String secretKey){
		try {
			hmac = Mac.getInstance(HMAC_ALGO);
			hmac.init(new SecretKeySpec(DatatypeConverter.parseBase64Binary(secretKey), HMAC_ALGO));
		} catch (Exception e) {
			throw new IllegalStateException("failed to initialize HMAC: " + e.getMessage(), e);
		}
	}
	private byte[] createHmac(byte[] content) {
		return hmac.doFinal(content);
	}
	
	@Override
	public boolean isAuthTokenValid(String token) {
		final String[] parts = token.split(SEPARATOR_SPLITTER);
		if (parts.length == 2 && parts[0].length() > 0 && parts[1].length() > 0) {
			try {
				final byte[] userBytes = AuthServiceUtil.fromBase64(parts[0]);
				final byte[] hash = AuthServiceUtil.fromBase64(parts[1]);
				boolean validHash = Arrays.equals(createHmac(userBytes), hash);
				if (validHash) {
					final UserEntity user = AuthServiceUtil.fromJSON(userBytes);
					if (new Date().getTime() < user.getExpiresTimeStamp()) {
						return true;
					}
				}
			} catch (IllegalArgumentException e) {
				//log tempering attempt here
			}
		}
		return false;
	}	

	@Override
	public String createTokenForUser(UserEntity user) {
		byte[] userBytes = AuthServiceUtil.toJSON(user);
		byte[] hash = createHmac(userBytes);
		final StringBuilder sb = new StringBuilder(170);
		sb.append(AuthServiceUtil.toBase64(userBytes));
		sb.append(SEPARATOR);
		sb.append(AuthServiceUtil.toBase64(hash));
		return sb.toString();
	}

	@Override
	public HashMap<String, String> fetchAPITokens(String apiKeysPath) {
		Properties apiKeyProps = AuthServiceUtil.loadProperties(apiKeysPath);		
		HashMap<String, String> apiTokenMap = new HashMap<String, String>();
		for (String key : apiKeyProps.stringPropertyNames()) {
			String apiKey = apiKeyProps.getProperty(key);
			String apiToken = generateAPIToken(apiKey);

			apiTokenMap.put(key,apiToken);
		}
		return apiTokenMap;
	}
	
	public String generateAPIToken(String apiKey) {
		byte[] apiKeyBytes = AuthServiceUtil.toJSON(apiKey);
		byte[] hash = createHmac(apiKeyBytes);
		final StringBuilder sb = new StringBuilder(170);
		sb.append(AuthServiceUtil.toBase64(hash));
		return sb.toString();
	}
	
	public String fetchSecretKey(String secretKeyPath){
		Properties secretKeyProperties = AuthServiceUtil.loadProperties(secretKeyPath);
		return secretKeyProperties.getProperty(HTTPHeaderNames.CS_SECRET_KEY);		
	}
}
