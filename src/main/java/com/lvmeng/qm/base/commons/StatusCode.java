package com.lvmeng.qm.base.commons;

public class StatusCode {
	/* 成功 */
	public static final String SUCCESS = "s";
	/* 失败 */
	public static final String FAILURE = "f";
	/* token失效 */
	public static final String INVALID = "u";
	/* 无权限 */
	public static final String FORBIDDEN="n";


	public static final String VALIDATE_CODE_ERROR="验证码不正确";
	public static final String VALIDATE_CODE_EXPIRATION="验证码过期";
	public static final String USER_INVALID = "用户失效，请重新登录";
	public static final String KEY_INVALID = "秘钥无效";
	public static final String USER_IS_NULL = "用户为空";
	public static final String AUDIO_IS_NULL = "音频文件不存在稍后再试";
	public static final String ACCOUNT_DISABLED = "账户已经被停用";
	public static final String UNAUTH_USERS = "未被授权用户";
	public static final String USERNAME_ERROR = "账户名不存在";
	public static final String PASSWORD_ERROR = "密码错误";
	public static final String PASSWORD_OLD_ERROR = "旧密码错误";
	public static final String ILLEGAL_LOGIN = "非法登录";
	public static final String USER_MODIFY_ERROR = "用户修改个人信息失败";
	public static final String USER_SAVE_ERROR = "用户存储信息失败";
	public static final String USER_DELETE_ERROR = "删除修改个人信息失败";
	public static final String EMPTY_DATA = "没有找到符合条件的数据";
	public static final String USERNAME_EXIST="用户名已存在";
	public static final String ROLENAME_EXIST="角色名已存在";
	// 没有查到单个对象
	public static final String ERROR_DATA = "数据错误！";
	public static final String ERROR_MESSAGE_DATA = "数据解析出现错误！";
	public static final String ERROR_PARAMTER = "参数错误！";
	public static final String ADD_MANAY = "已经存在对应数据！";
	public static final String ADD_TOO_MANAY = "已经存在对应数据,不允许添加！";
	public static final String ERROR_SYSTEM = "系统错误！";
	public static final String ERROR_PARAMTER_OUTLIMIT = "参数过长！";
	public static final String ERROR_MESSAGE_NULL="数据为空";
	public static final String ERROR_INTERFACE="请求接口错误";
	public static final String ERROR_Id="id不存在";
	public static final String 	EXIST_ROLEId="角色id被占用";

	public static final String ERROR_UpdateErro = "禁止修改！";

	public static final String DUPLICATION_NAME = "重名";
	public static final String ERROR_AUTHORITY = "权限错误！";

	public static final String ERROR_UPLOAD_SERVICE = "上传服务错误！";
	public static final String ERROR_CONVERT_SERVICE = "转码服务错误！";
	
	
	//错误
	public static final int ERROR = 999;
}