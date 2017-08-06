package com.lvmeng.qm.base.commons.util;


public class Constant {

	/**
	 * jwt
	 */
	public static final String JWT_ID = "jwt";
//	public static final String JWT_SECRET = "7786df7fc3a34e26a61c034d5ec8245d";
	public static final String JWT_SECRET = "7786df7f34e26a61c034d5ec8245dabc";
	public static final String WX_SECRET = "98101529752571005549971025050101";
	public static final String QR_SECRET = "KJOIho;lsdoho;ls&567w9)";
	public static final int JWT_TTL = 60*60*1000;  //millisecond
	public static final int JWT_REFRESH_INTERVAL = 55*60*1000;  //millisecond
	public static final int JWT_REFRESH_TTL = 12*60*60*1000;  //millisecond
	/**
	 * 用户登录有效期
	 */
	public static final int USER_TTL = 12*60*60*1000;  //millisecond
	
}
