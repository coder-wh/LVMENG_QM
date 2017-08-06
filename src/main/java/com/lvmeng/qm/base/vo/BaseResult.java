package com.lvmeng.qm.base.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lvmeng.qm.base.commons.StatusCode;



public class BaseResult extends AbstractModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2107737783208423478L;
	//状态码
	@JsonProperty("c")
	private String statusCode;
	//提示语句
	@JsonProperty("i")
	private String statusCodeInfo;
	public String getStatusCode() {
		if(statusCode==null)
		{
			statusCode=StatusCode.SUCCESS;
		}
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusCodeInfo() {
		return statusCodeInfo;
	}
	public void setStatusCodeInfo(String statusCodeInfo) {
		this.statusCodeInfo = statusCodeInfo;
	}
	public BaseResult failure(String message) {  
		this.statusCode=StatusCode.FAILURE;
		this.statusCodeInfo=message;
        return this;  
    }
	public BaseResult success() {  
		this.statusCode=StatusCode.SUCCESS;
        return this;  
    }


}
