package com.tfg.motorsportf1.appweb.motorsportf1.services;

import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tfg.motorsportf1.appweb.motorsportf1.services.utilities.AbstractTest;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UtilityServiceTest extends AbstractTest {

	// Servicio bajo testeo ---------------------
	@Autowired
	private UtilityService utilityService;
	
	
	// Suite test -------------------------------
	@Test
	public void test_getDateFromString() {		
		String str_date;
		Date date;
		
		str_date = "1896-12-27T23:00:00.000+0000";
		date = this.utilityService.getDateFromString(str_date);
		
		assertNotNull(date);
	}
	
	
}
