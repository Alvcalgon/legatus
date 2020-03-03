package com.tfg.motorsportf1.appweb.motorsportf1.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
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
	
	public Circuit(String name, String location, String type, String lapDistance) {
		super();
		this.name = name;
		this.location = location;
		this.type = type;
		this.lapDistance = lapDistance;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lapDistance == null) ? 0 : lapDistance.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Circuit other = (Circuit) obj;
		if (lapDistance == null) {
			if (other.lapDistance != null)
				return false;
		} else if (!lapDistance.equals(other.lapDistance))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
		
}