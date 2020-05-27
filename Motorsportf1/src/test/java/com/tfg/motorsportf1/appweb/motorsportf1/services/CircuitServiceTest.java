package com.tfg.motorsportf1.appweb.motorsportf1.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tfg.motorsportf1.appweb.motorsportf1.bean.CircuitJson;
import com.tfg.motorsportf1.appweb.motorsportf1.domain.Circuit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CircuitServiceTest {

	// Service under testing ----------------
	@Autowired
	private CircuitService circuitService;
	
	// Suite test ---------------------------
	@Test
	public void test1_findAll() {
		CircuitJson json;
		int totalPages, totalElements, offset;
		
		json = this.circuitService.findAll();
		
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
	public void test2_findAll() {
		CircuitJson json;
		int totalPages, totalElements, offset;
		Optional<Integer> selectedPage;
		
		selectedPage = Optional.of(2);
		
		json = this.circuitService.findAll(selectedPage);
		
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
	public void test_findByLocation() {
		CircuitJson json;
		int totalPages, totalElements, offset;
		Optional<Integer> selectedPage;
		String location;
		
		location = "spain";
		selectedPage = Optional.of(1);
		
		json = this.circuitService.findByLocation(location, selectedPage);
		
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
	public void test_findByName() {
		CircuitJson json;
		int totalPages, totalElements, offset;
		Optional<Integer> selectedPage;
		String name;
		
		name = "albert park";
		selectedPage = Optional.of(1);
		
		json = this.circuitService.findByName(name, selectedPage);
		
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
	public void test_findBySeason() {
		List<Circuit> results;
		String season;
		
		season = "2019";
		
		results = this.circuitService.findBySeason(season);
		
		assertNotNull(results);
		assertTrue(!results.isEmpty());
	}

	@Test
	public void test_findByParameters() {
		CircuitJson json;
		int totalPages, totalElements, offset;
		Optional<Integer> selectedPage;
		String location, name;
		
		location = "spain";
		name = "mont";
		selectedPage = Optional.of(1);
		
		json = this.circuitService.findByAllParameters(location, name, selectedPage);
		
		assertNotNull(json);
	
		totalPages = json.getTotalPages();
		totalElements = json.getTotalElements();
		offset = json.getNumber();
		
		assertTrue(json.getContent().length <= UtilityService.DEFAULT_LIMIT);
		assertTrue(totalPages > 0);
		assertTrue(totalElements > 0);
		assertTrue(offset >= 0);
	}
		
}
