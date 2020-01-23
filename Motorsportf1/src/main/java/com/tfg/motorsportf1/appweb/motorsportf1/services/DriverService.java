package com.tfg.motorsportf1.appweb.motorsportf1.services;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.tfg.motorsportf1.appweb.motorsportf1.domain.Driver;

@Service
public class DriverService {

	private static final Log log = LogFactory.getLog(DriverService.class); 
	
	public DriverService() {
		super();
	}
	
	public List<Driver> findAll() {
		
		log.info("Pilotos recuperados");
		return null;
	}
	
}
