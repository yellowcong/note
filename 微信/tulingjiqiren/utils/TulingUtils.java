package com.yellowcong.tuling.utils;

import java.util.HashMap;
import java.util.Map;

import com.yellowcong.tuling.model.Message;
import com.yellowcong.tuling.model.TulingValue;
import com.yellowcong.utils.HttpClientUtils;
import com.yellowcong.utils.JsonUtils;

/**
 * 通过这个对象来搞定发送数据
 * @author yellowcong
 * @date 2016年3月26日
 *
 */
public class TulingUtils {
	
	/**
	 * 发送数据到图灵
	 * @param info
	 */
	public static Message sendTuling(String info){
		Map<String,String> params = new HashMap<String, String>();
		params.put("key", TulingValue.APIKEY);
		params.put("info", info);
		
		//发送数据
		String json  = HttpClientUtils.post(TulingValue.URL, params);
		System.out.println(json);
		//将json数据转化为object
		return JsonUtils.json2Object(json, Message.class);
	}
}
