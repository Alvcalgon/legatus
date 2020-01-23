package com.tfg.motorsportf1.appweb.motorsportf1.domain;

public class DriverStanding {

	// Atributos --------------------------
	private String season;
	
	private Integer points;
	
	private String position;
	
	private Driver driver;
	
	private Constructor constructor;
	
	// Constructores ---------------------
	public DriverStanding() {
		super();
	}
	
	// Getter y setters -------------------	
	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
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
		return "DriverStanding [season=" + this.season + ", driver=" + this.getDriver().getFullname() + ", constructor=" + this.getConstructor().getName() + "]";
	}
	
}
