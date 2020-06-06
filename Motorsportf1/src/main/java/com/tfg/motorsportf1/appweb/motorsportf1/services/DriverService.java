package com.tfg.motorsportf1.appweb.motorsportf1.services;

import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.motorsportf1.appweb.motorsportf1.bean.DriverJson;
import com.tfg.motorsportf1.appweb.motorsportf1.domain.Driver;

@Service
public class DriverService {

	private static final Log log = LogFactory.getLog(DriverService.class);

	@Autowired
	private UtilityService utilityService;
	
	@Autowired
	private ResultService resultService;
	
	public DriverService() {
		super();
	}

	
	public Integer getPodiums(String fullname) {
		Integer result, first, second, third;
		
		first = this.resultService.findCountByPositionAndDriver(fullname, "1");
		second = this.resultService.findCountByPositionAndDriver(fullname, "2");
		third = this.resultService.findCountByPositionAndDriver(fullname, "3");
		
		result =  first + second + third;
		
		return result;
	}

	public Driver findOne(String fullname) {
		Driver result = null;
		
		try {			
			String encodedFullname = this.utilityService.getEncodedText(fullname);
			
			String url = UtilityService.API_URI + "/driver/display/" + encodedFullname;
			
			result = this.utilityService.getObjectFromJSON(url, Driver.class);
		} catch (Throwable oops) {
			log.info("Error al recuperar el piloto cuyo nombre completo es: " + fullname);
		}

		return result;
	}

	public DriverJson findAll() {
		return this.findAll(Optional.of(UtilityService.DEFAULT_OFFSET_TO_USER));
	}
	
	public DriverJson findAll(Optional<Integer> selectedPage) {
		String uri = UtilityService.API_URI + "/driver/list";

		uri = this.getURI(uri, selectedPage);
		
		DriverJson results = this.utilityService.getObjectFromJSON(uri, DriverJson.class);

		return results;
	}

	public DriverJson findByNationality(String nationality, Optional<Integer> selectedPage) {
		String encodedNationality = this.utilityService.getEncodedText(nationality);
		
		String uri = UtilityService.API_URI + "/driver/list/nationality/" + encodedNationality;
		
		uri = this.getURI(uri, selectedPage);
		
		DriverJson result = this.utilityService.getObjectFromJSON(uri, DriverJson.class);
		
		return result;
	}

	public DriverJson findByFullname(String fullname, Optional<Integer> selectedPage) {
		String encodedFullname = this.utilityService.getEncodedText(fullname);
		
		String uri = UtilityService.API_URI + "/driver/list/fullname/" + encodedFullname;
		
		uri = this.getURI(uri, selectedPage);
		
		DriverJson result = this.utilityService.getObjectFromJSON(uri, DriverJson.class);

		return result;
	}
	
	public DriverJson findByParameters(String fullname, String nationality,
			Optional<Integer> selectedPage) {
		String encodedFullname = this.utilityService.getEncodedText(fullname);
		String encodedNationality = this.utilityService.getEncodedText(nationality);
		
		String uri = UtilityService.API_URI + "/driver/list/nationality/" + encodedNationality
											+ "/fullname/" + encodedFullname;
		
		uri = this.getURI(uri, selectedPage);
		
		DriverJson results = this.utilityService.getObjectFromJSON(uri, DriverJson.class);

		return results;
	}
	
	private String getURI(String uri, Optional<Integer> selectedPage) {
		DriverJson result = this.utilityService.getObjectFromJSON(uri, DriverJson.class);
		
		int totalElements = result.getTotalElements();
			
		uri = this.utilityService.getURI(uri, selectedPage, totalElements);
		
		return uri;
	}
	
}
