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

import com.tfg.motorsportf1.appweb.motorsportf1.services.utilities.AbstractTest;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UtilityServiceTest extends AbstractTest {

	// Service under test ---------------------
	@Autowired
	private UtilityService utilityService;
	
	
	// Suite test -------------------------------
	
	@Test
	public void test1_getPages() {
		int totalPages;
		List<Integer> pages;
		
		totalPages = 8;
		pages = this.utilityService.getPages(totalPages);
		
		assertNotNull(pages);
		assertTrue(pages.size() == totalPages);
	}
	
	@Test
	public void test2_getPages() {
		int totalPages;
		List<Integer> pages;
		
		totalPages = 0;
		pages = this.utilityService.getPages(totalPages);
		
		assertNotNull(pages);
		assertTrue(pages.size() == totalPages);
	}
	
	@Test
	public void test1_getValidOffset() {
		Optional<Integer> selectedPage;
		int totalElements, offset;
		
		selectedPage = Optional.of(1);
		totalElements = 0;
		
		offset = this.utilityService.getValidOffset(selectedPage, totalElements);
		
		assertTrue(offset == 1);
	}
	
	@Test
	public void test2_getValidOffset() {
		Optional<Integer> selectedPage;
		int totalElements, offset;
		
		selectedPage = null;
		totalElements = 5;
		
		offset = this.utilityService.getValidOffset(selectedPage, totalElements);
		
		assertTrue(offset == 1);
	}
	
	@Test
	public void test3_getValidOffset() {
		Optional<Integer> selectedPage;
		int totalElements, offset;
		
		selectedPage = Optional.of(1);
		totalElements = 70;
		
		offset = this.utilityService.getValidOffset(selectedPage, totalElements);
		
		assertTrue(offset == 1);
	}
	
	@Test
	public void test1_getEncodedText() {
		String text, encodedText;
		
		text = "Fernando";
		encodedText = this.utilityService.getEncodedText(text);
		
		assertNotNull(encodedText);
		assertTrue(encodedText.equals(encodedText));
	}
	
	@Test
	public void test2_getEncodedText() {
		String text, encodedText;
		
		text = "Fernando Alonso";
		encodedText = this.utilityService.getEncodedText(text);
		
		assertNotNull(encodedText);
		assertTrue(encodedText.equals("Fernando%20Alonso"));
	}
	
}
