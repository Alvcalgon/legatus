package com.tfg.motorsportf1.appweb.motorsportf1.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tfg.motorsportf1.appweb.motorsportf1.services.utilities.AbstractTest;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DriverServiceTest extends AbstractTest {

	// Servicio bajo testeo ---------------
	@Autowired
	private DriverService driverService;
	
	
	// Suite test -------------------------
	
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
	
}
