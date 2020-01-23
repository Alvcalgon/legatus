package com.tfg.motorsportf1.appweb.motorsportf1.services;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.tfg.motorsportf1.appweb.motorsportf1.domain.Circuit;

@Service
public class CircuitService {

	private static final Log log = LogFactory.getLog(CircuitService.class);
	
	public CircuitService() {
		super();
	}
	
	public List<Circuit> findAll() {
		
		log.info("Circuitos recuperados");
		return null;
	}
	
}
