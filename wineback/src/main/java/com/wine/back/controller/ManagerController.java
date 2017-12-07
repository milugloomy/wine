package com.wine.back.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wine.back.common.RSAUtil;
import com.wine.base.bean.Manager;
import com.wine.base.common.MyResEntity;
import com.wine.base.common.WineException;
import com.wine.base.dao.ManagerMapper;

@Controller
public class ManagerController {
	
	@Autowired
	private ManagerMapper managerMapper;

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
	
	@RequestMapping("/modifyPwd")
	public MyResEntity modifyPwd(String encOldPwd,String encPwd,HttpSession session) throws WineException, IOException{
		int managerId=((Manager)session.getAttribute("manager")).getId();
		//解密
		String oldPwd=RSAUtil.decrypt(encOldPwd);
		Manager manager=managerMapper.selectByPrimaryKey(managerId);
		if(!manager.getPassword().equals(oldPwd)){
			throw new WineException("old.password.err");
		}
		String pwd=RSAUtil.decrypt(encPwd);
		managerMapper.updatePassword(managerId,pwd);
		return new MyResEntity();
	}
}
