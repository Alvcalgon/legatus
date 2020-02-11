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

import com.tfg.motorsportf1.appweb.motorsportf1.services.utilities.AbstractTest;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DriverServiceTest extends AbstractTest {

	// Servicio bajo testeo ---------------
	@Autowired
	private DriverService driverService;
	
	
	// Suite test -------------------------
	@Test
	public void positiveTest_findAll_uno() {
		Map<String,List<Object>> mapJSON;
		List<Object> drivers, dataPage;
		int offset, limit, totalPages, currentPage, totalElements;
		Optional<Integer> page, size;
		
		offset = -1;
		limit = 10;
		
		page = Optional.ofNullable(offset);
		size = Optional.ofNullable(limit);
		
		mapJSON = this.driverService.findAll(page, size);
		
		drivers = mapJSON.get("drivers");
		dataPage  = mapJSON.get("dataPage");
		
		assertNotNull(drivers);
		assertNotNull(dataPage);
		
		totalPages = (int) dataPage.get(0);
		currentPage = (int) dataPage.get(1);
		totalElements = (int) dataPage.get(2);
		limit = (int) dataPage.get(3);
		offset = (int) dataPage.get(4);
		
		assertTrue(drivers.size() == limit);
		assertTrue(totalPages >= 0);
		assertTrue(currentPage >= 0 && currentPage <= totalPages);
		assertTrue(totalElements >= 0);
		assertTrue(limit >= 1 && limit <= totalElements);
		assertTrue(offset >= 1 && offset <= totalPages);
	}
	
	@Test
	public void positiveTest_findAll_dos() {
		Map<String,List<Object>> mapJSON;
		List<Object> drivers, dataPage;
		int offset, limit, totalPages, currentPage, totalElements;
		Optional<Integer> page, size;
		
		offset = 0;
		limit = 10;
		
		page = Optional.ofNullable(offset);
		size = Optional.ofNullable(limit);
		
		mapJSON = this.driverService.findAll(page, size);
		
		drivers = mapJSON.get("drivers");
		dataPage  = mapJSON.get("dataPage");
		
		assertNotNull(drivers);
		assertNotNull(dataPage);
		
		totalPages = (int) dataPage.get(0);
		currentPage = (int) dataPage.get(1);
		totalElements = (int) dataPage.get(2);
		limit = (int) dataPage.get(3);
		offset = (int) dataPage.get(4);
		
		assertTrue(drivers.size() == limit);
		assertTrue(totalPages >= 0);
		assertTrue(currentPage >= 0 && currentPage <= totalPages);
		assertTrue(totalElements >= 0);
		assertTrue(limit >= 1 && limit <= totalElements);
		assertTrue(offset >= 1 && offset <= totalPages);
	}
	
	@Test
	public void positiveTest_findAll_tres() {
		Map<String,List<Object>> mapJSON;
		List<Object> drivers, dataPage;
		int offset, limit, totalPages, currentPage, totalElements;
		Optional<Integer> page, size;
		
		offset = 1;
		limit = 10;
		
		page = Optional.ofNullable(offset);
		size = Optional.ofNullable(limit);
		
		mapJSON = this.driverService.findAll(page, size);
		
		drivers = mapJSON.get("drivers");
		dataPage  = mapJSON.get("dataPage");
		
		assertNotNull(drivers);
		assertNotNull(dataPage);
		
		totalPages = (int) dataPage.get(0);
		currentPage = (int) dataPage.get(1);
		totalElements = (int) dataPage.get(2);
		limit = (int) dataPage.get(3);
		offset = (int) dataPage.get(4);
		
		assertTrue(drivers.size() == limit);
		assertTrue(totalPages >= 0);
		assertTrue(currentPage >= 0 && currentPage <= totalPages);
		assertTrue(totalElements >= 0);
		assertTrue(limit >= 1 && limit <= totalElements);
		assertTrue(offset >= 1 && offset <= totalPages);
	}
	
	@Test
	public void positiveTest_findAll_cuatro() {
		Map<String,List<Object>> mapJSON;
		List<Object> drivers, dataPage;
		int offset, limit, totalPages, currentPage, totalElements;
		Optional<Integer> page, size;
		
		offset = 5;
		limit = 10;
		
		page = Optional.ofNullable(offset);
		size = Optional.ofNullable(limit);
		
		mapJSON = this.driverService.findAll(page, size);
		
		drivers = mapJSON.get("drivers");
		dataPage  = mapJSON.get("dataPage");
		
		assertNotNull(drivers);
		assertNotNull(dataPage);
		
		totalPages = (int) dataPage.get(0);
		currentPage = (int) dataPage.get(1);
		totalElements = (int) dataPage.get(2);
		limit = (int) dataPage.get(3);
		offset = (int) dataPage.get(4);
		
		assertTrue(drivers.size() == limit);
		assertTrue(totalPages >= 0);
		assertTrue(currentPage >= 0 && currentPage <= totalPages);
		assertTrue(totalElements >= 0);
		assertTrue(limit >= 1 && limit <= totalElements);
		assertTrue(offset >= 1 && offset <= totalPages);
	}
	
	@Test
	public void positiveTest_findAll_cinco() {
		Map<String,List<Object>> mapJSON;
		List<Object> drivers, dataPage;
		int offset, limit, totalPages, currentPage, totalElements;
		Optional<Integer> page, size;
		
		offset = 84;
		limit = 10;
		
		page = Optional.ofNullable(offset);
		size = Optional.ofNullable(limit);
		
		mapJSON = this.driverService.findAll(page, size);
		
		drivers = mapJSON.get("drivers");
		dataPage  = mapJSON.get("dataPage");
		
		assertNotNull(drivers);
		assertNotNull(dataPage);
		
		totalPages = (int) dataPage.get(0);
		currentPage = (int) dataPage.get(1);
		totalElements = (int) dataPage.get(2);
		limit = (int) dataPage.get(3);
		offset = (int) dataPage.get(4);
		
		assertTrue(drivers.size() == limit);
		assertTrue(totalPages >= 0);
		assertTrue(currentPage >= 0 && currentPage <= totalPages);
		assertTrue(totalElements >= 0);
		assertTrue(limit >= 1 && limit <= totalElements);
		assertTrue(offset >= 1 && offset <= totalPages);
	}
	
	@Test
	public void positiveTest_findAll_seis() {
		Map<String,List<Object>> mapJSON;
		List<Object> drivers, dataPage;
		int offset, limit, totalPages, currentPage, totalElements;
		Optional<Integer> page, size;
		
		offset = 85;
		limit = 10;
		
		page = Optional.ofNullable(offset);
		size = Optional.ofNullable(limit);
		
		mapJSON = this.driverService.findAll(page, size);
		
		drivers = mapJSON.get("drivers");
		dataPage  = mapJSON.get("dataPage");
		
		assertNotNull(drivers);
		assertNotNull(dataPage);
		
		totalPages = (int) dataPage.get(0);
		currentPage = (int) dataPage.get(1);
		totalElements = (int) dataPage.get(2);
		limit = (int) dataPage.get(3);
		offset = (int) dataPage.get(4);
		
		assertTrue(drivers.size() == limit);
		assertTrue(totalPages >= 0);
		assertTrue(currentPage >= 0 && currentPage <= totalPages);
		assertTrue(totalElements >= 0);
		assertTrue(limit >= 1 && limit <= totalElements);
		assertTrue(offset >= 1 && offset <= totalPages);
	}
	
	@Test
	public void positiveTest_findAll_siete() {
		Map<String,List<Object>> mapJSON;
		List<Object> drivers, dataPage;
		int offset, limit, totalPages, currentPage, totalElements;
		Optional<Integer> page, size;
		
		offset = 86;
		limit = 10;
		
		page = Optional.ofNullable(offset);
		size = Optional.ofNullable(limit);
		
		mapJSON = this.driverService.findAll(page, size);
		
		drivers = mapJSON.get("drivers");
		dataPage  = mapJSON.get("dataPage");
		
		assertNotNull(drivers);
		assertNotNull(dataPage);
		
		totalPages = (int) dataPage.get(0);
		currentPage = (int) dataPage.get(1);
		totalElements = (int) dataPage.get(2);
		limit = (int) dataPage.get(3);
		offset = (int) dataPage.get(4);
		
		assertTrue(drivers.size() == limit);
		assertTrue(totalPages >= 0);
		assertTrue(currentPage >= 0 && currentPage <= totalPages);
		assertTrue(totalElements >= 0);
		assertTrue(limit >= 1 && limit <= totalElements);
		assertTrue(offset >= 1 && offset <= totalPages);
	}
	
	@Test
	public void positiveTest_findAll_ocho() {
		Map<String,List<Object>> mapJSON;
		List<Object> drivers, dataPage;
		int offset, limit, totalPages, currentPage, totalElements;
		Optional<Integer> page, size;
		
		offset = 25;
		limit = 10;
		
		page = Optional.ofNullable(null);
		size = Optional.ofNullable(null);
		
		mapJSON = this.driverService.findAll(page, size);
		
		drivers = mapJSON.get("drivers");
		dataPage  = mapJSON.get("dataPage");
		
		assertNotNull(drivers);
		assertNotNull(dataPage);
		
		totalPages = (int) dataPage.get(0);
		currentPage = (int) dataPage.get(1);
		totalElements = (int) dataPage.get(2);
		limit = (int) dataPage.get(3);
		offset = (int) dataPage.get(4);
		
		assertTrue(drivers.size() == limit);
		assertTrue(totalPages >= 0);
		assertTrue(currentPage >= 0 && currentPage <= totalPages);
		assertTrue(totalElements >= 0);
		assertTrue(limit >= 1 && limit <= totalElements);
		assertTrue(offset >= 1 && offset <= totalPages);
	}
	
	@Test
	public void positiveTest_findAll_nueve() {
		Map<String,List<Object>> mapJSON;
		List<Object> drivers, dataPage;
		int offset, limit, totalPages, currentPage, totalElements;
		Optional<Integer> page, size;
		
		offset = 5;
		limit = 10;
		
		page = null;
		size = null;
		
		mapJSON = this.driverService.findAll(page, size);
		
		drivers = mapJSON.get("drivers");
		dataPage  = mapJSON.get("dataPage");
		
		assertNotNull(drivers);
		assertNotNull(dataPage);
		
		totalPages = (int) dataPage.get(0);
		currentPage = (int) dataPage.get(1);
		totalElements = (int) dataPage.get(2);
		limit = (int) dataPage.get(3);
		offset = (int) dataPage.get(4);
		
		assertTrue(drivers.size() == limit);
		assertTrue(totalPages >= 0);
		assertTrue(currentPage >= 0 && currentPage <= totalPages);
		assertTrue(totalElements >= 0);
		assertTrue(limit >= 1 && limit <= totalElements);
		assertTrue(offset >= 1 && offset <= totalPages);
	}
	
	@Test
	public void positiveTest_findAll_diez() {
		Map<String,List<Object>> mapJSON;
		List<Object> drivers, dataPage;
		int offset, limit, totalPages, currentPage, totalElements;
		Optional<Integer> page, size;
		
		offset = 5;
		limit = -1;
		
		page = Optional.ofNullable(offset);
		size = Optional.ofNullable(limit);
		
		mapJSON = this.driverService.findAll(page, size);
		
		drivers = mapJSON.get("drivers");
		dataPage  = mapJSON.get("dataPage");
		
		assertNotNull(drivers);
		assertNotNull(dataPage);
		
		totalPages = (int) dataPage.get(0);
		currentPage = (int) dataPage.get(1);
		totalElements = (int) dataPage.get(2);
		limit = (int) dataPage.get(3);
		offset = (int) dataPage.get(4);
		
		assertTrue(drivers.size() == limit);
		assertTrue(totalPages >= 0);
		assertTrue(currentPage >= 0 && currentPage <= totalPages);
		assertTrue(totalElements >= 0);
		assertTrue(limit >= 1 && limit <= totalElements);
		assertTrue(offset >= 1 && offset <= totalPages);
	}
	
	@Test
	public void positiveTest_findAll_once() {
		Map<String,List<Object>> mapJSON;
		List<Object> drivers, dataPage;
		int offset, limit, totalPages, currentPage, totalElements;
		Optional<Integer> page, size;
		
		offset = 5;
		limit = 0;
		
		page = Optional.ofNullable(offset);
		size = Optional.ofNullable(limit);
		
		mapJSON = this.driverService.findAll(page, size);
		
		drivers = mapJSON.get("drivers");
		dataPage  = mapJSON.get("dataPage");
		
		assertNotNull(drivers);
		assertNotNull(dataPage);
		
		totalPages = (int) dataPage.get(0);
		currentPage = (int) dataPage.get(1);
		totalElements = (int) dataPage.get(2);
		limit = (int) dataPage.get(3);
		offset = (int) dataPage.get(4);
		
		assertTrue(drivers.size() == limit);
		assertTrue(totalPages >= 0);
		assertTrue(currentPage >= 0 && currentPage <= totalPages);
		assertTrue(totalElements >= 0);
		assertTrue(limit >= 1 && limit <= totalElements);
		assertTrue(offset >= 1 && offset <= totalPages);
	}
	
	@Test
	public void positiveTest_findAll_doce() {
		Map<String,List<Object>> mapJSON;
		List<Object> drivers, dataPage;
		int offset, limit, totalPages, currentPage, totalElements;
		Optional<Integer> page, size;
		
		offset = 5;
		limit = 1;
		
		page = Optional.ofNullable(offset);
		size = Optional.ofNullable(limit);
		
		mapJSON = this.driverService.findAll(page, size);
		
		drivers = mapJSON.get("drivers");
		dataPage  = mapJSON.get("dataPage");
		
		assertNotNull(drivers);
		assertNotNull(dataPage);
		
		totalPages = (int) dataPage.get(0);
		currentPage = (int) dataPage.get(1);
		totalElements = (int) dataPage.get(2);
		limit = (int) dataPage.get(3);
		offset = (int) dataPage.get(4);
		
		assertTrue(drivers.size() == limit);
		assertTrue(totalPages >= 0);
		assertTrue(currentPage >= 0 && currentPage <= totalPages);
		assertTrue(totalElements >= 0);
		assertTrue(limit >= 1 && limit <= totalElements);
		assertTrue(offset >= 1 && offset <= totalPages);
	}
	
	@Test
	public void positiveTest_findAll_trece() {
		Map<String,List<Object>> mapJSON;
		List<Object> drivers, dataPage;
		int offset, limit, totalPages, currentPage, totalElements;
		Optional<Integer> page, size;
		
		offset = 5;
		limit = 849;
		
		page = Optional.ofNullable(offset);
		size = Optional.ofNullable(limit);
		
		mapJSON = this.driverService.findAll(page, size);
		
		drivers = mapJSON.get("drivers");
		dataPage  = mapJSON.get("dataPage");
		
		assertNotNull(drivers);
		assertNotNull(dataPage);
		
		totalPages = (int) dataPage.get(0);
		currentPage = (int) dataPage.get(1);
		totalElements = (int) dataPage.get(2);
		limit = (int) dataPage.get(3);
		offset = (int) dataPage.get(4);
		
		assertTrue(drivers.size() >= 1 && drivers.size() <= limit);
		assertTrue(totalPages >= 0);
		assertTrue(currentPage >= 0 && currentPage <= totalPages);
		assertTrue(totalElements >= 0);
		assertTrue(limit >= 1 && limit <= totalElements);
		assertTrue(offset >= 1 && offset <= totalPages);
	}
	
	@Test
	public void positiveTest_findAll_catorce() {
		Map<String,List<Object>> mapJSON;
		List<Object> drivers, dataPage;
		int offset, limit, totalPages, currentPage, totalElements;
		Optional<Integer> page, size;
		
		offset = 1;
		limit = 850;
		
		page = Optional.ofNullable(offset);
		size = Optional.ofNullable(limit);
		
		mapJSON = this.driverService.findAll(page, size);
		
		drivers = mapJSON.get("drivers");
		dataPage  = mapJSON.get("dataPage");
		
		assertNotNull(drivers);
		assertNotNull(dataPage);
		
		totalPages = (int) dataPage.get(0);
		currentPage = (int) dataPage.get(1);
		totalElements = (int) dataPage.get(2);
		limit = (int) dataPage.get(3);
		offset = (int) dataPage.get(4);
		
		assertTrue(drivers.size() == limit);
		assertTrue(totalPages >= 0);
		assertTrue(currentPage >= 0 && currentPage <= totalPages);
		assertTrue(totalElements >= 0);
		assertTrue(limit >= 1 && limit <= totalElements);
		assertTrue(offset >= 1 && offset <= totalPages);
	}
	
	@Test
	public void positiveTest_findAll_quince() {
		Map<String,List<Object>> mapJSON;
		List<Object> drivers, dataPage;
		int offset, limit, totalPages, currentPage, totalElements;
		Optional<Integer> page, size;
		
		offset = 1;
		limit = 851;
		
		page = Optional.ofNullable(offset);
		size = Optional.ofNullable(limit);
		
		mapJSON = this.driverService.findAll(page, size);
		
		drivers = mapJSON.get("drivers");
		dataPage  = mapJSON.get("dataPage");
		
		assertNotNull(drivers);
		assertNotNull(dataPage);
		
		totalPages = (int) dataPage.get(0);
		currentPage = (int) dataPage.get(1);
		totalElements = (int) dataPage.get(2);
		limit = (int) dataPage.get(3);
		offset = (int) dataPage.get(4);
		
		assertTrue(drivers.size() == limit);
		assertTrue(totalPages >= 0);
		assertTrue(currentPage >= 0 && currentPage <= totalPages);
		assertTrue(totalElements >= 0);
		assertTrue(limit >= 1 && limit <= totalElements);
		assertTrue(offset >= 1 && offset <= totalPages);
	}
	
}
