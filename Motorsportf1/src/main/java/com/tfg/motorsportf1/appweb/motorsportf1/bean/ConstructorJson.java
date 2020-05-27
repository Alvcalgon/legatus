package com.tfg.motorsportf1.appweb.motorsportf1.bean;

import java.util.Arrays;

import com.tfg.motorsportf1.appweb.motorsportf1.domain.Constructor;

public class ConstructorJson extends PaginationJson {

	private Constructor[] content;
	
	
	public ConstructorJson() {
		super();
	}

	public Constructor[] getContent() {
		return content;
	}

	public void setContent(Constructor[] content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "ConstructorJson [content=" + Arrays.toString(content) + ", getPageable()=" + getPageable()
				+ ", getTotalPages()=" + getTotalPages() + ", getTotalElements()=" + getTotalElements() + ", getLast()="
				+ getLast() + ", getFirst()=" + getFirst() + ", getSort()=" + getSort() + ", getNumberOfElements()="
				+ getNumberOfElements() + ", getSize()=" + getSize() + ", getNumber()=" + getNumber() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}
}
