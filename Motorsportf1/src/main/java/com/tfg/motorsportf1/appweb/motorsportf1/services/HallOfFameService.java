package com.tfg.motorsportf1.appweb.motorsportf1.services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.motorsportf1.appweb.motorsportf1.domain.Item;

@Service
public class HallOfFameService {

	private static final Log log = LogFactory.getLog(HallOfFameService.class);
	
	@Autowired
	private UtilityService utilityService;
	
	
	public HallOfFameService() {
		super();
	}
	
	
	public List<Object> findDriversTitle() {
		List<Object> results;
		String url;

		url = UtilityService.API_URI + "/driver-standing/list/winners";
		results = this.getDataObject(url, "driverFullname", "titles");
		
		return results;
	}
	
	public List<Object> findConstructorsTitle() {
		List<Object> results;
		String url;

		url = UtilityService.API_URI + "/constructor-standing/list/winners";
		results = this.getDataObject(url, "constructorName", "titles");
		
		return results;
	}
	
	public List<Object> findDriversVictories() {
		List<Object> results;
		String url;

		url = UtilityService.API_URI + "/result/list/victories";
		results = this.getDataObject(url, "driverFullname", "victories");
		
		return results;
	}
	
	private List<Object> getDataObject(String url, String attributeName, String attributeCount) {
		List<LinkedHashMap<String, Object>> list_json;
		List<Object> results;
		String name;
		Integer count;
		String s;
		Item item;
		
		try {
			list_json = this.utilityService.listJSON2(url);
			
			results = new ArrayList<Object>();
			
			if (!list_json.isEmpty()) {
				for (LinkedHashMap<String, Object> mapObject : list_json) {
					name = String.valueOf(this.utilityService.getStringFromKey2(mapObject,
																			   attributeName));
					
					s = String.valueOf(mapObject.get(attributeCount));
					count = Integer.valueOf(s);
					
					item = new Item(name, count);
					
					results.add(item);
				}
			}
				
		} catch (Throwable oops) {
			log.info("HallOfFameService::getDataObjects - Algo fue mal al recuperar los"
					+ " objetos y datos de la paginacion: "
					+ oops.getMessage());

			results = new ArrayList<Object>();
		}
		
		return results;
	}
	
}
