package com.web.vo;

import java.io.Serializable;

public class MenuVo2 implements Serializable{
	private int mid;
	private String mname;
	private int  num;
	public MenuVo2(int mid, String mname, int m) {
		this.mid = mid;
		this.mname = mname;
		this.num = m;
	}
	public MenuVo2() {
	}
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
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
	
	
}

