package com.wine.base.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.wine.base.common.WineException;

@Component
public class HttpService {
	private static final Logger log = LoggerFactory.getLogger(HttpService.class);

	//订阅号
	/*public static String appId="wxee167cddcba2af47";
	public static String appSecret="971412dbe7e6f89204675995409c251a";*/
	//测试号
	/*public static String appId="wx158fc360fd937056";
	public static String appSecret="646049a506fd0007b8367a7a9f73ac83";*/
	//生产号
	public static String appId="wx5da1f2074f5077a2";
	public static String appSecret="242179f06e43018689be5475e911fb19";
	
	private HttpClient httpClient;
	private RestTemplate restTemplate;
	
	public HttpService(){
		httpClient=HttpClients.custom().build();
		restTemplate=new RestTemplate();
		//删除旧的StringConverter，添加UTF8的StringConverter
		List<HttpMessageConverter<?>> converterList=restTemplate.getMessageConverters();
        for (int i=0;i<converterList.size();i++) {
            if (converterList.get(i).getClass() == StringHttpMessageConverter.class) {
            	converterList.set(i, new StringHttpMessageConverter(StandardCharsets.UTF_8));
            	break;
            }
        }
	}
	
	public String wxGet(String url){
		String res=restTemplate.getForObject(url, String.class);
		log.info("微信接口：{}",url);
		log.info("微信返回：{}",res);
		return res;
	}
	
	public JSONObject wxGetJson(String url) throws WineException{
		String res=wxGet(url);
		JSONObject jo=JSONObject.parseObject(res);
		if(jo.containsKey("errcode") && !jo.get("errcode").equals(0)){
			throw new WineException(jo.getString("errcode"),(String)jo.get("errmsg"));
		}
		return jo;
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
