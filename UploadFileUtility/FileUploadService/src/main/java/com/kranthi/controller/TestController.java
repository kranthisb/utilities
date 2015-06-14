package com.kranthi.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;


@Path("/")
public class TestController {	
	final static Logger log = Logger.getLogger(TestController.class);
	
	private final String UPLOADED_FILE_PATH = "/tmp/";
	
	@POST
	@Path("/upload") 
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes("multipart/form-data")
    public Response demoTestTemplateServiceCall(MultipartFormDataInput input) {
		
				
		String fileName = "";
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		List<InputPart> inputParts = uploadForm.get("file");
		log.debug("uploadForm : "+uploadForm);
		
		for (InputPart inputPart : inputParts) {
			 try {
				MultivaluedMap<String, String> header = inputPart.getHeaders();
				fileName = getFileName(header);
	 			InputStream inputStream = inputPart.getBody(InputStream.class,null);
	 			byte [] bytes = IOUtils.toByteArray(inputStream);
	 			log.debug("fileName : "+fileName);
	 			
	 			fileName = UPLOADED_FILE_PATH + fileName;
	 
				writeFile(bytes,fileName);
		 } catch (IOException e) {
				e.printStackTrace();
			  } 
		}
		return Response
				.status(Status.OK)
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
				.header("Access-Control-Allow-Credentials", "true")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
				.header("Access-Control-Max-Age", "1209600")
				.build();
		
    }
	
	private String getFileName(MultivaluedMap<String, String> header) {
		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
		for (String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {
				String[] name = filename.split("=");
				String finalFileName = name[1].trim().replaceAll("\"", "");
				return finalFileName;
			}
		}
		return "unknown";
	}
		
	private void writeFile(byte[] content, String filename) throws IOException {
		File file = new File(filename);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream fop = new FileOutputStream(file);
		fop.write(content);
		fop.flush();
		fop.close();
	}
}