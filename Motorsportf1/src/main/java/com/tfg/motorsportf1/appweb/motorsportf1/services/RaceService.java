package com.tfg.motorsportf1.appweb.motorsportf1.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.motorsportf1.appweb.motorsportf1.domain.Circuit;
import com.tfg.motorsportf1.appweb.motorsportf1.domain.Race;

@Service
public class RaceService {

	private static final Log log = LogFactory.getLog(RaceService.class);
	
	@Autowired
	private UtilityService utilityService;
	
	public RaceService() {
		super();
	}

	
	public Object findOne(String season, String event) {
		Object result;
		
		String encodedSeason, encodedEvent, url;

		try {
			encodedSeason = this.utilityService.getEncodedText(season);
			encodedEvent = this.utilityService.getEncodedText(event);

			url = UtilityService.API_URI_PRE + "/race//display/season/" +
					encodedSeason +  "/event/" + encodedEvent;

			result = this.utilityService.stringMapJSON(url);
		} catch (Throwable oops) {
			result = null;

			log.info("RaceService::findOne - Error al recuperar la carrera por temporada y evento.");
		}
		
		return result;
	}
	
	public Map<String, List<Object>> findBySeason(String season, Optional<Integer> selectedPage) {
		Map<String, List<Object>> results;
		String url, encodedSeason;
		
		encodedSeason = this.utilityService.getEncodedText(season);
		
		url = UtilityService.API_URI_PRE + "/race/list/season/" + encodedSeason;
		
		results = this.getDataPaginationAndObjects(url, selectedPage);
		
		return results;
	}
	
	public Map<String, List<Object>> findByCircuit(String circuit, Optional<Integer> selectedPage) {
		Map<String, List<Object>> results;
		String url;

		url = UtilityService.API_URI_PRE + "/race/list/circuit/" + circuit;

		results = this.getDataPaginationAndObjects(url, selectedPage);

		return results;
	}
	
	public Map<String, List<Object>> findByDriver(String driver, Optional<Integer> selectedPage) {
		Map<String, List<Object>> results;
		String url;

		url = UtilityService.API_URI_PRE + "/race/list/driver/" + driver;

		results = this.getDataPaginationAndObjects(url, selectedPage);

		return results;
	}
	
	public Map<String, List<Object>> findByConstructor(String constructor, Optional<Integer> selectedPage) {
		Map<String, List<Object>> results;
		String url;

		url = UtilityService.API_URI_PRE + "/race/list/constructor/" + constructor;

		results = this.getDataPaginationAndObjects(url, selectedPage);

		return results;
	}
	
	public Map<String, List<Object>> findByDriverAndSeason(String driver, String season, Optional<Integer> selectedPage) {
		Map<String, List<Object>> results;
		String url;

		url = UtilityService.API_URI_PRE + "/race/list/driver/" + driver + "/season/" + season;

		results = this.getDataPaginationAndObjects(url, selectedPage);

		return results;
	}
	
	public Map<String, List<Object>> findByConstructorAndSeason(String constructor, String season, Optional<Integer> selectedPage) {
		Map<String, List<Object>> results;
		String url;

		url = UtilityService.API_URI_PRE + "/race/list/constructor/" + constructor + "/season/" + season;

		results = this.getDataPaginationAndObjects(url, selectedPage);

		return results;
	}
	
	public Map<String, List<Object>> findByEvent(String event, Optional<Integer> selectedPage) {
		Map<String, List<Object>> results;
		String url;

		url = UtilityService.API_URI_PRE + "/race/list/event/" + event;

		results = this.getDataPaginationAndObjects(url, selectedPage);

		return results;
	}
	
	public Map<String, List<Object>> findBySeasonAndEvent(String season, String event, Optional<Integer> selectedPage) {
		Map<String, List<Object>> results;
		String url;

		url = UtilityService.API_URI_PRE + "/race/list/season/" + season + "/event/" + event;

		results = this.getDataPaginationAndObjects(url, selectedPage);

		return results;
	}
	
	private List<Object> getDataObjects(String url) {
		List<LinkedHashMap<String, Object>> list_json;
		String season, str_raceDate, event;
		String name, location, type, lapDistance;
		LinkedHashMap<String, Object> o;
		Date raceDate;
		Circuit circuit;
		List<Object> races;
		Race race;

		try {
			list_json = this.utilityService.listJSON2(url);

			races = new ArrayList<Object>();

			if (!list_json.isEmpty()) {
				for (LinkedHashMap<String, Object> mapRace : list_json) {
					season = this.utilityService.getStringFromKey2(mapRace, "season");
					
					str_raceDate = this.utilityService.getStringFromKey2(mapRace, "raceDate");
					raceDate = this.utilityService.getDateFromString(str_raceDate);
					
					event = this.utilityService.getStringFromKey2(mapRace, "event");
					
					circuit = null;
					
					if (mapRace.containsKey("circuit")) {
						o = (LinkedHashMap<String, Object>) mapRace.get("circuit");
						
						name = this.utilityService.getStringFromKey2(o, "name");
						location = this.utilityService.getStringFromKey2(o, "location");
						type = this.utilityService.getStringFromKey2(o, "type");
						lapDistance = this.utilityService.getStringFromKey2(o, "lapDistance");
					
						circuit = new Circuit(name, location, type, lapDistance);
					}
					
					race = new Race(season, raceDate, event, circuit);
					races.add(race);
				}
			}
			
		} catch (Throwable oops) {
			log.info(
					"CircuitService::getDataPaginationAndObjects - Algo fue mal al recuperar los objetos y datos de la paginacion: "
							+ oops.getMessage());

			races = new ArrayList<Object>();
		}

		return races;
	}
	
	private Map<String, List<Object>> getDataPaginationAndObjects(String url, Optional<Integer> selectedPage) {
		Map<String, List<Object>> results;
		List<LinkedHashMap<String, Object>> ls_map_races;
		int totalPages, totalElements, valid_selectedPage, targetPage;
		String name, location, type, lapDistance;
		String season, str_raceDate, event;
		Map<String, Object> map_json, temp;
		LinkedHashMap<String, Object> o;
		List<Object> races;
		List<Object> dataPage;
		Circuit circuit;
		Date raceDate;
		Race race;
		
		try {
			temp = this.utilityService.mapJSON(url, 0);
			totalElements = (int) temp.get("totalElements");

			// Validamos offset de la paginacion
			valid_selectedPage = this.utilityService.getValidOffset(selectedPage,
																	totalElements);

			targetPage = valid_selectedPage - 1;
			map_json = this.utilityService.mapJSON(url, targetPage);

			results = new HashMap<String, List<Object>>();

			dataPage = new ArrayList<Object>();
			races = new ArrayList<Object>();

			if (!map_json.isEmpty()) {
				ls_map_races = (List<LinkedHashMap<String, Object>>) map_json.get("content");

				if (!ls_map_races.isEmpty()) {
					for (LinkedHashMap<String, Object> mapRace : ls_map_races) {
						season = this.utilityService.getStringFromKey2(mapRace, "season");
						
						str_raceDate = this.utilityService.getStringFromKey2(mapRace, "raceDate");
						raceDate = this.utilityService.getDateFromString(str_raceDate);
						
						event = this.utilityService.getStringFromKey2(mapRace, "event");
						
						circuit = null;
						
						if (mapRace.containsKey("circuit")) {
							o = (LinkedHashMap<String, Object>) mapRace.get("circuit");
							
							name = this.utilityService.getStringFromKey2(o, "name");
							location = this.utilityService.getStringFromKey2(o, "location");
							type = this.utilityService.getStringFromKey2(o, "type");
							lapDistance = this.utilityService.getStringFromKey2(o, "lapDistance");
						
							circuit = new Circuit(name, location, type, lapDistance);
						}
						
						race = new Race(season, raceDate, event, circuit);
						races.add(race);
					}
				}

				totalPages = (int) map_json.get("totalPages");
				totalElements = (int) map_json.get("totalElements");
				
				dataPage = this.utilityService.fillDataPage(totalPages,
															totalElements,
															valid_selectedPage);
			}

			results.put("races", races);
			results.put("dataPage", dataPage);

		} catch (Throwable oops) {
			log.info("RaceService::getDataPaginationAndObjects - Algo fue mal al recuperar los objetos y datos de la paginacion: " + oops.getMessage());

			results = new HashMap<String, List<Object>>();

			races = new ArrayList<Object>();
			dataPage = this.utilityService.fillDataPage(-1, -1, UtilityService.DEFAULT_OFFSET_TO_USER);

			results.put("races", races);
			results.put("dataPage", dataPage);
		}

		return results;
	}
	
}
