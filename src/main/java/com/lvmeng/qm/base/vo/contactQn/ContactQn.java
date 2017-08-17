package com.lvmeng.qm.base.vo.contactQn;

import com.lvmeng.qm.base.vo.BaseQn;

public class ContactQn extends BaseQn {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3272480298319510858L;
	private Integer panelId;
	private String startTime;
	private String customerName;
	private String name;
	private String phone;
	private String telephone;
	public Integer getPanelId() {
		return panelId;
	}
	public void setPanelId(Integer panelId) {
		this.panelId = panelId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
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
