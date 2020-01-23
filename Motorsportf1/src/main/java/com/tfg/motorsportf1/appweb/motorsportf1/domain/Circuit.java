package com.tfg.motorsportf1.appweb.motorsportf1.domain;

public class Circuit {

	
	// Atributos --------------------------------	
	private String name;
	
	private String location;
	
	private String type;
	
	private String lapDistance;
	
	
	// Constructores ------------------------------
	public Circuit() {
		super();
	}

	// Getters y setters ----------------------
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLapDistance() {
		return lapDistance;
	}

	public void setLapDistance(String lapDistance) {
		this.lapDistance = lapDistance;
	}
		
	
	@Override
	public String toString() {
		return "Circuit [name=" + this.name + ", location=" + this.location + ", type=" + this.type + ", distance=" + this.lapDistance + "]";
	}
	
}