package com.lvmeng.qm.base.vo.contactQn;

import java.util.List;

import com.lvmeng.qm.base.vo.AbstractModel;

public class ContactQn extends AbstractModel {
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
