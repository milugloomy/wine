package com.wine.wx.controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wine.base.bean.User;
import com.wine.base.common.MyResEntity;
import com.wine.base.common.Util;
import com.wine.base.common.WineException;
import com.wine.base.service.HttpService;

@RestController
public class UserController {
	
	@Autowired
	private HttpService httpService;
	
	@RequestMapping("/userInfo")
	public MyResEntity userInfo(HttpSession session){
		User user=(User)session.getAttribute("user");
		return new MyResEntity(user);
	}
	
	@RequestMapping(value = "/wxSign")
	public MyResEntity sign(String url) throws WineException{
		long now = System.currentTimeMillis()/1000;//接口要求秒级
		String nonceStr=UUID.randomUUID().toString();
		
		JSONObject signJo = new JSONObject();
		signJo.put("appId", HttpService.appId);
		signJo.put("nonceStr", nonceStr);
		signJo.put("timestamp", now);

		//获取jsapi_ticket
		String jsapiTicket = httpService.getJsapiTicket();
		try {
			// 注意这里参数名必须全部小写，且必须有序
			String str = "jsapi_ticket=" + jsapiTicket 
					+ "&noncestr=" + nonceStr
					+ "&timestamp=" + now 
					+ "&url=" + url;
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.update(str.getBytes("UTF-8"));
			String signature = Util.byteToHex(crypt.digest());
			signJo.put("signature", signature);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new MyResEntity(signJo);
	}
	
}
