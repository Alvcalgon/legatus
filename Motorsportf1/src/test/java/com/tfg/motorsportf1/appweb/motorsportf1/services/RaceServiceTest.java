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
public class RaceServiceTest {

	// Service under testing ------------------
	@Autowired
	private RaceService raceService;
	
	// Suite test -----------------------------
	@Test
	public void test_display() {
		Object race;
		String season, event;
		
		season = "2019";
		event = "Australian Grand Prix";
		race = this.raceService.findOne(season, event);
		
		assertNotNull(race);
	}
	
	@Test
	public void test1_findBySeason() {
		Map<String, List<Object>> results;
		List<Object> races, dataPage;
		int totalPages, totalElements, offset;
		String season;
		Optional<Integer> selectedPage;
		
		season = "2019";
		selectedPage = Optional.of(1);
		
		results = this.raceService.findBySeason(season, selectedPage);
		
		assertNotNull(results);
		assertTrue(!results.isEmpty());
		assertTrue(results.containsKey("races"));
		assertTrue(results.containsKey("dataPage"));
	
		races = results.get("races");
		dataPage = results.get("dataPage");
		
		totalPages = (int) dataPage.get(0);
		totalElements = (int) dataPage.get(1);
		offset = (int) dataPage.get(2);
		
		assertTrue(races.size() == UtilityService.DEFAULT_LIMIT);
		assertTrue(totalPages > 0);
		assertTrue(totalElements > 0);
		assertTrue(offset >= 0);
	}
	
//	@Test
//	public void test1_findByCircuit() {
//		Map<String, List<Object>> results;
//		List<Object> races, dataPage;
//		int totalPages, totalElements, offset;
//		String circuit;
//		Optional<Integer> selectedPage;
//		
//		circuit = "Bahrain International Circuit";
//		selectedPage = Optional.of(1);
//		
//		results = this.raceService.findByCircuit(circuit, selectedPage);
//		
//		assertNotNull(results);
//		assertTrue(!results.isEmpty());
//		assertTrue(results.containsKey("races"));
//		assertTrue(results.containsKey("dataPage"));
//	
//		races = results.get("races");
//		dataPage = results.get("dataPage");
//		
//		totalPages = (int) dataPage.get(0);
//		totalElements = (int) dataPage.get(1);
//		offset = (int) dataPage.get(2);
//		
//		assertTrue(races.size() <= UtilityService.DEFAULT_LIMIT);
//		assertTrue(totalPages > 0);
//		assertTrue(totalElements > 0);
//		assertTrue(offset >= 0);
//	}
//	
//	@Test
//	public void test1_findByEvent() {
//		Map<String, List<Object>> results;
//		List<Object> races, dataPage;
//		int totalPages, totalElements, offset;
//		String event;
//		Optional<Integer> selectedPage;
//		
//		event = "Bahrain Grand Prix";
//		selectedPage = Optional.of(1);
//		
//		results = this.raceService.findByEvent(event, selectedPage);
//		
//		assertNotNull(results);
//		assertTrue(!results.isEmpty());
//		assertTrue(results.containsKey("races"));
//		assertTrue(results.containsKey("dataPage"));
//	
//		races = results.get("races");
//		dataPage = results.get("dataPage");
//		
//		totalPages = (int) dataPage.get(0);
//		totalElements = (int) dataPage.get(1);
//		offset = (int) dataPage.get(2);
//		
//		assertTrue(races.size() <= UtilityService.DEFAULT_LIMIT);
//		assertTrue(totalPages > 0);
//		assertTrue(totalElements > 0);
//		assertTrue(offset >= 0);
//	}
	
}
