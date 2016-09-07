package com.web.entity;

import java.io.Serializable;

public class Product implements Serializable{
	private int pid;
	private String pname;
	private String recommendDoctor;
	private String productDescription;
	private String createTime;
	private int iseffective;
	public Product(int pid, String pname, String recommendDoctor,
			String productDescription, String createTime, int iseffective) {
		this.pid = pid;
		this.pname = pname;
		this.recommendDoctor = recommendDoctor;
		this.productDescription = productDescription;
		this.createTime = createTime;
		this.iseffective = iseffective;
	}
	
	public Product() {
	}
	
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getRecommendDoctor() {
		return recommendDoctor;
	}
	public void setRecommendDoctor(String recommendDoctor) {
		this.recommendDoctor = recommendDoctor;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public int getIseffective() {
		return iseffective;
	}
	public void setIseffective(int iseffective) {
		this.iseffective = iseffective;
	}
	
	
}
