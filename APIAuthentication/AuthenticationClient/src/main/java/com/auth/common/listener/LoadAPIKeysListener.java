package com.auth.common.listener;


import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;

import com.auth.common.constants.HTTPHeaderNames;
import com.auth.common.service.TokenService;


@WebListener
public class LoadAPIKeysListener implements ServletContextListener{

	final static Logger log = Logger.getLogger(LoadAPIKeysListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		log.info("AuthenticationService :  LoadAPIKeysListener  context initialized");
		ServletContext servletContext = sce.getServletContext();
		servletContext.setAttribute("configProperties", loadConfigurations(servletContext));
		TokenService tokenService = new TokenService(servletContext);	
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		log.info("AuthenticationService :  LoadAPIKeysListener  context desrtoyed");
	}

	public Properties loadConfigurations(ServletContext servletContext){
		Properties configProperties = new Properties();		
		try {
			//Ensure that this is set in the Tomcat/bin/catalina.sh file
			//Ex: CATALINA_OPTS="-Denv=dev"
			String environment = System.getProperty("env");
			configProperties.load(servletContext.getResourceAsStream("/WEB-INF/classes/config-"+environment+".properties"));
		} catch (IOException e) {
			e.printStackTrace();
			log.error("Error opening the config properties. Error message :"+e.getMessage());
		} 
		return configProperties;
	}
	
	
}