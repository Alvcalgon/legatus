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
import org.springframework.util.StringUtils;

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

	public Map<String, List<Object>> findBySeason(String season, Optional<Integer> selectedPage,
			Optional<Integer> limit) {
		Map<String, List<Object>> results;
		String url;

		url = UtilityService.API_URI_PRE + "/constructor-standing/list/season/" + season;

		results = this.getDataPaginationAndObjects(url, selectedPage, limit);

		return results;
	}

	public Map<String, List<Object>> findByPosition(String position, Optional<Integer> selectedPage,
			Optional<Integer> limit) {
		Map<String, List<Object>> results;
		String url;

		url = UtilityService.API_URI_PRE + "/constructor-standing/list/position/" + position;

		results = this.getDataPaginationAndObjects(url, selectedPage, limit);

		return results;
	}

	public Map<String, List<Object>> findByConstructor(String constructor, Optional<Integer> selectedPage,
			Optional<Integer> limit) {
		Map<String, List<Object>> results;
		String url;

		url = UtilityService.API_URI_PRE + "/constructor-standing/list/constructor/" + constructor;

		results = this.getDataPaginationAndObjects(url, selectedPage, limit);

		return results;
	}
	
	private Map<String, List<Object>> getDataPaginationAndObjects(String url, Optional<Integer> selectedPage,
			Optional<Integer> limit) {
		Map<String, List<Object>> results;
		List<LinkedHashMap<String, Object>> ls_map_constructorsStanding;
		int totalPages, totalElements, valid_limit, valid_selectedPage, targetPage;
		String season, position, constructorName, constructorCountry, constructorPrincipal;
		Integer points;
		Constructor constructor;
		Map<String, Object> map_json, temp;
		List<Object> constructorsStanding;
		List<Object> dataPage;
		ConstructorStanding cs;
		LinkedHashMap<String, Object> o;

		try {
			temp = this.utilityService.mapJSON(url, 0, 2);
			totalElements = (int) temp.get("totalElements");

			// Validamos campos de la paginacion
			valid_limit = this.utilityService.getValidLimit(limit, totalElements);
			valid_selectedPage = this.utilityService.getValidOffset(selectedPage,
																	valid_limit,
																	totalElements);

			targetPage = valid_selectedPage - 1;
			map_json = this.utilityService.mapJSON(url, targetPage, valid_limit);

			results = new HashMap<String, List<Object>>();

			dataPage = new ArrayList<Object>();
			constructorsStanding = new ArrayList<Object>();

			if (!map_json.isEmpty()) {
				ls_map_constructorsStanding = (List<LinkedHashMap<String, Object>>) map_json.get("content");

				if (!ls_map_constructorsStanding.isEmpty()) {
					for (LinkedHashMap<String, Object> mapDriver : ls_map_constructorsStanding) {
						// Atributos de constructorStanding
						season = (String) mapDriver.get("season");
						position = (String) mapDriver.get("position");
						points = (Integer) mapDriver.get("points");
						o = (LinkedHashMap<String, Object>) mapDriver.get("constructor");
						
						// Atributos de ConstructorStanding::constructor
						constructorName = (String) o.get("name");
						constructorCountry = (String) o.get("country");
						constructorPrincipal = (StringUtils.isEmpty(o.get("principal")))
								? null : (String) o.get("principal"); 
						
						constructor = new Constructor(constructorName,
													  constructorCountry,
													  constructorPrincipal);
		
						cs = new ConstructorStanding(season, position,
													 points, constructor);

						constructorsStanding.add(cs);
					}
				}

				totalPages = (int) map_json.get("totalPages");
				totalElements = (int) map_json.get("totalElements");
				
				dataPage = this.utilityService.fillDataPage(totalPages,
															totalElements, 
															valid_limit,
															valid_selectedPage);
			}

			results.put("constructorsStanding", constructorsStanding);
			results.put("dataPage", dataPage);

		} catch (Throwable oops) {
			log.info("Algo fue mal al recuperar los objetos y datos de la paginacion: " + oops.getMessage());

			results = new HashMap<String, List<Object>>();

			constructorsStanding = new ArrayList<Object>();
			dataPage = this.utilityService.fillDataPage(-1, -1, 10, 1);

			results.put("constructorsStanding", constructorsStanding);
			results.put("dataPage", dataPage);
		}

		return results;
	}

}
