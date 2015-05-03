package com.auth.common.util;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.xml.bind.DatatypeConverter;

import com.auth.common.entity.UserEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthServiceUtil {
	public static Properties loadProperties(String path) {
		InputStream input = null;
		Properties apiKeyProps = null;
		try {
			input = new FileInputStream(path);
			apiKeyProps = new Properties();
			apiKeyProps.load(input);	 
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}	
		return apiKeyProps;
	}
	
	public static UserEntity fromJSON(final byte[] userBytes) {
		try {
			return new ObjectMapper().readValue(new ByteArrayInputStream(userBytes), UserEntity.class);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	public static byte[] toJSON(UserEntity user) {
		try {
			return new ObjectMapper().writeValueAsBytes(user);
		} catch (JsonProcessingException e) {
			throw new IllegalStateException(e);
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static byte[] toJSON(String value) {
		try {
				return new ObjectMapper().writeValueAsBytes(value);
		} catch (JsonProcessingException e) {
			throw new IllegalStateException(e);
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
	}

	public static String toBase64(byte[] content) {
		return DatatypeConverter.printBase64Binary(content);
	}

	public static byte[] fromBase64(String content) {
		return DatatypeConverter.parseBase64Binary(content);
	}
}
