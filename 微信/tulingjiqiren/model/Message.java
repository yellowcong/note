package com.yellowcong.tuling.model;

import java.util.List;
/**
 * 信息数据
 * @author yellowcong
 * @date 2016年3月26日
 *
 */
public class Message {
	private String code;
	//文字,100000
	private String text;
	//图片,200000 ,
	private String url;
	//菜谱，新闻308000
	private List<Date> list;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<Date> getList() {
		return list;
	}
	public void setList(List<Date> list) {
		this.list = list;
	}
}
