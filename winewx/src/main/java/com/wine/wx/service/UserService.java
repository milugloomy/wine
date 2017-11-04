package com.wine.wx.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wine.base.bean.User;
import com.wine.base.common.WineException;
import com.wine.base.dao.UserMapper;
import com.wine.base.service.WxService;

@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private WxService wxService;
	
	public User getUser(String code) throws WineException{
		User user=null;
		String openid = wxService.getOpenidByCode(code);
		user=userMapper.selectByOpenid(openid);
		if(user==null){
			user=wxService.userDetail(openid);
			//未关注
			if(user.getSubscribe().equals("0")){
				return null;
			}
			//微信时间戳要乘以1000
			Date subscribeTime=user.getSubscribeTime();
			subscribeTime.setTime(subscribeTime.getTime()*1000);
			user.setSubscribeTime(subscribeTime);
			user.setStatus("N");
			user.setRegTime(new Date());
			//用户插入数据库
			userMapper.insertSelective(user);
		}
		return user;
	}
}
