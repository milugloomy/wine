package com.wine.base.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wine.base.bean.User;
import com.wine.base.common.Util;
import com.wine.base.common.WineException;

@Service
public class WxService {

	private String token="token_wine";
	@Autowired
	private HttpService httpService;
	
	public List<String> userList() throws WineException{
		String url="https://api.weixin.qq.com/cgi-bin/user/get?access_token="+httpService.getAcToken();
		String ret=httpService.wxGet(url);
		JSONObject jo=JSON.parseObject(ret);
		JSONArray ja=(JSONArray)((JSONObject)jo.get("data")).get("openid");
		
		List<String> openidList=new ArrayList<String>();
		for(int i=0;i<ja.size();i++){
			openidList.add(ja.get(i).toString());
		}
		return openidList;
	}
	
	public User userDetail(String openid) throws WineException{
		String url="https://api.weixin.qq.com/cgi-bin/user/info?access_token="+httpService.getAcToken()+"&openid="+openid;
		String ret=httpService.wxGet(url);
		User user=JSON.parseObject(ret,User.class);
		return user;
	}
	
	public void createMenu() throws WineException{
		String url="https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+httpService.getAcToken();
		JSONObject jo=new JSONObject();
		JSONArray menus=new JSONArray();
		//第一个菜单
		JSONObject menu1=new JSONObject();
		menu1.put("type", "view");
		menu1.put("url", "http://miluglomy.iask.in/wine/wx/actk");
		menu1.put("name", "花道商城11");
		//第二个菜单
		JSONObject menu2=new JSONObject();
		menu2.put("type", "view");
		menu2.put("url", "http://miluglomy.iask.in/wine/wx/actk");
		menu2.put("name", "花道商城22");
		menus.add(menu1);
		menus.add(menu2);
		jo.put("button", menus);
		String ret=httpService.sendPost(url,jo.toJSONString());
		System.out.println(ret);
	}
	
	public boolean checkSignature(String signature, String timestamp, String nonce) {
		// 对token、timestamp和nonce按字典排序
		String[] paramArr = new String[] { token, timestamp, nonce };
		Arrays.sort(paramArr);
		// 将排序后的结果拼接成一个字符串
		String content = paramArr[0].concat(paramArr[1]).concat(paramArr[2]);
		String ciphertext = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			// 对接后的字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes());
			ciphertext = Util.byte2Str(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// 将sha1加密后的字符串与signature进行对比
		return ciphertext != null ? ciphertext.equals(signature.toUpperCase()) : false;
	}
	
}
