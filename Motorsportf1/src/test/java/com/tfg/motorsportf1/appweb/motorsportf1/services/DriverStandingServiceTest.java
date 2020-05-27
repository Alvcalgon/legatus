package com.tfg.motorsportf1.appweb.motorsportf1.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tfg.motorsportf1.appweb.motorsportf1.bean.DriverStandingJson;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DriverStandingServiceTest {

	// Service under testing ----------------------------
	@Autowired
	private DriverStandingService driverStandingService;
	
	
	// Suite test --------------------------------------
	@Test
	public void test_findBySeason() {
		DriverStandingJson json;
		int totalPages, totalElements, offset;
		Optional<Integer> selectedPage;
		String season;
		
		season = "2019";
		selectedPage = Optional.of(1);
		
		json = this.driverStandingService.findBySeason(season, selectedPage);
		
		assertNotNull(json);
		
		totalPages = json.getTotalPages();
		totalElements = json.getTotalElements();
		offset = json.getNumber();
		
		assertTrue(json.getContent().length <= UtilityService.DEFAULT_LIMIT);
		assertTrue(totalPages > 0);
		assertTrue(totalElements > 0);
		assertTrue(offset >= 0);
	}
	
	@Test
	public void test_findByPosition() {
		DriverStandingJson json;
		int totalPages, totalElements, offset;
		Optional<Integer> selectedPage;
		String position;
		
		position = "1";
		selectedPage = Optional.of(1);
		
		json = this.driverStandingService.findByPosition(position, selectedPage);
		
		assertNotNull(json);
		
		totalPages = json.getTotalPages();
		totalElements = json.getTotalElements();
		offset = json.getNumber();
		
		assertTrue(json.getContent().length <= UtilityService.DEFAULT_LIMIT);
		assertTrue(totalPages > 0);
		assertTrue(totalElements > 0);
		assertTrue(offset >= 0);
	}
	
	@Test
	public void test_findByDriver() {
		DriverStandingJson json;
		int totalPages, totalElements, offset;
		Optional<Integer> selectedPage;
		String driver;
		
		driver = "Lewis Hamilton";
		selectedPage = Optional.of(1);
		
		json = this.driverStandingService.findByDriver(driver, selectedPage);
		
		assertNotNull(json);
		
		totalPages = json.getTotalPages();
		totalElements = json.getTotalElements();
		offset = json.getNumber();
		
		assertTrue(json.getContent().length <= UtilityService.DEFAULT_LIMIT);
		assertTrue(totalPages > 0);
		assertTrue(totalElements > 0);
		assertTrue(offset >= 0);
	}
	
	@Test
	public void positiveTest_findCountByDriver() {
		String driver;
		Integer result;
		
		driver = "Fernando Alonso";
		result = this.driverStandingService.findCountByDriver(driver);
		
		assertTrue(result == 17);
	}
	
	@Test
	public void negativeTest_findCountByDriver() {
		String driver;
		Integer result;
		
		driver = "Fernando Alonso Jr";
		result = this.driverStandingService.findCountByDriver(driver);
		
		assertTrue(result == null || result == 0);
	}
	
	@Test
	public void test_findCountByDriverAndPosition() {
		String driver, position;
		Integer result;
		
		position = "1";
		driver = "Fernando Alonso";
		
		result = this.driverStandingService.findCountByDriverAndPosition(driver,
																		 position);
		
		assertTrue(result == 2);
	}
	
}
