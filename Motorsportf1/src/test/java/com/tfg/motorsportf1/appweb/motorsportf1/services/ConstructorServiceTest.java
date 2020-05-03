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
public class ConstructorServiceTest {

	// Service under testing ------------
	@Autowired
	private ConstructorService constructorService;
	
	
	// Suite test -----------------------
	@Test
	public void test1_findOne() {
		String name;
		Object constructor;
		
		name = "Ferrari";
		constructor = this.constructorService.findOne(name);
		
		assertNotNull(constructor);
	}
	
	@Test
	public void test2_findOne() {
		String name;
		Object constructor;
		
		name = "Ferrari nope";
		constructor = this.constructorService.findOne(name);
		
		assertTrue(constructor == null);
	}
	
	@Test
	public void test1_findAll() {
		Map<String, List<Object>> results;
		List<Object> constructors, dataPage;
		int totalPages, totalElements, offset;
		
		results = this.constructorService.findAll();
		
		assertNotNull(results);
		assertTrue(!results.isEmpty());
		assertTrue(results.containsKey("constructors"));
		assertTrue(results.containsKey("dataPage"));
	
		constructors = results.get("constructors");
		dataPage = results.get("dataPage");
		
		totalPages = (int) dataPage.get(0);
		totalElements = (int) dataPage.get(1);
		offset = (int) dataPage.get(2);
		
		assertTrue(constructors.size() == UtilityService.DEFAULT_LIMIT);
		assertTrue(totalPages > 0);
		assertTrue(totalElements > 0);
		assertTrue(offset >= 0);
	}
	
	@Test
	public void test2_findAll() {
		Map<String, List<Object>> results;
		List<Object> constructors, dataPage;
		int totalPages, totalElements, offset;
		Optional<Integer> selectedPage;
		
		selectedPage = Optional.of(2);
		
		results = this.constructorService.findAll(selectedPage);
		
		assertNotNull(results);
		assertTrue(!results.isEmpty());
		assertTrue(results.containsKey("constructors"));
		assertTrue(results.containsKey("dataPage"));
	
		constructors = results.get("constructors");
		dataPage = results.get("dataPage");
		
		totalPages = (int) dataPage.get(0);
		totalElements = (int) dataPage.get(1);
		offset = (int) dataPage.get(2);
		
		assertTrue(constructors.size() == UtilityService.DEFAULT_LIMIT);
		assertTrue(totalPages > 0);
		assertTrue(totalElements > 0);
		assertTrue(offset >= 0);
	}
	
	@Test
	public void test_findByNationality() {
		Map<String, List<Object>> results;
		List<Object> constructors, dataPage;
		int totalPages, totalElements, offset;
		String nationality;
		Optional<Integer> selectedPage;
		
		nationality = "Italian";
		selectedPage = Optional.of(1);
		
		results = this.constructorService.findByNationality(nationality,
															selectedPage);
		
		assertNotNull(results);
		assertTrue(!results.isEmpty());
		assertTrue(results.containsKey("constructors"));
		assertTrue(results.containsKey("dataPage"));
	
		constructors = results.get("constructors");
		dataPage = results.get("dataPage");
		
		totalPages = (int) dataPage.get(0);
		totalElements = (int) dataPage.get(1);
		offset = (int) dataPage.get(2);
		
		assertTrue(constructors.size() <= UtilityService.DEFAULT_LIMIT);
		assertTrue(totalPages > 0);
		assertTrue(totalElements > 0);
		assertTrue(offset >= 0);
	}
	
	@Test
	public void test_findByName() {
		Map<String, List<Object>> results;
		List<Object> constructors, dataPage;
		int totalPages, totalElements, offset;
		String nationality;
		Optional<Integer> selectedPage;
		
		nationality = "Italian";
		selectedPage = Optional.of(1);
		
		results = this.constructorService.findByNationality(nationality,
															selectedPage);
		
		assertNotNull(results);
		assertTrue(!results.isEmpty());
		assertTrue(results.containsKey("constructors"));
		assertTrue(results.containsKey("dataPage"));
	
		constructors = results.get("constructors");
		dataPage = results.get("dataPage");
		
		totalPages = (int) dataPage.get(0);
		totalElements = (int) dataPage.get(1);
		offset = (int) dataPage.get(2);
		
		assertTrue(constructors.size() <= UtilityService.DEFAULT_LIMIT);
		assertTrue(totalPages > 0);
		assertTrue(totalElements > 0);
		assertTrue(offset >= 0);
	}
	
	@Test
	public void test_findByParameters() {
		Map<String, List<Object>> results;
		List<Object> constructors, dataPage;
		int totalPages, totalElements, offset;
		String nationality, name;
		Optional<Integer> selectedPage;
		
		name = "ferrari";
		nationality = "Italian";
		selectedPage = Optional.of(1);
		
		results = this.constructorService.findByParameters(name,
														   nationality,
														   selectedPage);
		
		assertNotNull(results);
		assertTrue(!results.isEmpty());
		assertTrue(results.containsKey("constructors"));
		assertTrue(results.containsKey("dataPage"));
	
		constructors = results.get("constructors");
		dataPage = results.get("dataPage");
		
		totalPages = (int) dataPage.get(0);
		totalElements = (int) dataPage.get(1);
		offset = (int) dataPage.get(2);
		
		assertTrue(constructors.size() <= UtilityService.DEFAULT_LIMIT);
		assertTrue(totalPages > 0);
		assertTrue(totalElements > 0);
		assertTrue(offset >= 0);
	}
}
