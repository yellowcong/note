package com.yellowcong.alibaba.sms;

import java.util.HashMap;
import java.util.Map;

/**
 * 设定一些常量，是我们使用阿里巴巴的短信服务所需要配置的
 * @author yellowcong
 * @date 2016年3月26日
 *
 */
public class AlibabaValue {
	//阿里请求的路径
	public static String URL ="http://gw.api.taobao.com/router/rest";
	//app的密钥
	public static String APPKEY ="23332780";
	public static String SECRET = "631c69ac31d91d539fd22788537431a2";
	//我网站的名称
	public static String WEBName = "yellowcongの博客";
	
	//定义操作的一些短信代码
	public static Map<SmsType,SmsCode> CODE= new HashMap<SmsType, SmsCode>();
	static{
		CODE.put(SmsType.Regist,new SmsCode("注册验证", "SMS_6700477"));
		CODE.put(SmsType.Login,new SmsCode("登录验证", "SMS_6700479"));
		CODE.put(SmsType.LoginTrouble,new SmsCode("登录验证", "SMS_6700478"));
		CODE.put(SmsType.CheckID,new SmsCode("身份验证", "SMS_6700481"));
		//验证码${code}，您正在尝试变更${product}重要信息，请妥善保管账户信息。 
		CODE.put(SmsType.Update,new SmsCode("变更验证", "SMS_6700474"));
		//修改密码  验证码${code}，您正在尝试修改${product}登录密码，请妥善保管账户信息。 验证码${code}，您正在尝试修改${product}登录密码，请妥善保管账户信息。 
		CODE.put(SmsType.Password,new SmsCode("变更验证", "SMS_6700475"));
		//
		//验证码${code}，您正在参加${product}的${item}活动，请确认系本人申请。 
		//活动
		CODE.put(SmsType.Active,new SmsCode("活动验证", "SMS_6700476"));
	}
}
