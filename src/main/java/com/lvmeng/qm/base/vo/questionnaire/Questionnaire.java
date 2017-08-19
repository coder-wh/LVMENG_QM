package com.lvmeng.qm.base.vo.questionnaire;

import java.util.List;

import com.lvmeng.qm.base.vo.AbstractModel;

public class Questionnaire extends AbstractModel{
	/**
	 * 
	 */
	private Integer qnId;
	private Integer panelId;
	private String startTime;
	private String endTime;
	private String answerDuration;
	private String recordDuration;
	private String ip;
	private String host;
	private String customerName;
	private String name;
	private String email;
	private String phone;
	private String telephone;
	private String proName;
	private String saler;
	private String proManager;
	private String serialNum;
	private String productName;
	private String proCoder;
	private String engineer;
	private String province;
	private List<String> baseInfo;
	private List<String> questionnaire;
	
	public List<String> getBaseInfo() {
		return baseInfo;
	}
	public void setBaseInfo(List<String> baseInfo) {
		this.baseInfo = baseInfo;
	}
	public Integer getQnId() {
		return qnId;
	}
	public void setQnId(Integer qnId) {
		this.qnId = qnId;
	}
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
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getAnswerDuration() {
		return answerDuration;
	}
	public void setAnswerDuration(String answerDuration) {
		this.answerDuration = answerDuration;
	}
	public String getRecordDuration() {
		return recordDuration;
	}
	public void setRecordDuration(String recordDuration) {
		this.recordDuration = recordDuration;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getProManager() {
		return proManager;
	}
	public void setProManager(String proManager) {
		this.proManager = proManager;
	}
	public String getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProCoder() {
		return proCoder;
	}
	public void setProCoder(String proCoder) {
		this.proCoder = proCoder;
	}
	public String getEngineer() {
		return engineer;
	}
	public void setEngineer(String engineer) {
		this.engineer = engineer;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public List<String> getQuestionnaire() {
		return questionnaire;
	}
	public void setQuestionnaire(List<String> questionnaire) {
		this.questionnaire = questionnaire;
	}
	
}
