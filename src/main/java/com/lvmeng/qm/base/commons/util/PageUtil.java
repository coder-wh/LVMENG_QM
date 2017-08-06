package com.lvmeng.qm.base.commons.util;


public class PageUtil {
	
	public static int getPageOffset(int pageNo,int pageSize){
		return (pageNo-1)*pageSize;
	}
	
}
