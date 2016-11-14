package com.yellowcong.alibaba.sms;
/**
 * 通过SmsType来解决信息变更问题
 * @author yellowcong
 * @date 2016年3月26日
 *
 */
public enum SmsType {
/**
 * CODE.put("注册验证", "SMS_6700477");
	CODE.put("登录验证", "SMS_6700479");
	CODE.put("身份验证", "SMS_6700481");
	//验证码${code}，您正在尝试变更${product}重要信息，请妥善保管账户信息。 
	CODE.put("变更验证", "SMS_6700474");
	//修改密码  验证码${code}，您正在尝试修改${product}登录密码，请妥善保管账户信息。 验证码${code}，您正在尝试修改${product}登录密码，请妥善保管账户信息。 
	CODE.put("变更密码验证", "SMS_6700475");
	//验证码${code}，您正在参加${product}的${item}活动，请确认系本人申请。 
	CODE.put("活动验证", "SMS_6700479");
 */
	Regist,//注册
	Login, //登录
	LoginTrouble,//登录有问题
	Update, //更新信息
	Active,//活动
	CheckID, //身份验证
	Password;  //更新密码
	
}
