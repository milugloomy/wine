package com.wine.base.service;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wine.base.bean.LoginAcToken;
import com.wine.base.common.WineException;

@Service
public class TokenService {

	@Autowired
	private HttpService httpService;
	
	@Cacheable(value = "wxToken")
	public String getAcToken() throws WineException{
		String url=MessageFormat.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}", 
				HttpService.appId,HttpService.appSecret);
		JSONObject jo=httpService.wxGetJson(url);
		String acToken=(String)jo.get("access_token");
		return acToken;
	}
	
	@Cacheable(value = "wxToken")
	public String getJsapiTicket() throws WineException{
			String url="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="
					+ getAcToken()+"&type=jsapi";
			JSONObject jo=httpService.wxGetJson(url);
			String jsapiTicket=(String)jo.get("ticket");
		return jsapiTicket;
	}
	
	public LoginAcToken getLoginAcToken(String code) throws WineException{
		String acUrl="https://api.weixin.qq.com/sns/oauth2/access_token?"
				+ "appid={0}&secret={1}&code={2}&grant_type=authorization_code";
		acUrl=MessageFormat.format(acUrl, 
				HttpService.appId,HttpService.appSecret,code);
		String ret=httpService.wxGet(acUrl);
		LoginAcToken t=JSON.parseObject(ret,LoginAcToken.class);
		return t;
	}
}
