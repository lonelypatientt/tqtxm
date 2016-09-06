package com.web.entity;

import java.io.Serializable;

/**
 * 部门实体类
 * @author Administrator
 *
 */
public class Department implements Serializable  {
	/**
	 * •部门主键
	•部门名称
	•部门主管ID
	•部门描述
	•部门是否有效
	 */
	private int did;
	private String dname;
	private int directorID;
	private String description;
	private int iseffective;
	public int getDid() {
		return did;
	}
	public void setDid(int did) {
		this.did = did;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	public int getDirectorID() {
		return directorID;
	}
	public void setDirectorID(int directorID) {
		this.directorID = directorID;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getIseffective() {
		return iseffective;
	}
	public void setIseffective(int iseffective) {
		this.iseffective = iseffective;
	}
	public Department(int did, String dname, int directorID,
			String description, int iseffective) {
		this.did = did;
		this.dname = dname;
		this.directorID = directorID;
		this.description = description;
		this.iseffective = iseffective;
	}
	
	public Department() {
	}
	
	
}
