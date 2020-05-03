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
public class CircuitServiceTest {

	// Service under testing ----------------
	@Autowired
	private CircuitService circuitService;
	
	// Suite test ---------------------------
	@Test
	public void test1_findAll() {
		Map<String, List<Object>> results;
		List<Object> circuits, dataPage;
		int totalPages, totalElements, offset;
		
		results = this.circuitService.findAll();
		
		assertNotNull(results);
		assertTrue(!results.isEmpty());
		assertTrue(results.containsKey("circuits"));
		assertTrue(results.containsKey("dataPage"));
	
		circuits = results.get("circuits");
		dataPage = results.get("dataPage");
		
		totalPages = (int) dataPage.get(0);
		totalElements = (int) dataPage.get(1);
		offset = (int) dataPage.get(2);
		
		assertTrue(circuits.size() == UtilityService.DEFAULT_LIMIT);
		assertTrue(totalPages > 0);
		assertTrue(totalElements > 0);
		assertTrue(offset >= 0);
	}
	
	@Test
	public void test2_findAll() {
		Map<String, List<Object>> results;
		List<Object> circuits, dataPage;
		int totalPages, totalElements, offset;
		Optional<Integer> selectedPage;
		
		selectedPage = Optional.of(2);
		
		results = this.circuitService.findAll(selectedPage);
		
		assertNotNull(results);
		assertTrue(!results.isEmpty());
		assertTrue(results.containsKey("circuits"));
		assertTrue(results.containsKey("dataPage"));
	
		circuits = results.get("circuits");
		dataPage = results.get("dataPage");
		
		totalPages = (int) dataPage.get(0);
		totalElements = (int) dataPage.get(1);
		offset = (int) dataPage.get(2);
		
		assertTrue(circuits.size() == UtilityService.DEFAULT_LIMIT);
		assertTrue(totalPages > 0);
		assertTrue(totalElements > 0);
		assertTrue(offset >= 0);
	}
	
	@Test
	public void test_findByLocation() {
		Map<String, List<Object>> results;
		List<Object> circuits, dataPage;
		int totalPages, totalElements, offset;
		Optional<Integer> selectedPage;
		String location;
		
		location = "spain";
		selectedPage = Optional.of(1);
		
		results = this.circuitService.findByLocation(location, selectedPage);
		
		assertNotNull(results);
		assertTrue(!results.isEmpty());
		assertTrue(results.containsKey("circuits"));
		assertTrue(results.containsKey("dataPage"));
	
		circuits = results.get("circuits");
		dataPage = results.get("dataPage");
		
		totalPages = (int) dataPage.get(0);
		totalElements = (int) dataPage.get(1);
		offset = (int) dataPage.get(2);
		
		assertTrue(circuits.size() <= UtilityService.DEFAULT_LIMIT);
		assertTrue(totalPages > 0);
		assertTrue(totalElements > 0);
		assertTrue(offset >= 0);
	}
	
	@Test
	public void test_findByName() {
		Map<String, List<Object>> results;
		List<Object> circuits, dataPage;
		int totalPages, totalElements, offset;
		Optional<Integer> selectedPage;
		String name;
		
		name = "albert park";
		selectedPage = Optional.of(1);
		
		results = this.circuitService.findByName(name, selectedPage);
		
		assertNotNull(results);
		assertTrue(!results.isEmpty());
		assertTrue(results.containsKey("circuits"));
		assertTrue(results.containsKey("dataPage"));
	
		circuits = results.get("circuits");
		dataPage = results.get("dataPage");
		
		totalPages = (int) dataPage.get(0);
		totalElements = (int) dataPage.get(1);
		offset = (int) dataPage.get(2);
		
		assertTrue(circuits.size() <= UtilityService.DEFAULT_LIMIT);
		assertTrue(totalPages > 0);
		assertTrue(totalElements > 0);
		assertTrue(offset >= 0);
	}
	
	@Test
	public void test_findBySeason() {
		List<Object> results;
		String season;
		
		season = "2019";
		
		results = this.circuitService.findBySeason(season);
		
		assertNotNull(results);
		assertTrue(!results.isEmpty());
	}
	
	@Test
	public void test_findByParameters() {
		Map<String, List<Object>> results;
		List<Object> circuits, dataPage;
		int totalPages, totalElements, offset;
		Optional<Integer> selectedPage;
		String location, name;
		
		location = "spain";
		name = "mont";
		selectedPage = Optional.of(1);
		
		results = this.circuitService.findByAllParameters(location, name, selectedPage);
		
		assertNotNull(results);
		assertTrue(!results.isEmpty());
		assertTrue(results.containsKey("circuits"));
		assertTrue(results.containsKey("dataPage"));
	
		circuits = results.get("circuits");
		dataPage = results.get("dataPage");
		
		totalPages = (int) dataPage.get(0);
		totalElements = (int) dataPage.get(1);
		offset = (int) dataPage.get(2);
		
		assertTrue(circuits.size() <= UtilityService.DEFAULT_LIMIT);
		assertTrue(totalPages > 0);
		assertTrue(totalElements > 0);
		assertTrue(offset >= 0);
	}
	
	
}
