package com.yellowcong.tuling.model;

/**
 * 列表中的数据
 * @author yellowcong
 * @date 2016年3月26日
 *
 *
 *新闻和菜谱
 */
public class Date {
/**
 * article 	新闻标题
source 	新闻来源
icon 	新闻图片
detailurl 	新闻详情链接
 */
	
/*


 "name": "鱼香肉丝",

"icon": "http://i4.xiachufang.com/image/280/cb1cb7c49ee011e38844b8ca3aeed2d7.jpg",

"info": "猪肉、鱼香肉丝调料 | 香菇、木耳、红萝卜、黄酒、玉米淀粉、盐",

"detailurl": "http://m.xiachufang.com/recipe/264781/"
 */
	private String article;
	private String source;
	//名称
	private String name;
	//信息
	private String info;
	private String icon;
	private String detailurl;
	
	public Date() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getArticle() {
		return article;
	}
	public void setArticle(String article) {
		this.article = article;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getDetailurl() {
		return detailurl;
	}
	public void setDetailurl(String detailurl) {
		this.detailurl = detailurl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
}
