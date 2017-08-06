package com.lvmeng.qm.base.vo.saleQn;

import java.util.List;

import com.lvmeng.qm.base.vo.AbstractModel;

public class SaleQn extends AbstractModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8443632148826898960L;
	private Integer panelId;
	private String startTime;
	private String proCoder;
	private String proName;
	private String saler;
	private String customerName;
	private String name;
	private String phone;
	private String telephone;
	private List<String> questionnaire;
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
	public String getProCoder() {
		return proCoder;
	}
	public void setProCoder(String proCoder) {
		this.proCoder = proCoder;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getSaler() {
		return saler;
	}
	public void setSaler(String saler) {
		this.saler = saler;
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
	public List<String> getQuestionnaire() {
		return questionnaire;
	}
	public void setQuestionnaire(List<String> questionnaire) {
		this.questionnaire = questionnaire;
	}
	
	
}
