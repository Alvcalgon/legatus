package com.tfg.motorsportf1.appweb.motorsportf1.bean;


public class PaginationJson {

	private Object pageable;
	private Integer totalPages;
	private Integer totalElements;
	private Boolean last;
	private Boolean first;
	private Object sort;
	private Integer numberOfElements;
	private Integer size;
	private Integer number;
	
	public PaginationJson() {
		super();
	}

	public Object getPageable() {
		return pageable;
	}

	public void setPageable(Object pageable) {
		this.pageable = pageable;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public Integer getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(Integer totalElements) {
		this.totalElements = totalElements;
	}

	public Boolean getLast() {
		return last;
	}

	public void setLast(Boolean last) {
		this.last = last;
	}

	public Boolean getFirst() {
		return first;
	}

	public void setFirst(Boolean first) {
		this.first = first;
	}

	public Object getSort() {
		return sort;
	}

	public void setSort(Object sort) {
		this.sort = sort;
	}

	public Integer getNumberOfElements() {
		return numberOfElements;
	}

	public void setNumberOfElements(Integer numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "PaginationJson [pageable=" + pageable + ", totalPages=" + totalPages + ", totalElements="
				+ totalElements + ", last=" + last + ", first=" + first + ", sort=" + sort + ", numberOfElements="
				+ numberOfElements + ", size=" + size + ", number=" + number + "]";
	}
}
