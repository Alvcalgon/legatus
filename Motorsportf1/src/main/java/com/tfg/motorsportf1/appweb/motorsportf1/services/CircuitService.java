package com.tfg.motorsportf1.appweb.motorsportf1.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tfg.motorsportf1.appweb.motorsportf1.bean.CircuitJson;
import com.tfg.motorsportf1.appweb.motorsportf1.domain.Circuit;

@Service
public class CircuitService {

	//private static final Log log = LogFactory.getLog(CircuitService.class);

	@Autowired
	private UtilityService utilityService;

	public CircuitService() {
		super();
	}

	
	public CircuitJson findAll() {
		return this.findAll(Optional.of(UtilityService.DEFAULT_OFFSET_TO_USER));
	}
	
	public CircuitJson findAll(Optional<Integer> selectedPage) {
		String uri = UtilityService.API_URI + "/circuit/list";
				
		uri = this.getURI(uri, selectedPage);
		
		CircuitJson result = this.utilityService.getObjectFromJSON(uri, CircuitJson.class);
		
		return result;
	}

	public CircuitJson findByLocation(String location, Optional<Integer> selectedPage) {
		String encodedLocation = this.utilityService.getEncodedText(location);
		
		String uri = UtilityService.API_URI + "/circuit/list/location/" + encodedLocation;
		
		uri = this.getURI(uri, selectedPage);
		
		CircuitJson result = this.utilityService.getObjectFromJSON(uri, CircuitJson.class);
		
		return result;
	}

	public List<Circuit> findBySeason(String season) {
		List<Circuit> circuits;
		
		String url = UtilityService.API_URI + "/circuit/list/season/" + season;
		String strJSON = this.utilityService.getStringOfJSON(url);
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
				
		Circuit[] results = gson.fromJson(strJSON, Circuit[].class);
		
		circuits = (results.length > 0) ? Arrays.asList(results) : new ArrayList<Circuit>();
		
		return circuits;
	}
		
	public CircuitJson findByAllParameters(String location, String name,
										   Optional<Integer> selectedPage) {		
		String encodedLocation = this.utilityService.getEncodedText(location);
		String encodedName = this.utilityService.getEncodedText(name);
		
		String uri = UtilityService.API_URI + "/circuit/list/location/" 
											+ encodedLocation + "/name/" + encodedName;
		uri = this.getURI(uri, selectedPage);
		
		CircuitJson result = this.utilityService.getObjectFromJSON(uri, CircuitJson.class);
		
		return result;
	}
		
	public CircuitJson findByName(String name, Optional<Integer> selectedPage) {
		String encodedName = this.utilityService.getEncodedText(name);
		
		String uri = UtilityService.API_URI + "/circuit/list/name/" + encodedName;
		
		uri = this.getURI(uri, selectedPage);
		
		CircuitJson result = this.utilityService.getObjectFromJSON(uri, CircuitJson.class);
		
		return result;
	}
	
	private String getURI(String uri, Optional<Integer> selectedPage) {
		CircuitJson result = this.utilityService.getObjectFromJSON(uri, CircuitJson.class);
		
		int totalElements = result.getTotalElements();
			
		uri = this.utilityService.getURI(uri, selectedPage, totalElements);
		
		return uri;
	}
	
}
