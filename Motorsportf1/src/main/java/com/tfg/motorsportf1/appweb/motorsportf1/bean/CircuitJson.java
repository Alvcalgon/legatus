package com.tfg.motorsportf1.appweb.motorsportf1.bean;

import java.util.Arrays;

import com.tfg.motorsportf1.appweb.motorsportf1.domain.Circuit;

public class CircuitJson extends PaginationJson {

	private Circuit[] content;
	
	public CircuitJson() {
		super();
	}

	public Circuit[] getContent() {
		return content;
	}

	public void setContent(Circuit[] content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "CircuitJson [content=" + Arrays.toString(content) + ", getPageable()=" + getPageable()
				+ ", getTotalPages()=" + getTotalPages() + ", getTotalElements()=" + getTotalElements() + ", getLast()="
				+ getLast() + ", getFirst()=" + getFirst() + ", getSort()=" + getSort() + ", getNumberOfElements()="
				+ getNumberOfElements() + ", getSize()=" + getSize() + ", getNumber()=" + getNumber() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}

}
