package com.tfg.motorsportf1.appweb.motorsportf1.bean;

import java.util.Arrays;

import com.tfg.motorsportf1.appweb.motorsportf1.domain.Driver;

public class DriverJson extends PaginationJson {

	private Driver[] content;
	
	public DriverJson() {
		super();
	}

	public Driver[] getContent() {
		return content;
	}

	public void setContent(Driver[] content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "DriverJson [content=" + Arrays.toString(content) + ", getPageable()=" + getPageable()
				+ ", getTotalPages()=" + getTotalPages() + ", getTotalElements()=" + getTotalElements() + ", getLast()="
				+ getLast() + ", getFirst()=" + getFirst() + ", getSort()=" + getSort() + ", getNumberOfElements()="
				+ getNumberOfElements() + ", getSize()=" + getSize() + ", getNumber()=" + getNumber() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}
	
}
