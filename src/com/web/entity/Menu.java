package com.web.entity;

import java.io.Serializable;

public class Menu implements Serializable{
	private int mid;
	private String mname;
	private String url;
	private int isshow;
	private int level;
	private int parentid;
	
	public Menu(){}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getIsshow() {
		return isshow;
	}

	public void setIsshow(int isshow) {
		this.isshow = isshow;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getParentid() {
		return parentid;
	}

	public void setParentid(int parentid) {
		this.parentid = parentid;
	}

	public Menu(int mid, String mname, String url, int isshow, int level,
			int parentid) {
		super();
		this.mid = mid;
		this.mname = mname;
		this.url = url;
		this.isshow = isshow;
		this.level = level;
		this.parentid = parentid;
	}

//	@Override
//	public String toString() {
//		return "{\"mid\":"+mid+",\"mname\":"+mname+",\"url\":"+url+",\"isshow\":"+isshow+",\"level\":"+level+",\"parentid\":"+parentid+"}";
//	}
//	
	
}
