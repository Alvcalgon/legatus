package com.tfg.motorsportf1.appweb.motorsportf1.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.motorsportf1.appweb.motorsportf1.domain.Constructor;
import com.tfg.motorsportf1.appweb.motorsportf1.domain.ConstructorStanding;

@Service
public class ConstructorStandingService {

	private static final Log log = LogFactory.getLog(ConstructorStandingService.class);

	@Autowired
	private UtilityService utilityService;

	public ConstructorStandingService() {
		super();
	}

	
	public Map<String, List<Object>> findBySeason(String season) {
		Map<String, List<Object>> results;
		String url;

		url = UtilityService.API_URI_PRE + "/constructor-standing/list/season/" + season;

		results = this.getDataPaginationAndObjects(url, 
				Optional.of(UtilityService.DEFAULT_OFFSET_TO_USER));

		return results;
	}
	
	public Map<String, List<Object>> findBySeason(String season, Optional<Integer> selectedPage) {
		Map<String, List<Object>> results;
		String url;

		url = UtilityService.API_URI_PRE + "/constructor-standing/list/season/" + season;

		results = this.getDataPaginationAndObjects(url, selectedPage);

		return results;
	}

	public Map<String, List<Object>> findByPosition(String position,
													Optional<Integer> selectedPage) {
		Map<String, List<Object>> results;
		String url;

		url = UtilityService.API_URI_PRE + "/constructor-standing/list/position/" + position;

		results = this.getDataPaginationAndObjects(url, selectedPage);

		return results;
	}

	public Map<String, List<Object>> findByConstructor(String constructor,
													   Optional<Integer> selectedPage) {
		Map<String, List<Object>> results;
		String url;

		url = UtilityService.API_URI_PRE + "/constructor-standing/list/constructor/" + constructor;

		results = this.getDataPaginationAndObjects(url, selectedPage);

		return results;
	}
	
	public Integer findCountByConstructor(String constructor) {
		Integer result;
		String url;
		String encoded_constructor;
		
		encoded_constructor = this.utilityService.getEncodedText(constructor);
		
		url = UtilityService.API_URI_PRE + "/constructor-standing/count/constructor/"
				+ encoded_constructor;
		
		result = this.utilityService.countJSON(url);
		
		return result;
	}
	
	public Integer findCountByConstructorAndPosition(String constructor, String position) {
		Integer result;
		String url;
		String encoded_constructor;
		
		encoded_constructor = this.utilityService.getEncodedText(constructor);
		
		url = UtilityService.API_URI_PRE + "/constructor-standing/count/constructor/"
				+ encoded_constructor + "/position/" + position;
		
		result = this.utilityService.countJSON(url);
		
		return result;
	}
	
	public Integer findDriversTitlesByConstructorAPI(String constructor) {
		Integer result;
		String url;
		String encoded_constructor;
		
		encoded_constructor = this.utilityService.getEncodedText(constructor);
		
		url = UtilityService.API_URI_PRE + "/constructor-standing/drivers-titles/"
				+ encoded_constructor;
		
		result = this.utilityService.countJSON(url);
		
		return result;
	}
	
	
	private Map<String, List<Object>> getDataPaginationAndObjects(String url, Optional<Integer> selectedPage) {
		Map<String, List<Object>> results;
		List<LinkedHashMap<String, Object>> ls_map_constructorsStanding;
		int totalPages, totalElements, valid_selectedPage, targetPage;
		String season, position, constructorName, constructorNationality, constructorInfo;
		Integer wins, points;
		Constructor constructor;
		Map<String, Object> map_json, temp;
		List<Object> constructorsStanding;
		List<Object> dataPage;
		ConstructorStanding cs;
		LinkedHashMap<String, Object> o;

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
			constructorsStanding = new ArrayList<Object>();

			if (!map_json.isEmpty()) {
				ls_map_constructorsStanding = (List<LinkedHashMap<String, Object>>) map_json.get("content");

				if (!ls_map_constructorsStanding.isEmpty()) {
					for (LinkedHashMap<String, Object> mapConstructorStd : ls_map_constructorsStanding) {
						// Atributos de constructorStanding
						season = (String) mapConstructorStd.get("season");
						position = (String) mapConstructorStd.get("position");
						points = (Integer) mapConstructorStd.get("points");
						wins = (Integer) mapConstructorStd.get("wins");
						o = (LinkedHashMap<String, Object>) mapConstructorStd.get("constructor");
						
						// Atributos de ConstructorStanding::constructor
						constructorName = (String) o.get("name");
						constructorNationality = (String) o.get("nationality");
						constructorInfo = (String) o.get("information");
						
						constructor = new Constructor(constructorName,
													  constructorNationality,
													  constructorInfo);
		
						cs = new ConstructorStanding(season, position, points, wins, constructor);

						constructorsStanding.add(cs);
					}
				}

				totalPages = (int) map_json.get("totalPages");
				totalElements = (int) map_json.get("totalElements");
				
				dataPage = this.utilityService.fillDataPage(totalPages,
															totalElements,
															valid_selectedPage);
			}

			results.put("constructorsStanding", constructorsStanding);
			results.put("dataPage", dataPage);

		} catch (Throwable oops) {
			log.info("ConstructorStandingService::getDataPaginationAndObjects - Algo fue mal al"
					+ " recuperar los objetos y datos de la paginacion: " + oops.getMessage());

			results = new HashMap<String, List<Object>>();

			constructorsStanding = new ArrayList<Object>();
			dataPage = this.utilityService.fillDataPage(-1,
														-1,
														UtilityService.DEFAULT_OFFSET_TO_USER);

			results.put("constructorsStanding", constructorsStanding);
			results.put("dataPage", dataPage);
		}

		return results;
	}

}
