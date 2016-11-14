package com.yellowcong.alibaba.sms;

public class SmsActiveMessage {
	//验证码
		private String code;
		//产品的名称
		private String product;
		private String item;
		
		public SmsActiveMessage( String item,SmsMessage msg) {
			super();
			this.code = msg.getCode();
			this.product = msg.getProduct();
			this.item = item;
		}
		public SmsActiveMessage(String code, String product, String item) {
			super();
			this.code = code;
			this.product = product;
			this.item = item;
		}
		public SmsActiveMessage() {
			super();
			// TODO Auto-generated constructor stub
		}
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
		public String getItem() {
			return item;
		}
		public void setItem(String item) {
			this.item = item;
		}
		
}
