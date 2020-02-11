package com.tfg.motorsportf1.appweb.motorsportf1.forms;

import javax.validation.constraints.Min;

public class PaginationForm {
	
	@Min(1)
	private Integer offset;
	
	@Min(1)
	private Integer limit;

	
	public PaginationForm() {
		super();
		
		this.offset = 1;
		this.limit = 10;
	}
	
	public PaginationForm(Integer offset, Integer limit) {
		super();
		
		this.offset = offset;
		this.limit = limit;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	
	
	
}
