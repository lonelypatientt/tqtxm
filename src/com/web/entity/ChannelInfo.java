package com.web.entity;

import java.io.Serializable;

public class ChannelInfo implements Serializable{
	/**
	 * •渠道信息
			•渠道主键
			•渠道名称
			•渠道投入成本金额
			•渠道开发时间
			•渠道是否有效
	 */
	private int ciid;
	private String ciname;
	private double costmoney;
	private String developtime;
	private int iseffective;
	public ChannelInfo(int ciid, String ciname, double costmoney,
			String developtime, int iseffective) {
		this.ciid = ciid;
		this.ciname = ciname;
		this.costmoney = costmoney;
		this.developtime = developtime;
		this.iseffective = iseffective;
	}
	public ChannelInfo() {
	}
	public int getCiid() {
		return ciid;
	}
	public void setCiid(int ciid) {
		this.ciid = ciid;
	}
	public String getCiname() {
		return ciname;
	}
	public void setCiname(String ciname) {
		this.ciname = ciname;
	}
	public double getCostmoney() {
		return costmoney;
	}
	public void setCostmoney(double costmoney) {
		this.costmoney = costmoney;
	}
	public String getDeveloptime() {
		return developtime;
	}
	public void setDeveloptime(String developtime) {
		this.developtime = developtime;
	}
	public int getIseffective() {
		return iseffective;
	}
	public void setIseffective(int iseffective) {
		this.iseffective = iseffective;
	}
	
	
}
