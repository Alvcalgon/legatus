package com.tfg.motorsportf1.appweb.motorsportf1.domain;

public class ConstructorStanding {

	// Attributos --------------------------
	private String season;
	
	private String position;
	
	private Integer points;
	
	private Constructor constructor;
	
	
	// Constructores ----------------------
	public ConstructorStanding() {
		super();
	}
	
	
	// Getters y setters	
	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}
	
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Constructor getConstructor() {
		return constructor;
	}

	public void setConstructor(Constructor constructor) {
		this.constructor = constructor;
	}
	
	
	@Override
	public String toString() {
		return "ConstructorStanding [season=" + this.season + ", constructor=" + this.getConstructor() + ", position=" + this.position + ", points" + this.points + "]"; 
	}
	
}
