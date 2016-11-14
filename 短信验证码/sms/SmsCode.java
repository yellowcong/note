package com.yellowcong.alibaba.sms;
/**
 * SmsCode用来存储 模版字眼和我们的对应模版
 * @author yellowcong
 * @date 2016年3月26日
 *
 */
public class SmsCode {
	private String modeId;
	private String templateCode;
	
	
	public SmsCode(String modeId, String templateCode) {
		super();
		this.modeId = modeId;
		this.templateCode = templateCode;
	}
	public SmsCode() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getModeId() {
		return modeId;
	}
	public void setModeId(String modeId) {
		this.modeId = modeId;
	}
	public String getTemplateCode() {
		return templateCode;
	}
	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}
}
