package com.tfg.motorsportf1.appweb.motorsportf1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultService {

	//private static final Log log = LogFactory.getLog(ResultService.class);
	
	@Autowired
	private UtilityService utilityService;
	
	
	public ResultService() {
		super();
	}

	public Integer findCountByPositionAndDriver(String driver, String position) {
		Integer result;
		String url;
		String encodedDriver;
		
		encodedDriver = this.utilityService.getEncodedText(driver);

		url = UtilityService.API_URI + "/result/count/driver/" + encodedDriver + "/position/" + position;

		result = this.utilityService.countJSON(url);

		return result;
	}

	public Integer findCountByGridAndDriver(String driver, String grid) {
		Integer result;
		String url;
		String encodedDriver;
		
		encodedDriver = this.utilityService.getEncodedText(driver);

		url = UtilityService.API_URI + "/result/count/driver/" + encodedDriver + "/grid/" + grid;

		result = this.utilityService.countJSON(url);

		return result;
	}
	
	public Integer findResultsByPositionAndConstructor(String constructor, String position) {
		Integer result;
		String url;
		String encodedConstructor;
		
		encodedConstructor = this.utilityService.getEncodedText(constructor);

		url = UtilityService.API_URI + "/result/count/constructor/" + encodedConstructor + "/position/" + position;

		result = this.utilityService.countJSON(url);

		return result;
	}
	
	public Integer findResultsByGridAndConstructor(String constructor, String grid) {
		Integer result;
		String url;
		String encodedConstructor;
		
		encodedConstructor = this.utilityService.getEncodedText(constructor);

		url = UtilityService.API_URI + "/result/count/constructor/" + encodedConstructor + "/grid/" + grid;

		result = this.utilityService.countJSON(url);

		return result;
	}
	
	public Integer findCountByDriver(String driver) {
		Integer result;
		String url;
		String encodedDriver;
		
		encodedDriver = this.utilityService.getEncodedText(driver);

		url = UtilityService.API_URI + "/result/count/driver/" + encodedDriver;

		result = this.utilityService.countJSON(url);

		return result;
	}

	public Integer findCountByConstructor(String constructor) {
		Integer result;
		String url;
		String encodedConstructor;
		
		encodedConstructor = this.utilityService.getEncodedText(constructor);

		url = UtilityService.API_URI + "/result/count/constructor/" + encodedConstructor;

		result = this.utilityService.countJSON(url);

		return result;
	}
	
}
