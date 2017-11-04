package com.wine.back.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wine.back.common.RSAUtil;
import com.wine.base.bean.Manager;
import com.wine.base.bean.User;
import com.wine.base.common.MyResEntity;
import com.wine.base.common.Util;
import com.wine.base.common.WineException;
import com.wine.base.dao.ManagerMapper;
import com.wine.base.dao.UserMapper;
import com.wine.base.service.WxService;

@RestController
public class UserController {
	
	@Autowired
	private ManagerMapper managerMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private WxService wxService;
	
	@RequestMapping("/login")
	public MyResEntity login(@RequestParam("username")String encUsername,
			@RequestParam("password")String encPassword,HttpSession session) throws WineException{
		//解密
		String username=RSAUtil.decrypt(encUsername);
		String password=RSAUtil.decrypt(encPassword);
		
		Manager manager=managerMapper.selectManagerByUsername(username);
		if(manager==null)
			throw new WineException("username.not.exist");
		if(!manager.getPassword().equals(password))
			throw new WineException("password.err");
		session.setAttribute("manager", manager);
		return new MyResEntity();
	}
	
	@RequestMapping("/logout")
	public MyResEntity logout(HttpSession session){
		session.invalidate();
		return new MyResEntity();
	}
	
	@RequestMapping("/manager")
	public MyResEntity manager(HttpSession session){
		return new MyResEntity(session.getAttribute("manager"));
	}

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
