package com.tfg.motorsportf1.appweb.motorsportf1.services;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DriverStandingServiceTest {

	// Servicio bajo testeo ----------------------------
	@Autowired
	private DriverStandingService driverStandingService;
	
	
	// Suite test --------------------------------------
	
	@Test
	public void positiveTest_findCountByDriver() {
		String driver;
		Integer result;
		
		driver = "Fernando Alonso";
		result = this.driverStandingService.findCountByDriver(driver);
		
		assertTrue(result == 16);
	}
	
	@Test
	public void negativeTest_findCountByDriver() {
		String driver;
		Integer result;
		
		driver = "Fernando Alonso Jr";
		result = this.driverStandingService.findCountByDriver(driver);
		
		assertTrue(result == null || result == 0);
	}
	
}
