package com.tfg.motorsportf1.appweb.motorsportf1.forms;

import javax.validation.constraints.Min;

public class PaginationForm {
	
	@Min(1)
	private Integer offset;
	
	public PaginationForm() {
		super();
		
		this.offset = 1;
	}
	
	public PaginationForm(Integer offset) {
		super();
		
		this.offset = offset;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	
	@Override
	public String toString() {
		return "PaginationForm [offset=" + offset + "]";
	}

}
