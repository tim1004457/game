package com.pig8.api.business.common.entity.response;

import java.util.List;


/**
 * @author navy
 *
 */
public class Page<T> {

	//页码
	private long pageNo;
	//总页数
	private long pageCount;
	//数据总条数
	private long count;
	//当前页的数据
	private List<T> data;


	private long pageSize;

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public Page(long pageNo, long pageCount, long count,  List<T> data,long pageSize) {
		this.pageNo = pageNo;
		this.pageCount = pageCount;
		this.count = count;
		this.data = data;
		this.pageSize = pageSize;
	}
	
	public long getPageNo() {
		return pageNo;
	}

	public void setPageNo(long pageNo) {
		this.pageNo = pageNo;
	}

	public long getPageCount() {
		return pageCount;
	}

	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
	
}