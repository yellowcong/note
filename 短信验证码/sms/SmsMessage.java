package com.yellowcong.alibaba.sms;
/**
 * 短信的模版
 * @author yellowcong
 * @date 2016年3月26日
 *
 */
public class SmsMessage {
	//验证码
	private String code;
	//产品的名称
	private String product;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
}
