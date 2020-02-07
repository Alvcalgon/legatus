package com.tfg.motorsportf1.appweb.motorsportf1.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

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
	public void test_findAll() {
		Map<String,List<Object>> mapJSON;
		List<Object> drivers, dataPage;
		int limit, totalNumbers, currentPage;
		
		limit = 5;
		mapJSON = this.driverService.findAll(0, limit);
		
		drivers = mapJSON.get("drivers");
		dataPage  = mapJSON.get("dataPage");
		
		totalNumbers = (int) dataPage.get(0);
		currentPage = (int) dataPage.get(1);
		
		assertNotNull(drivers);
		assertTrue(drivers.size() == limit);
		assertTrue(totalNumbers >= 0);
		assertTrue(currentPage >= 0);
	}
	
}
