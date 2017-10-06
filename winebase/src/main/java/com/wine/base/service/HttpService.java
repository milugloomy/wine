package com.wine.base.service;

import java.io.IOException;
import java.text.MessageFormat;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wine.base.common.MyExceptionHandler;
import com.wine.base.common.WineException;

@Component
public class HttpService {
	private static final Logger log = LoggerFactory.getLogger(MyExceptionHandler.class);

	public static String appId="wxee167cddcba2af47";
	public static String appSecret="971412dbe7e6f89204675995409c251a";
	public static String[] whiteIp=new String[]{"58.48.244.252","59.172.234.164","171.113.111.219"};
	
	private String acToken;
	private long acTokenTime;
	private HttpClient httpClient;
	
	public HttpService(){
		httpClient=HttpClients.custom().build();
	}
	
	public String getAcToken() throws WineException{
		if(acTokenTime==0l||acToken==null||
				(System.currentTimeMillis()-acTokenTime)>7200*1000){
			String url=MessageFormat.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}", 
					appId,appSecret);
			String retStr=sendGet(url);
			JSONObject jo=JSON.parseObject(retStr);
			acToken=(String)jo.get("access_token");
			acTokenTime=System.currentTimeMillis();
		}
		return acToken;
	}
	
	public String sendGet(String url) throws WineException{
        try {
    		HttpGet httpGet = new HttpGet(url);  
			HttpResponse response = httpClient.execute(httpGet);
			String out = EntityUtils.toString(response.getEntity());
			log.info("：{}",out);
			return out;
		} catch (IOException e) {
			throw new WineException("wx.api.error");
		}
	}
	
	public String sendPost(String url,String param) throws WineException{
        try {
    		HttpPost httpPost = new HttpPost(url);
    		StringEntity entity = new StringEntity(param,"UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
			HttpResponse response = httpClient.execute(httpPost);
			String out = EntityUtils.toString(response.getEntity());
			log.info("：{}",out);
			return out;
		} catch (IOException e) {
			throw new WineException("wx.api.error");
		}
	}
}
