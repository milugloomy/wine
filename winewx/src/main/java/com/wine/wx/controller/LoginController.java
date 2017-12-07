package com.wine.wx.controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wine.base.bean.User;
import com.wine.base.common.WineException;
import com.wine.base.service.HttpService;
import com.wine.wx.service.UserService;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/login")
	public ModelAndView winewxlogin(String code,HttpServletRequest request,HttpServletResponse response) throws WineException{
		User user=userService.getFollowUser(code);
		//用户未关注
		if(user==null){
			String requestUrl=request.getRequestURL().toString();
			String domain=requestUrl.substring(0,requestUrl.indexOf("/winewx/"));
			String wxUrl;
			try {
				wxUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?"
						+ "appid="+HttpService.appId
						+ "&redirect_uri="+URLEncoder.encode(domain+"/winewx/unfollowLogin","utf-8")
						+ "&response_type=code"
//						+ "&scope=snsapi_base"//不需用户同意即可获取用户信息，需用户关注
						+ "&scope=snsapi_userinfo"//未关注可获取用户信息，需用户同意
						+ "&state=STATE"
						+ "#wechat_redirect";
				System.out.println("redirect:"+wxUrl);
				response.sendRedirect(wxUrl);
			} catch (IOException e) {
				throw new WineException(e.getMessage());
			}
			return null;
		//用户已关注
		}else{
			request.getSession().setAttribute("user", user);
			return new ModelAndView("redirect:index.html");
		}
	}
	
	@RequestMapping("/unfollowLogin")
	public ModelAndView unfollowLogin(String code,HttpSession session) throws WineException{
		User user=userService.getUnfollowUser(code);
		session.setAttribute("user", user);
		return new ModelAndView("redirect:index.html");
	}
	
}
