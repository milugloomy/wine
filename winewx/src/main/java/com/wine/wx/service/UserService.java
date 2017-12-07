package com.wine.wx.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wine.base.bean.LoginAcToken;
import com.wine.base.bean.User;
import com.wine.base.common.WineException;
import com.wine.base.dao.UserMapper;
import com.wine.base.service.TokenService;
import com.wine.base.service.WxService;

@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private WxService wxService;
	@Autowired
	private TokenService tokenService;
	
	//关注用户
	public User getFollowUser(String code) throws WineException{
		String openid = wxService.getOpenidByCode(code);
		User user=userMapper.selectByOpenid(openid);
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

	//未关注用户
	public User getUnfollowUser(String code) throws WineException {
		//TODO  code been used
		LoginAcToken tk=tokenService.getLoginAcToken(code);
		
		String openid=tk.getOpenid();
//		String refresh_token=tk.getRefresh_token();
		String access_token=tk.getAccess_token();
		
		User user=wxService.unfollowUserDetail(access_token, openid);
		
		//数据库没有则插入
		User dbUser=userMapper.selectByOpenid(openid);
		if(dbUser==null){
			//未关注
			user.setSubscribe("0");
			user.setStatus("N");
			user.setRegTime(new Date());
			userMapper.insertSelective(user);
		}
		return user;
	}
	
}
