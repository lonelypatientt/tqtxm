package com.web.vo;

import java.io.Serializable;

public class MenuVo implements Serializable{
	private int mid;
	private String mname;
	private String url;
	private int isshow;//1表示要展示
	private int level;
	private String parentName;
	
	public MenuVo(){}

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

	public MenuVo(int mid, String mname, String url, int isshow, int level,
			String parentName) {
		super();
		this.mid = mid;
		this.mname = mname;
		this.url = url;
		this.isshow = isshow;
		this.level = level;
		this.parentName = parentName;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}


	
	
}

