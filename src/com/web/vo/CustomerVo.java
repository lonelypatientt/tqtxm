package com.web.vo;

import java.io.Serializable;

public class CustomerVo implements Serializable{

	/*
	 *•客户主键
		•预约号（填写框，唯一）
		•客户姓名（填写框）
		•客户性别（下拉式）
		•客户电话（填写框，唯一）
		•客户电话2（填写框，唯一）
		•客户来源（下拉式，见系统维护中的来源渠道列表）
		•客户QQ（填写框）
		•咨询产品（下拉框）
		•咨询师（默认为自己）
		•首次成交金额（填写框）
		•成交总金额（填写框）
		•详细地址（填写框）
		•预计到店时间（选择框）
		•预计回访时间（选择框）
	 */
		private int cid;
		private String interviewnumber;
		private String cname;
		private int csex;
		private String cphone1;
		private String cphone2;
		private String ciname;
		private String cqq;
		private String pname;
		private String uname;
		private String firstdealmoney;
		private String dealallmoney;
		private String address;
		private String cometostoretime;
		private String cometocustomertime;
		public CustomerVo(int cid, String interviewnumber, String cname,
				int csex, String cphone1, String cphone2, String ciname,
				String cqq, String pname, String uname, String firstdealmoney,
				String dealallmoney, String address, String cometostoretime,
				String cometocustomertime) {
			this.cid = cid;
			this.interviewnumber = interviewnumber;
			this.cname = cname;
			this.csex = csex;
			this.cphone1 = cphone1;
			this.cphone2 = cphone2;
			this.ciname = ciname;
			this.cqq = cqq;
			this.pname = pname;
			this.uname = uname;
			this.firstdealmoney = firstdealmoney;
			this.dealallmoney = dealallmoney;
			this.address = address;
			this.cometostoretime = cometostoretime;
			this.cometocustomertime = cometocustomertime;
		}
		public CustomerVo() {
		}
		public int getCid() {
			return cid;
		}
		public void setCid(int cid) {
			this.cid = cid;
		}
		public String getInterviewnumber() {
			return interviewnumber;
		}
		public void setInterviewnumber(String interviewnumber) {
			this.interviewnumber = interviewnumber;
		}
		public String getCname() {
			return cname;
		}
		public void setCname(String cname) {
			this.cname = cname;
		}
		public int getCsex() {
			return csex;
		}
		public void setCsex(int csex) {
			this.csex = csex;
		}
		public String getCphone1() {
			return cphone1;
		}
		public void setCphone1(String cphone1) {
			this.cphone1 = cphone1;
		}
		public String getCphone2() {
			return cphone2;
		}
		public void setCphone2(String cphone2) {
			this.cphone2 = cphone2;
		}
		public String getCiname() {
			return ciname;
		}
		public void setCiname(String ciname) {
			this.ciname = ciname;
		}
		public String getCqq() {
			return cqq;
		}
		public void setCqq(String cqq) {
			this.cqq = cqq;
		}
		public String getPname() {
			return pname;
		}
		public void setPname(String pname) {
			this.pname = pname;
		}
		public String getUname() {
			return uname;
		}
		public void setUname(String uname) {
			this.uname = uname;
		}
		public String getFirstdealmoney() {
			return firstdealmoney;
		}
		public void setFirstdealmoney(String firstdealmoney) {
			this.firstdealmoney = firstdealmoney;
		}
		public String getDealallmoney() {
			return dealallmoney;
		}
		public void setDealallmoney(String dealallmoney) {
			this.dealallmoney = dealallmoney;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getCometostoretime() {
			return cometostoretime;
		}
		public void setCometostoretime(String cometostoretime) {
			this.cometostoretime = cometostoretime;
		}
		public String getCometocustomertime() {
			return cometocustomertime;
		}
		public void setCometocustomertime(String cometocustomertime) {
			this.cometocustomertime = cometocustomertime;
		}
		
		
}

