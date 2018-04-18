package com.bigao.backend.module.sys.model;

import java.util.List;

public final class PaginationResult<E> {

	private int total = 0;
	private List<E> resultList;
	
	public PaginationResult(){}

	public PaginationResult(int total, List<E> result) {
		super();
		this.total = total;
		this.resultList = result;
	}

	public int getTotal() {
		return total;
	}

	public List<E> getResultList() {
		return resultList;
	}


}
