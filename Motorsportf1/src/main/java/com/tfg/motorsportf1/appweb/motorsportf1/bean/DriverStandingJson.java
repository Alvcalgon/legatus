package com.tfg.motorsportf1.appweb.motorsportf1.bean;

import java.util.Arrays;

import com.tfg.motorsportf1.appweb.motorsportf1.domain.DriverStanding;

public class DriverStandingJson extends PaginationJson {

	private DriverStanding[] content;
	
	public DriverStandingJson() {
		super();
	}

	public DriverStanding[] getContent() {
		return content;
	}

	public void setContent(DriverStanding[] content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "DriverStandingJson [content=" + Arrays.toString(content) + ", getPageable()=" + getPageable()
				+ ", getTotalPages()=" + getTotalPages() + ", getTotalElements()=" + getTotalElements() + ", getLast()="
				+ getLast() + ", getFirst()=" + getFirst() + ", getSort()=" + getSort() + ", getNumberOfElements()="
				+ getNumberOfElements() + ", getSize()=" + getSize() + ", getNumber()=" + getNumber() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}
	
}
