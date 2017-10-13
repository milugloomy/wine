package com.wine.back.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wine.back.batch.UserBatch;
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
	@Autowired
	private UserBatch userBatch;
	
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
	
	@RequestMapping("/userSync")
	public MyResEntity userSync(String openid) throws WineException{
		userBatch.updateUser();
		return new MyResEntity();
	}
	
}
