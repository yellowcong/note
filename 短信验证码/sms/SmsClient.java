package com.yellowcong.alibaba.sms;

import java.util.UUID;

import com.sun.media.sound.AlawCodec;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import com.yellowcong.utils.JsonUtils;

/**
 * 发送短信
 * @author yellowcong
 * @date 2016年3月26日
 *
 */
public class SmsClient {
	
	/**
	 * 
	 * @param phone 电话号码
	 * @param code 短信类型 
	 */
	public static String sendSMS(String phone,SmsType type){
		return sendSMS(phone, type,null);
	}
	/**
	 * 
	 * @param phone 电话号码
	 * @param code 短信类型 
	 * 
	 * @param active 活动名称 ，默认可以不写
	 * @return 
	 */
	public static String sendSMS(String phone,SmsType type,String active){
		String uuidStr = "";
		try {
			TaobaoClient client = new DefaultTaobaoClient(AlibabaValue.URL,AlibabaValue.APPKEY,AlibabaValue.SECRET);
			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
			//id 来表示用户
			req.setExtend("123456");
			//短信类型
			req.setSmsType("normal");
			
			SmsCode smsCode = AlibabaValue.CODE.get(type);
			//发送短信的人
			req.setSmsFreeSignName(smsCode.getModeId());
			
			if(type.toString().equals("Active")){
				if(active == null || "".equals(active)){
					active ="打酱油";
				}
				SmsActiveMessage msg = SmsClient.getActiveMsg(active);
				
				req.setSmsParamString(JsonUtils.object2Json(msg));
				uuidStr = msg.getCode();
			}else{
				SmsMessage msg = SmsClient.getMsg();
				uuidStr = msg.getCode();
				//模版中的需要参数
				req.setSmsParamString(JsonUtils.object2Json(msg));
			}
			//接受的电话
			req.setRecNum(phone);
			//模版代码
			req.setSmsTemplateCode(smsCode.getTemplateCode());
			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
			System.out.println(rsp.getBody());
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return uuidStr;
	}
	/**
	 * 获取请求的param
	 * @return
	 */
	public static SmsMessage getMsg(){
		//随机生成的代码
		String code = UUID.randomUUID().toString().substring(0, 4);
		SmsMessage msg = new SmsMessage();
		msg.setCode(code);
		msg.setProduct(AlibabaValue.WEBName);
		return msg;
		
	}
	/**
	 * 获取请求的param
	 * @return
	 */
	public static SmsActiveMessage getActiveMsg(String active){
		//随机生成的代码
		String code = UUID.randomUUID().toString().substring(0, 4);
		SmsActiveMessage msg = new SmsActiveMessage();
		msg.setCode(code);
		msg.setProduct(AlibabaValue.WEBName);
		msg.setItem(active);
		return msg;
		
	}
}
