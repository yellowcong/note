package com.yellowcong.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.common.collect.Multiset.Entry;

/**
 * 这个工具包是用来 做代理服务 爬去数据的，结果好多代理数据没有 
 * 2015-10 爬取 推酷数据 来做服务
 * 通过这个工具来来添加代理，来处理数据
 * @author yellowcong
 *
 */
public class HttpClientUtils {
	private static int timeout = 50000;

	/**
	 * 通过url来获取我们的GetMethod
	 * @param url
	 * @return
	 */
	public static GetMethod setGetMethod(String url) {
		// TODO Auto-generated method stub
		/* 2.生成 GetMethod 对象并设置参数 */
		GetMethod getMethod = null; 
		try{
			//可能会在查询的时候出现异常，我们简单的丢去
			getMethod = new GetMethod(url);
			// 设置 get 请求超时 5s
			getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, timeout);
			// 设置请求重试处理
			getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
			//Mozilla/5.0 (Windows; U; Windows NT 5.2) Gecko/2008070208 Firefox/3.0.1
			//Mozilla/5.0 (Windows; U; Windows NT 5.1) Gecko/20070309 Firefox/2.0.0.3
			//Mozilla/5.0 (Windows; U; Windows NT 5.1) Gecko/20070803 Firefox/1.5.0.12
			//Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0; WOW64; Trident/4.0; SLCC1)
			//Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727)
			//Mozilla/5.0 (Windows; U; Windows NT 5.2) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.2.149.27 Safari/525.13
			//设置USER_AGENT
			getMethod.getParams().setParameter(HttpMethodParams.USER_AGENT,"Mozilla/5.0 (Windows; U; Windows NT 5.1) Gecko/20070803 Firefox/1.5.0.12");
		}catch (Exception e){
			throw new RuntimeException("-------------------------请求协议存在问题-----------------------");
		}
		return getMethod;
	}
	
	/**
	 * 
	 * @param host 要访问的主机
	 * @param proxyIP 代理ip
	 * @param proxyPort 代理端口
	 * @return 
	 * @throws Exception
	 */
	public static int testProxy(String host,String proxyIP,int proxyPort){
		int code = 0;
		try {
			//获取到HttpClient
			HttpClient httpClient =  new HttpClient();
			httpClient.getHostConfiguration().setHost(host);
			//设定超时  5000 毫秒的时间
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
			//设定代理
			httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
			//设定代理 ip 和端口
			httpClient.getHostConfiguration().setProxy(proxyIP, proxyPort);
			//设定代理的用户和密码
			Credentials defaultcreds = new UsernamePasswordCredentials("",  "");
			httpClient.getState().setProxyCredentials(new AuthScope(proxyIP, proxyPort, null), defaultcreds);
			
			//获取GetMethod
			GetMethod method = setGetMethod(host);
			
			if(method != null){
				 code = httpClient.executeMethod(method);
				 //获取请求的数据
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//throw new RuntimeException("-------------"+proxyIP+":"+proxyPort+"\t 无效----------");
			
		}
        return code;
	}
	
	/**
	 * 获取到我们的HttpClient
	 * @param url
	 * @return
	 */
	private static HttpClient getHttpClient(String url){
		HttpClient httpClient =  new HttpClient();
		httpClient.getHostConfiguration().setHost(url);
		//设定超时  5000 毫秒的时间
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(50000);
		return httpClient;
	}
	/**
	 * 设定我们带有代理的HttpClieantProxy
	 * @param url
	 * @param proxyIP
	 * @param proxyPort
	 * @return
	 */
	private static HttpClient setHttpClientProxy(String url,String proxyIP,int proxyPort){
		HttpClient httpClient =   getHttpClient(url);
		//设定代理
		httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
		//设定代理 ip 和端口
		httpClient.getHostConfiguration().setProxy(proxyIP, proxyPort);
		//设定代理的用户和密码
		Credentials defaultcreds = new UsernamePasswordCredentials("",  "");
		httpClient.getState().setProxyCredentials(new AuthScope(proxyIP, proxyPort, null), defaultcreds);
		
		//反悔
		return httpClient;
	}
	
	/**
	 * 发送Get请求
	 * @param url
	 * @return
	 */
	public static String sendGet(String url){
		return sendGet(url,false);
	}
	
	/**
	 * 发送get请求 待遇proxy
	 * @param url
	 * @param isProxy
	 * @return
	 */
	public static String sendGet(String url,boolean isProxy){
		String content = null;
		HttpClient client  = null;
		try {
			if(isProxy){
				//当是代理的时候，获取数据
				//ProxyHttps porxy = ProxyUtils.getRandomPropertisProxy();
				//106.38.194.199:80 // 好用
				//client = setHttpClientProxy(url, porxy.getIp(),Integer.parseInt(porxy.getPort()));
				//System.out.println("-------------------使用代理"+porxy.getIp()+":"+porxy.getPort());
				//209.66.193.186
				client = setHttpClientProxy(url,"121.14.138.56",81);
				
			}else{
				client = getHttpClient(url);
			}
			GetMethod method = HttpClientUtils.setGetMethod(url);
			if(method != null){
				content = dealHtml(client, method);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
		
	}
	/**
	 * 处理网页
	 * @param client
	 * @param method
	 * @return
	 */
	public static String dealHtml(HttpClient client,GetMethod method){
		String content = null;
		try {
			int code  = client.executeMethod(method);
			if(code == 200){
				//当数请求成功
				Header header = method.getResponseHeader("Content-Type");
				if(header != null){
					String applicationType = header.getValue();
					if(applicationType != null){
						//当是网页的情况
						if(applicationType.indexOf("html") != -1){
							
							content = getInput2Str(method.getResponseBodyAsStream());
						}else if(applicationType.indexOf("json") != -1){
							content = getInput2Str(method.getResponseBodyAsStream());
							//当是图片的问题
							//System.out.println(JsonUtils.object2Json(applicationType));
						}
					}
				}
			}else if ((code == HttpStatus.SC_MOVED_TEMPORARILY)
					|| (code == HttpStatus.SC_MOVED_PERMANENTLY)
					|| (code == HttpStatus.SC_SEE_OTHER)
					|| (code == HttpStatus.SC_TEMPORARY_REDIRECT)) {
				System.err.println("------------------------请求失败: " + method.getStatusLine());
				return null;
				//当我们的ip被限制的情况
			}else if(code == HttpStatus.SC_FORBIDDEN){
				
			}
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
	}
	/**
	 * 发送json数据到服务器
	 * @param url
	 * @param json
	 * @return
	 */
	public static String postJson(String url,String json){
		//使用DefaultHttpClient 这个对象才可以获取到Json
		String str = null;
		try {
			DefaultHttpClient client = new DefaultHttpClient();  
			//添加json
			HttpPost post =new HttpPost(url);
			StringEntity entity = new StringEntity(json,ContentType.create("application/json", "utf-8"));
			post.setEntity(entity);
			//返回的数据
			HttpResponse response = client.execute(post);
			int code = response.getStatusLine().getStatusCode();
			if(code >=200 && code <300){
				InputStream  in = response.getEntity().getContent();
				str = HttpClientUtils.getInput2Str(in);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 通过post来提交数据，没有带参数
	 * @param url 请求的地址
	 * @return
	 */
	public static String post(String url){
		return HttpClientUtils.post(url,null);
	}
	/**
	 * 通过post来提交数据，带参数的方法
	 * @param url 请求地址 
	 * @param params 参数
	 * @return
	 */
	public static String post(String url,Map<String,String> params){
		String str  = null;
		try {
			HttpClient client = new  HttpClient();
			PostMethod method = new PostMethod(url);
			//设定请求头的样式
			method.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");  
			if(params != null && params.size() >0){
				for(Map.Entry<String,String> entry:params.entrySet()){
					method.setParameter(entry.getKey(),entry.getValue());
				}
			}
			int code = client.executeMethod(method);
			if(code >=200 && code <300){
				InputStream  in = method.getResponseBodyAsStream();
				str = HttpClientUtils.getInput2Str(in);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
	/**
	 * 将InputStream 转化为 字符串
	 * @param in
	 * @return
	 */
	public static String getInput2Str(InputStream in){
		String str = null;
		try {
			
			//写数据
			String line = null;
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			    
			StringBuffer sb = new StringBuffer();
			while((line = reader.readLine())!= null){
				 sb.append(line);
			}
			str = sb.toString();
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			try {
				if(in != null){
					in.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return str;
	}
}
