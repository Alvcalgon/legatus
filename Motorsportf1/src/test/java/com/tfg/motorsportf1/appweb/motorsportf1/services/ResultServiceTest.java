package com.tfg.motorsportf1.appweb.motorsportf1.services;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResultServiceTest {

	// Service under testing ---------------
	@Autowired
	private ResultService resultService;
	
	// Suite test --------------------------
	@Test
	public void test_findCountByPositionAndDriver() {
		String driver, position;
		Integer result;
		
		position = "1";
		driver = "Fernando Alonso";
		
		result = this.resultService.findCountByPositionAndDriver(driver, position);
		
		assertTrue(result == 32);
	}
	
	@Test
	public void test_findCountByGridAndDriver() {
		String driver, grid;
		Integer result;
		
		grid = "1";
		driver = "Fernando Alonso";
		
		result = this.resultService.findCountByGridAndDriver(driver, grid);
		
		assertTrue(result == 22);
	}
	
	@Test
	public void test_findCountByDriver() {
		String driver;
		Integer result;
		
		driver = "Fernando Alonso";
		
		result = this.resultService.findCountByDriver(driver);
		
		assertTrue(result == 314);
	}
	
	@Test
	public void test_findCountByPositionAndConstructor() {
		String constructor, position;
		Integer result;
		
		position = "1";
		constructor = "Ferrari";
		
		result = this.resultService.findCountByPositionAndConstructor(constructor, position);
		
		assertTrue(result == 239);
	}
	
	@Test
	public void test_findCountByGridAndConstructor() {
		String constructor, grid;
		Integer result;
		
		grid = "1";
		constructor = "Ferrari";
		
		result = this.resultService.findCountByGridAndConstructor(constructor, grid);
		
		assertTrue(result == 233);
	}
	
	@Test
	public void test_findCountByConstructor() {
		String constructor;
		Integer result;
		
		constructor = "Ferrari";
		
		result = this.resultService.findCountByConstructor(constructor);
		
		assertTrue(result == 992);
	}
	
}
