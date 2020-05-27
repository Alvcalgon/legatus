package com.tfg.motorsportf1.appweb.motorsportf1.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.motorsportf1.appweb.motorsportf1.bean.ConstructorStandingJson;

@Service
public class ConstructorStandingService {

	//private static final Log log = LogFactory.getLog(ConstructorStandingService.class);

	@Autowired
	private UtilityService utilityService;

	
	public ConstructorStandingService() {
		super();
	}

	
	public ConstructorStandingJson findBySeason(String season) {
		return this.findBySeason(season, Optional.of(UtilityService.DEFAULT_OFFSET_TO_USER));
	}
	
	public ConstructorStandingJson findBySeason(String season, Optional<Integer> selectedPage) {
		String url = UtilityService.API_URI + "/constructor-standing/list/season/" + season;

		url = this.getURI(url, selectedPage);
		
		ConstructorStandingJson result = this.utilityService.getObjectFromJSON(url, ConstructorStandingJson.class);

		return result;
	}

	public ConstructorStandingJson findByPosition(String position,
													Optional<Integer> selectedPage) {
		String uri = UtilityService.API_URI + "/constructor-standing/list/position/" + position;

		uri = this.getURI(uri, selectedPage);
		
		ConstructorStandingJson result = this.utilityService.getObjectFromJSON(uri, ConstructorStandingJson.class);

		return result;
	}

	public ConstructorStandingJson findByConstructor(String constructor,
													   Optional<Integer> selectedPage) {
		String uri = UtilityService.API_URI + "/constructor-standing/list/constructor/" + constructor;

		uri = this.getURI(uri, selectedPage);
		
		ConstructorStandingJson results = this.utilityService.getObjectFromJSON(uri, ConstructorStandingJson.class);

		return results;
	}
	
	public Integer findCountByConstructor(String constructor) {
		Integer result;
		String url;
		String encoded_constructor;
		
		encoded_constructor = this.utilityService.getEncodedText(constructor);
		
		url = UtilityService.API_URI + "/constructor-standing/count/constructor/"
				+ encoded_constructor;
		
		result = this.utilityService.countJSON(url);
		
		return result;
	}
	
	public Integer findCountByConstructorAndPosition(String constructor, String position) {
		Integer result;
		String url;
		String encoded_constructor;
		
		encoded_constructor = this.utilityService.getEncodedText(constructor);
		
		url = UtilityService.API_URI + "/constructor-standing/count/constructor/"
				+ encoded_constructor + "/position/" + position;
		
		result = this.utilityService.countJSON(url);
		
		return result;
	}
	
	public Integer findDriversTitlesByConstructorAPI(String constructor) {
		Integer result;
		String url;
		String encoded_constructor;
		
		encoded_constructor = this.utilityService.getEncodedText(constructor);
		
		url = UtilityService.API_URI + "/constructor-standing/drivers-titles/"
				+ encoded_constructor;
		
		result = this.utilityService.countJSON(url);
		
		return result;
	}
	
	private String getURI(String uri, Optional<Integer> selectedPage) {
		ConstructorStandingJson result = this.utilityService.getObjectFromJSON(uri, ConstructorStandingJson.class);
		
		int totalElements = result.getTotalElements();
			
		uri = this.utilityService.getURI(uri, selectedPage, totalElements);
		
		return uri;
	}
	
}
