package com.dph.common.entity;

import java.util.List;

public class Page<T> {

	private int pageNo = 1;
	private int pageSize = 5;
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

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo >= 1? pageNo : 1;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize >= 1? pageSize : 1;
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
