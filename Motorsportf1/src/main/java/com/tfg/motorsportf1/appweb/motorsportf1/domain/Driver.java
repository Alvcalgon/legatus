package com.tfg.motorsportf1.appweb.motorsportf1.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Driver {
	
	// Atributos --------------------------
	private String fullname;
		
	private String nacionality;
		
	private String dateOfBirth;	
	
	private String information;
	
	// Constructores -------------------------
	public Driver() {
		super();
	}
		
	public Driver(String fullname,
				  String nacionality,
				  String dateOfBirth,
			      String information) {
		super();
		
		this.fullname = fullname;
		this.nacionality = nacionality;
		this.dateOfBirth = dateOfBirth;
		this.information = information;
	}
	

	// Getters y setters ----------------------	
	public String getFullname() {
		return fullname;
	}
	
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	public String getNacionality() {
		return nacionality;
	}

	public void setNacionality(String nacionality) {
		this.nacionality = nacionality;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}
	
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result + ((fullname == null) ? 0 : fullname.hashCode());
		result = prime * result + ((information == null) ? 0 : information.hashCode());
		result = prime * result + ((nacionality == null) ? 0 : nacionality.hashCode());
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
		Driver other = (Driver) obj;
		if (dateOfBirth == null) {
			if (other.dateOfBirth != null)
				return false;
		} else if (!dateOfBirth.equals(other.dateOfBirth))
			return false;
		if (fullname == null) {
			if (other.fullname != null)
				return false;
		} else if (!fullname.equals(other.fullname))
			return false;
		if (information == null) {
			if (other.information != null)
				return false;
		} else if (!information.equals(other.information))
			return false;
		if (nacionality == null) {
			if (other.nacionality != null)
				return false;
		} else if (!nacionality.equals(other.nacionality))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Driver [fullname=" + this.fullname + ", nacionality=" + this.nacionality + "]";
	}

}
