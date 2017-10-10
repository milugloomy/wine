package com.wine.back.batch;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wine.base.bean.User;
import com.wine.base.common.WineException;
import com.wine.base.dao.UserMapper;
import com.wine.base.service.WxService;

import batch.common.MyBatch;

@Service
public class UserBatch {
	private Logger log = LoggerFactory.getLogger(UserBatch.class);
	@Autowired
	private WxService wxService;
	@Autowired
	private UserMapper userMapper;
	
	//每天晚上10点
	@MyBatch(cron = "0 0 22 * * ?")
	public void updateUser() throws WineException{
		log.error("------用户信息同步开始------");
		List<String> openidList=wxService.userList();
		for(String openid:openidList){
			try{
				User userWx=wxService.userDetail(openid);
				User userDb=userMapper.selectByOpenid(openid);
				//微信时间戳要乘以1000
				Date subscribeTime=userWx.getSubscribeTime();
				subscribeTime.setTime(subscribeTime.getTime()*1000);
				userWx.setSubscribeTime(subscribeTime);
				//数据库中不存在则插入
				if(userDb==null){
					userWx.setStatus("N");
					userWx.setRegTime(new Date());
					userMapper.insertSelective(userWx);
 					continue;
				}
				//属性不相同则更新
				if(!fieldEquals(userWx, userDb)){
					userWx.setUserId(userDb.getUserId());
					userMapper.updateByPrimaryKeySelective(userWx);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		log.error("------用户信息同步结束------");
	}
	
	private boolean fieldEquals(User userWx,User userDb){
		if(!userWx.getCity().equals(userDb.getCity()))
			return false;
		if(!userWx.getCountry().equals(userDb.getCountry()))
			return false;
		if(!userWx.getHeadimgurl().equals(userDb.getHeadimgurl()))
			return false;
		if(!userWx.getLanguage().equals(userDb.getLanguage()))
			return false;
		if(!userWx.getNickname().equals(userDb.getNickname()))
			return false;
		if(!userWx.getProvince().equals(userDb.getProvince()))
			return false;
		if(!userWx.getRemark().equals(userDb.getRemark()))
			return false;
		if(!userWx.getSex().equals(userDb.getSex()))
			return false;
		if(!userWx.getSubscribe().equals(userDb.getSubscribe()))
			return false;
		if(!userWx.getSubscribeTime().equals(userDb.getSubscribeTime()))
			return false;
		return true;
	}
}
