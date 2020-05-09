package com.tfg.motorsportf1.appweb.motorsportf1.domain;

public class Item {

	private String name;
	private Integer count;
	
		
	public Item() {
		super();
	}
	
		
	public Item(String name, Integer count) {
		super();
		this.name = name;
		this.count = count;
	}

	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getCount() {
		return count;
	}
	
	public void setCount(Integer count) {
		this.count = count;
	}
	
}
