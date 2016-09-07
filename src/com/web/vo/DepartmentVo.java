package com.web.vo;

import java.io.Serializable;

/**
 * 部门展现到jsp
 * @author Administrator
 *
 */
public class DepartmentVo implements Serializable  {
	/**
	 * •部门主键
	•部门名称
	•部门主管ID
	•部门描述
	•部门是否有效
	 */
	private int did;
	private String dname;
	private String  departmentBossName;
	private String description;
	private int iseffective;
	public DepartmentVo(int did, String dname, String departmentBossName,
			String description, int iseffective) {
		this.did = did;
		this.dname = dname;
		this.departmentBossName = departmentBossName;
		this.description = description;
		this.iseffective = iseffective;
	}
	public DepartmentVo() {
	}
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
	public String getDepartmentBossName() {
		return departmentBossName;
	}
	public void setDepartmentBossName(String departmentBossName) {
		this.departmentBossName = departmentBossName;
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
	
}
