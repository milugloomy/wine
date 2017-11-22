package com.wine.wx.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wine.base.bean.User;
import com.wine.base.common.WineException;
import com.wine.wx.service.UserService;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/login")
	public ModelAndView winewxlogin(String code,HttpSession session) throws WineException{
		User user=userService.getFollowUser(code);
		//用户未关注
		if(user==null){
			user=userService.getUnfollowUser(code);
		}
		session.setAttribute("user", user);
		return new ModelAndView("redirect:index.html");
	}
	
}
