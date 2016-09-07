package com.web.vo;

import java.io.Serializable;

public class UserVo implements Serializable{

	/*
	 * •用户主键
		•员工工号
		•用户登录名
		•用户密码
		•真实姓名
		•手机号
		•用户头像
		•邮箱
		•QQ
		•微信
		•紧急联系人
		•紧急联系人手机号
		•所属部门名称
		•入职时间
		•是否有效
	 */
		private int uid;
		private String uNo;
		private String userName;
		private String userPassWord;
		private String realName;
		private String phone;
		private String avatar;
		private String emali;
		private String qq;
		private String weChatNo;
		private String emergencyContactPerson;
		private String emergencyContactPhone;
		private String departmentName;
		private String entryTime;
		public UserVo(int uid, String uNo, String userName,
				String userPassWord, String realName, String phone,
				String avatar, String emali, String qq, String weChatNo,
				String emergencyContactPerson, String emergencyContactPhone,
				String departmentName, String entryTime) {
			super();
			this.uid = uid;
			this.uNo = uNo;
			this.userName = userName;
			this.userPassWord = userPassWord;
			this.realName = realName;
			this.phone = phone;
			this.avatar = avatar;
			this.emali = emali;
			this.qq = qq;
			this.weChatNo = weChatNo;
			this.emergencyContactPerson = emergencyContactPerson;
			this.emergencyContactPhone = emergencyContactPhone;
			this.departmentName = departmentName;
			this.entryTime = entryTime;
		}
		
		public UserVo() {
		}

		public int getUid() {
			return uid;
		}

		public void setUid(int uid) {
			this.uid = uid;
		}

		public String getuNo() {
			return uNo;
		}

		public void setuNo(String uNo) {
			this.uNo = uNo;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getUserPassWord() {
			return userPassWord;
		}

		public void setUserPassWord(String userPassWord) {
			this.userPassWord = userPassWord;
		}

		public String getRealName() {
			return realName;
		}

		public void setRealName(String realName) {
			this.realName = realName;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getAvatar() {
			return avatar;
		}

		public void setAvatar(String avatar) {
			this.avatar = avatar;
		}

		public String getEmali() {
			return emali;
		}

		public void setEmali(String emali) {
			this.emali = emali;
		}

		public String getQq() {
			return qq;
		}

		public void setQq(String qq) {
			this.qq = qq;
		}

		public String getWeChatNo() {
			return weChatNo;
		}

		public void setWeChatNo(String weChatNo) {
			this.weChatNo = weChatNo;
		}

		public String getEmergencyContactPerson() {
			return emergencyContactPerson;
		}

		public void setEmergencyContactPerson(String emergencyContactPerson) {
			this.emergencyContactPerson = emergencyContactPerson;
		}

		public String getEmergencyContactPhone() {
			return emergencyContactPhone;
		}

		public void setEmergencyContactPhone(String emergencyContactPhone) {
			this.emergencyContactPhone = emergencyContactPhone;
		}

		public String getDepartmentName() {
			return departmentName;
		}

		public void setDepartmentName(String departmentName) {
			this.departmentName = departmentName;
		}

		public String getEntryTime() {
			return entryTime;
		}

		public void setEntryTime(String entryTime) {
			this.entryTime = entryTime;
		}
		
		
		
		
}

