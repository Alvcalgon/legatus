package com.tfg.motorsportf1.appweb.motorsportf1.services;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.tfg.motorsportf1.appweb.motorsportf1.domain.Constructor;

@Service
public class ConstructorService {

	private static final Log log = LogFactory.getLog(ConstructorService.class);
	//private static final String URL_API = "http://localhost:8080/fone/constructor/list";
	
	public ConstructorService() {
		super();
	}
	
	public List<Constructor> findAll() {
		
		log.info("Escuderias recuperadas");
		
		return null;
	}
	
}
