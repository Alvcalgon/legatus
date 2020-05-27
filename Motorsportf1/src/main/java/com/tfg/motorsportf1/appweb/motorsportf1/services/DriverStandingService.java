package com.tfg.motorsportf1.appweb.motorsportf1.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.motorsportf1.appweb.motorsportf1.bean.DriverStandingJson;

@Service
public class DriverStandingService {

	//private static final Log log = LogFactory.getLog(DriverStandingService.class);
	
	@Autowired
	private UtilityService utilityService;
	
	public DriverStandingService() {
		super();
	}
	
	
	public DriverStandingJson findBySeason(String season,
												  Optional<Integer> selectedPage) {
		String uri = UtilityService.API_URI + "/driver-standing/list/season/" + season;
		
		uri = this.getURI(uri, selectedPage);
		
		DriverStandingJson results = this.utilityService.getObjectFromJSON(uri, DriverStandingJson.class);

		return results;
	}
	
	public DriverStandingJson findByPosition(String position,
													Optional<Integer> selectedPage) {
		String uri = UtilityService.API_URI + "/driver-standing/list/position/" + position;

		uri = this.getURI(uri, selectedPage);
		
		DriverStandingJson results = this.utilityService.getObjectFromJSON(uri, DriverStandingJson.class);

		return results;
	}
	
	public DriverStandingJson findByDriver(String driver,
												  Optional<Integer> selectedPage) {	
		String encoded_driver = this.utilityService.getEncodedText(driver);

		String url = UtilityService.API_URI + "/driver-standing/list/driver/" + encoded_driver;

		DriverStandingJson result = this.utilityService.getObjectFromJSON(url, DriverStandingJson.class);

		return result;
	}
	
	public Integer findCountByDriver(String driver) {
		Integer result;
		String url;
		String encoded_driver;
		
		encoded_driver = this.utilityService.getEncodedText(driver);
		
		url = UtilityService.API_URI + "/driver-standing/count/driver/" + encoded_driver;
		
		result = this.utilityService.countJSON(url);
		
		return result;
	}
	
	public Integer findCountByDriverAndPosition(String driver, String position) {
		Integer result;
		String url;
		String encoded_driver;
		
		encoded_driver = this.utilityService.getEncodedText(driver);
		
		url = UtilityService.API_URI + "/driver-standing/count/driver/" + encoded_driver +
				"/position/" + position;
		
		result = this.utilityService.countJSON(url);
		
		return result;
	}
	
	private String getURI(String uri, Optional<Integer> selectedPage) {
		DriverStandingJson result = this.utilityService.getObjectFromJSON(uri, DriverStandingJson.class);
		
		int totalElements = result.getTotalElements();
			
		uri = this.utilityService.getURI(uri, selectedPage, totalElements);
		
		return uri;
	}
	
}
