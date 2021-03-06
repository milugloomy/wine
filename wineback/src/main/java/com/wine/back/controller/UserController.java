package com.wine.back.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wine.base.bean.User;
import com.wine.base.common.MyResEntity;
import com.wine.base.common.Util;
import com.wine.base.common.WineException;
import com.wine.base.dao.UserMapper;
import com.wine.base.service.WxService;

@RestController
public class UserController {
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private WxService wxService;
	

	@RequestMapping("/userList")
	public MyResEntity userList(@RequestParam(defaultValue="1")int pageNo) throws WineException{
		int offset=(pageNo-1)*Util.pageSize;
		int length=Util.pageSize;
		List<User> list=userMapper.selectByPageNo(offset, length);
		return new MyResEntity(list);
	}
	
	@RequestMapping("/userListSize")
	public MyResEntity userListSize() throws WineException{
		int total=userMapper.selectTotal();
		return new MyResEntity(total);
	}
	
	@RequestMapping("/user")
	public MyResEntity user(String openid) throws WineException{
		User user=wxService.userDetail(openid);
		return new MyResEntity(user);
	}
	
	@RequestMapping("/checkSignature")
	public void checkSignature(String signature,String echostr,String timestamp,String nonce,HttpServletResponse response) throws WineException, IOException{
		boolean b=wxService.checkSignature(signature, timestamp, nonce);
		System.out.println(b);
		response.getWriter().print(echostr);
	}
	
}
