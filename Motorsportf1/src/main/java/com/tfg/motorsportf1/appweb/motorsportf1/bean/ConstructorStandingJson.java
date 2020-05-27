package com.tfg.motorsportf1.appweb.motorsportf1.bean;

import java.util.Arrays;

import com.tfg.motorsportf1.appweb.motorsportf1.domain.ConstructorStanding;

public class ConstructorStandingJson extends PaginationJson {

	private ConstructorStanding content[];

	public ConstructorStandingJson() {
		super();
	}

	public ConstructorStanding[] getContent() {
		return content;
	}

	public void setContent(ConstructorStanding[] content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "ConstructorStandingJson [content=" + Arrays.toString(content) + ", getPageable()=" + getPageable()
				+ ", getTotalPages()=" + getTotalPages() + ", getTotalElements()=" + getTotalElements() + ", getLast()="
				+ getLast() + ", getFirst()=" + getFirst() + ", getSort()=" + getSort() + ", getNumberOfElements()="
				+ getNumberOfElements() + ", getSize()=" + getSize() + ", getNumber()=" + getNumber() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}
		
}
