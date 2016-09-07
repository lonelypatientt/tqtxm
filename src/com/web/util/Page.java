package com.web.util;

import java.io.Serializable;
import java.util.List;
public class Page<T> implements Serializable {
	//当前显示的页数
	private  int pageNo=1;
	private int pageSize=10;
	private List<T> dateList;
	private  long total;
	
	public Page() {
	}
	
	public Page(int pageNo, int pageSize, List<T> dateList, long total) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.dateList = dateList;
		this.total = total;
	}
	
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public List<T> getDateList() {
		return dateList;
	}
	public void setDateList(List<T> dateList) {
		this.dateList = dateList;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
		
}
