package com.tfg.motorsportf1.appweb.motorsportf1.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {

	// Atributos --------------------------------	
	private String position;
	
	private String time;
	
	private Integer laps;
	
	private String grid;
	
	private Integer points;
	
	private Driver driver;
	
	private Constructor constructor;
	
	
	// Constructores --------------------------------
	public Result() {
		super();
	}
	
	// Getters y setters -----------------------------
	public String getPosition() {
		return position;
	}


	public void setPosition(String position) {
		this.position = position;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}


	public Integer getLaps() {
		return laps;
	}


	public void setLaps(Integer laps) {
		this.laps = laps;
	}


	public String getGrid() {
		return grid;
	}


	public void setGrid(String grid) {
		this.grid = grid;
	}


	public Integer getPoints() {
		return points;
	}


	public void setPoints(Integer points) {
		this.points = points;
	}


	public Driver getDriver() {
		return driver;
	}


	public void setDriver(Driver driver) {
		this.driver = driver;
	}


	public Constructor getConstructor() {
		return constructor;
	}


	public void setConstructor(Constructor constructor) {
		this.constructor = constructor;
	}
	
	
	@Override
	public String toString() {
		return "Result [driver=" + this.driver.getFullname() + ", constructor=" + this.getConstructor().getName() + ", position=" + this.position + "]";
	}
	
}