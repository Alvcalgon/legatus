package com.tfg.motorsportf1.appweb.motorsportf1.services;

import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.motorsportf1.appweb.motorsportf1.bean.ConstructorJson;
import com.tfg.motorsportf1.appweb.motorsportf1.domain.Constructor;

@Service
public class ConstructorService {

	private static final Log log = LogFactory.getLog(ConstructorService.class);

	@Autowired
	private UtilityService utilityService;
	
	public ConstructorService() {
		super();
	}

	public Constructor findOne(String name) {
		Constructor result;
		String encodedName, url;

		try {
			encodedName = this.utilityService.getEncodedText(name);

			url = UtilityService.API_URI + "/constructor/display/" + encodedName;

			result = this.utilityService.getObjectFromJSON(url, Constructor.class);

		} catch (Throwable oops) {
			result = null;

			log.info("Error al recuperar el piloto cuyo nombre completo es: " + name);
		}

		return result;
	}
	
	public ConstructorJson findAll() {
		return this.findAll(Optional.of(UtilityService.DEFAULT_OFFSET_TO_USER));
	}

	
	public ConstructorJson findAll(Optional<Integer> selectedPage) {
		String url = UtilityService.API_URI + "/constructor/list";
		
		url = this.getURI(url, selectedPage);
		
		ConstructorJson result = this.utilityService.getObjectFromJSON(url, ConstructorJson.class);
		
		return result;
	}
	
	public ConstructorJson findByNationality(String nationality, Optional<Integer> selectedPage) {
		ConstructorJson result;
		String url, encodedNationality;

		encodedNationality = this.utilityService.getEncodedText(nationality);

		url = UtilityService.API_URI + "/constructor/list/nationality/" + encodedNationality;
		url = this.getURI(url, selectedPage);
		
		result = this.utilityService.getObjectFromJSON(url, ConstructorJson.class);

		return result;
	}

	public ConstructorJson findByName(String name, Optional<Integer> selectedPage) {
		ConstructorJson result;

		String encodedName = this.utilityService.getEncodedText(name);
		String url = UtilityService.API_URI + "/constructor/list/name/" + encodedName;
		
		url = this.getURI(url, selectedPage);
		
		result = this.utilityService.getObjectFromJSON(url, ConstructorJson.class);

		return result;
	}

	public ConstructorJson findByParameters(String name, String nationality, Optional<Integer> selectedPage) {
		ConstructorJson result;
		String url;

		String encodedName = this.utilityService.getEncodedText(name);
		String encodedNationality = this.utilityService.getEncodedText(nationality);

		url = UtilityService.API_URI + "/constructor/list/nationality/" + encodedNationality + "/name/" + encodedName;

		result = this.utilityService.getObjectFromJSON(url, ConstructorJson.class);

		return result;
	}
	
	private String getURI(String uri, Optional<Integer> selectedPage) {
		ConstructorJson result = this.utilityService.getObjectFromJSON(uri, ConstructorJson.class);
		
		int totalElements = result.getTotalElements();
			
		uri = this.utilityService.getURI(uri, selectedPage, totalElements);
		
		return uri;
	}
	
}
