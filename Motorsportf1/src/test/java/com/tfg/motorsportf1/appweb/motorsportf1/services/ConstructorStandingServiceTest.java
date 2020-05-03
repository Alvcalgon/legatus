package com.tfg.motorsportf1.appweb.motorsportf1.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConstructorStandingServiceTest {

	// Service under testing -------------------
	@Autowired
	private ConstructorStandingService constructorStandingService;
	
	// Suite test ------------------------------
	@Test
	public void test_findBySeason() {
		Map<String, List<Object>> results;
		List<Object> constructorsStanding, dataPage;
		int totalPages, totalElements, offset;
		Optional<Integer> selectedPage;
		String season;
		
		season = "2019";
		selectedPage = Optional.of(1);
		
		results = this.constructorStandingService.findBySeason(season, selectedPage);
		
		assertNotNull(results);
		assertTrue(!results.isEmpty());
		assertTrue(results.containsKey("constructorsStanding"));
		assertTrue(results.containsKey("dataPage"));
	
		constructorsStanding = results.get("constructorsStanding");
		dataPage = results.get("dataPage");
		
		totalPages = (int) dataPage.get(0);
		totalElements = (int) dataPage.get(1);
		offset = (int) dataPage.get(2);
		
		assertTrue(constructorsStanding.size() <= UtilityService.DEFAULT_LIMIT);
		assertTrue(totalPages > 0);
		assertTrue(totalElements > 0);
		assertTrue(offset >= 0);
	}
	
	@Test
	public void test_findByPosition() {
		Map<String, List<Object>> results;
		List<Object> constructorsStanding, dataPage;
		int totalPages, totalElements, offset;
		Optional<Integer> selectedPage;
		String position;
		
		position = "1";
		selectedPage = Optional.of(1);
		
		results = this.constructorStandingService.findByPosition(position, selectedPage);
		
		assertNotNull(results);
		assertTrue(!results.isEmpty());
		assertTrue(results.containsKey("constructorsStanding"));
		assertTrue(results.containsKey("dataPage"));
	
		constructorsStanding = results.get("constructorsStanding");
		dataPage = results.get("dataPage");
		
		totalPages = (int) dataPage.get(0);
		totalElements = (int) dataPage.get(1);
		offset = (int) dataPage.get(2);
		
		assertTrue(constructorsStanding.size() <= UtilityService.DEFAULT_LIMIT);
		assertTrue(totalPages > 0);
		assertTrue(totalElements > 0);
		assertTrue(offset >= 0);
	}
	
	@Test
	public void test_findByConstructor() {
		Map<String, List<Object>> results;
		List<Object> constructorsStanding, dataPage;
		int totalPages, totalElements, offset;
		Optional<Integer> selectedPage;
		String constructor;
		
		constructor = "Mercedes";
		selectedPage = Optional.of(1);
		
		results = this.constructorStandingService.findByConstructor(constructor, selectedPage);
		
		assertNotNull(results);
		assertTrue(!results.isEmpty());
		assertTrue(results.containsKey("constructorsStanding"));
		assertTrue(results.containsKey("dataPage"));
	
		constructorsStanding = results.get("constructorsStanding");
		dataPage = results.get("dataPage");
		
		totalPages = (int) dataPage.get(0);
		totalElements = (int) dataPage.get(1);
		offset = (int) dataPage.get(2);
		
		assertTrue(constructorsStanding.size() <= UtilityService.DEFAULT_LIMIT);
		assertTrue(totalPages > 0);
		assertTrue(totalElements > 0);
		assertTrue(offset >= 0);
	}
	
	@Test
	public void positiveTest_findCountByConstructor() {
		String constructor;
		Integer result;
		
		constructor = "Mercedes";
		result = this.constructorStandingService.findCountByConstructor(constructor);
		
		assertTrue(result == 10);
	}
	
	@Test
	public void negativeTest_findCountByConstructor() {
		String constructor;
		Integer result;
		
		constructor = "No no";
		result = this.constructorStandingService.findCountByConstructor(constructor);
		
		assertTrue(result == null || result == 0);
	}
	
	@Test
	public void test_findCountByConstructorAndPosition() {
		String constructor, position;
		Integer result;
		
		position = "1";
		constructor = "Mercedes";
		
		result = this.constructorStandingService.findCountByConstructorAndPosition(constructor,
																		 position);
		
		assertTrue(result == 6);
	}
	
	
}
