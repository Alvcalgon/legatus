package com.tfg.motorsportf1.appweb.motorsportf1.services;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultService {

	private static final Log log = LogFactory.getLog(ResultService.class);
	
	@Autowired
	private UtilityService utilityService;
	
	
	public ResultService() {
		super();
	}

	public Integer findResultsByPositionAndDriver(String driver, String position) {
		Integer result;
		String url;
		String encodedDriver;
		
		encodedDriver = this.utilityService.getEncodedText(driver);

		url = UtilityService.API_URI_PRE + "/result/list/driver/" + encodedDriver + "/position/" + position;

		result = this.getTotalElements(url);

		return result;
	}

	public Integer findResultsByGridAndDriver(String driver, String grid) {
		Integer result;
		String url;
		String encodedDriver;
		
		encodedDriver = this.utilityService.getEncodedText(driver);

		url = UtilityService.API_URI_PRE + "/result/list/driver/" + encodedDriver + "/grid/" + grid;

		result = this.getTotalElements(url);

		return result;
	}
	
	public Integer findResultsByPositionAndConstructor(String constructor, String position) {
		Integer result;
		String url;
		String encodedConstructor;
		
		encodedConstructor = this.utilityService.getEncodedText(constructor);

		url = UtilityService.API_URI_PRE + "/result/list/constructor/" + encodedConstructor + "/position/" + position;

		result = this.getTotalElements(url);

		return result;
	}
	
	public Integer findResultsByGridAndConstructor(String constructor, String grid) {
		Integer result;
		String url;
		String encodedConstructor;
		
		encodedConstructor = this.utilityService.getEncodedText(constructor);

		url = UtilityService.API_URI_PRE + "/result/list/constructor/" + encodedConstructor + "/grid/" + grid;

		result = this.getTotalElements(url);

		return result;
	}
	
	private Integer getTotalElements(String url) {
		Map<String, Object> temp;
		Integer result;

		try {
			temp = this.utilityService.mapJSON(url, 0);
			result = (int) temp.get("totalElements");

		} catch (Throwable oops) {
			log.info("ResultService::getTotalElements - Algo fue mal al recuperar los objetos y datos de la paginacion: " + oops.getMessage());

			result = null;
		}

		return result;
	}
	
}
