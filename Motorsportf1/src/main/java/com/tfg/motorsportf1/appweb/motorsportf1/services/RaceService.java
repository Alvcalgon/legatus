package com.tfg.motorsportf1.appweb.motorsportf1.services;

import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.motorsportf1.appweb.motorsportf1.bean.RaceJson;
import com.tfg.motorsportf1.appweb.motorsportf1.domain.Race;

@Service
public class RaceService {

	private static final Log log = LogFactory.getLog(RaceService.class);
	
	@Autowired
	private UtilityService utilityService;
	
	
	public RaceService() {
		super();
	}

	
	public Race findOne(String season, String event) {
		Race result = null;
		
		try {
			String encodedSeason = this.utilityService.getEncodedText(season);
			String encodedEvent = this.utilityService.getEncodedText(event);

			String url = UtilityService.API_URI + "/race//display/season/" +
					encodedSeason +  "/event/" + encodedEvent;

			result = this.utilityService.getObjectFromJSON(url, Race.class);
		} catch (Throwable oops) {
			log.info("RaceService::findOne - Error al recuperar la carrera por temporada y evento.");
		}
		
		return result;
	}
	
	public RaceJson findBySeason(String season) {
		return this.findBySeason(season,
				     Optional.of(UtilityService.DEFAULT_OFFSET_TO_USER));
	}
	
	public RaceJson findBySeason(String season, Optional<Integer> selectedPage) {
		String encodedSeason = this.utilityService.getEncodedText(season);
		
		String uri = UtilityService.API_URI + "/race/list/season/" + encodedSeason;
		
		uri = this.getURI(uri, selectedPage);
		
		RaceJson results = this.utilityService.getObjectFromJSON(uri, RaceJson.class);
		
		return results;
	}
	
	public RaceJson findByCircuit(String circuit, Optional<Integer> selectedPage) {
		String uri = UtilityService.API_URI + "/race/list/circuit/" + circuit;

		uri = this.getURI(uri, selectedPage);
		
		RaceJson results = this.utilityService.getObjectFromJSON(uri, RaceJson.class);
		
		return results;
	}
	
	public RaceJson findByEvent(String event, Optional<Integer> selectedPage) {
		String uri = UtilityService.API_URI + "/race/list/event/" + event;

		uri = this.getURI(uri, selectedPage);
		
		RaceJson results = this.utilityService.getObjectFromJSON(uri, RaceJson.class);

		return results;
	}
	
	public RaceJson findBySeasonAndEvent(String season, String event, Optional<Integer> selectedPage) {
		String uri = UtilityService.API_URI + "/race/list/season/" + season + "/event/" + event;

		uri = this.getURI(uri, selectedPage);
		
		RaceJson results = this.utilityService.getObjectFromJSON(uri, RaceJson.class);

		return results;
	}
	
	private String getURI(String uri, Optional<Integer> selectedPage) {
		RaceJson result = this.utilityService.getObjectFromJSON(uri, RaceJson.class);
		
		int totalElements = result.getTotalElements();
			
		uri = this.utilityService.getURI(uri, selectedPage, totalElements);
		
		return uri;
	}
	
}
