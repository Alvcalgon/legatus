package com.tfg.motorsportf1.appweb.motorsportf1.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tfg.motorsportf1.appweb.motorsportf1.bean.DriverJson;
import com.tfg.motorsportf1.appweb.motorsportf1.services.utilities.AbstractTest;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DriverServiceTest extends AbstractTest {

	// Service under testing ---------------
	@Autowired
	private DriverService driverService;
	
	
	// Suite test -------------------------
	@Test
	public void getPodiums() {
		String fullname = "Fernando Alonso";
		Integer podiums;
		
		podiums = this.driverService.getPodiums(fullname);
		
		assertTrue(podiums == 97);
	}
	
	@Test
	public void positiveTest_display_uno() {
		Object driver;
		String fullname;
		
		fullname = "Fernando Alonso";
		driver = this.driverService.findOne(fullname);
		
		assertNotNull(driver);
	}
	
	@Test
	public void positiveTest_display_dos() {
		Object driver;
		String fullname;
		
		fullname = "Philippe Étancelin";
		driver = this.driverService.findOne(fullname);
		
		assertNotNull(driver);
	}
	
	@Test
	public void negativeTest_display() {
		Object driver;
		String fullname;
		
		fullname = "Desconocido";
		driver = this.driverService.findOne(fullname);
		
		assertTrue(driver == null);
	}
	
	@Test
	public void test1_findAll() {
		DriverJson json;
		int totalPages, totalElements, offset;
		
		json = this.driverService.findAll();
		
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
		DriverJson json;
		int totalPages, totalElements, offset;
		Optional<Integer> selectedPage;
		
		selectedPage = Optional.of(2);
		
		json = this.driverService.findAll(selectedPage);
		
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
	public void test_findByNationality() {
		DriverJson json;
		int totalPages, totalElements, offset;
		Optional<Integer> selectedPage;
		String nationality;
		
		nationality = "Spanish";
		selectedPage = Optional.of(2);
		
		json = this.driverService.findByNationality(nationality, selectedPage);
		
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
	public void test_findByFullname() {
		DriverJson json;
		int totalPages, totalElements, offset;
		Optional<Integer> selectedPage;
		String fullname;
		
		fullname = "Lewis";
		selectedPage = Optional.of(1);
		
		json = this.driverService.findByFullname(fullname, selectedPage);
		
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
	public void test_findByParameters() {
		DriverJson json;
		int totalPages, totalElements, offset;
		Optional<Integer> selectedPage;
		String nationality, fullname;
		
		nationality = "spanish";
		fullname = "fernando";
		selectedPage = Optional.of(1);
		
		json = this.driverService.findByParameters(fullname, nationality, selectedPage);
		
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
