package com.lvmeng.qm.base.vo.contactQn;

import com.lvmeng.qm.base.vo.BaseQn;

public class ContactQn extends BaseQn {
	/**
	 * 
	 */
	private String endTime;
	private String customerName;
	private String name;
	private String phone;
	private String telephone;
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
}
