package com.dph.common.entity;

import java.util.List;

public class Page<T> {

	private int pageNo = 1;
	private int pageSize = 30;
	private int totalRecord;
	private int totalPage;
	private List<T> results;

	public void initTotal() {
		if (pageSize != 0) {
			totalPage = (int) Math.ceil((double) totalRecord / pageSize);
		}
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		if (pageNo == null || pageNo < 1) {
			return;
		}

		this.pageNo = pageNo;
	}

	public void setPageNo(int pageNo) {
		if (pageNo < 1) {
			return;
		}

		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		if (pageSize == null || pageSize < 1) {
			return;
		}

		this.pageSize = pageSize;
	}

	public void setPageSize(int pageSize) {
		if (pageSize < 1) {
			return;
		}

		this.pageSize = pageSize;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}

}
