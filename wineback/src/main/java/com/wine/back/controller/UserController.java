package com.wine.back.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wine.base.bean.Manager;
import com.wine.base.common.MyResEntity;
import com.wine.base.common.WineException;
import com.wine.base.dao.ManagerMapper;

@RestController
public class UserController {
	
	@Autowired
	private ManagerMapper managerMapper;
	
	@RequestMapping("/login")
	public MyResEntity login(String username,String password,HttpSession session) throws WineException{
		Manager manager=managerMapper.selectManagerByUsername(username);
		if(manager==null)
			throw new WineException("username.not.exist");
		if(!manager.getPassword().equals(password))
			throw new WineException("password.err");
		session.setAttribute("manager", manager);
		return new MyResEntity();
	}
	
	@RequestMapping("/isLogin")
	public MyResEntity isLogin(HttpSession session){
		return new MyResEntity(session.getAttribute("manager")!=null);
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

	
}
