package com.wine.wx.common;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wine.base.bean.User;
import com.wine.base.common.SpringUtil;
import com.wine.base.dao.UserMapper;
import com.wine.base.service.HttpService;

public class LoginFilter implements Filter{
	
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)servletRequest;
		HttpServletResponse response=(HttpServletResponse)servletResponse;
		HttpSession session=request.getSession();

		String uri=request.getRequestURI();
		
		if(needLogin(session, uri)){
			String requestUrl=request.getRequestURL().toString();
			String domain=requestUrl.substring(0,requestUrl.indexOf("/winewx/"));
			String wxUrl="https://open.weixin.qq.com/connect/oauth2/authorize?"
					+ "appid="+HttpService.appId
					+ "&redirect_uri="+URLEncoder.encode(domain+"/winewx/login","utf-8")
					+ "&response_type=code"
//					+ "&scope=snsapi_base"//不需用户同意即可获取用户信息，需用户关注
					+ "&scope=snsapi_userinfo"//未关注可获取用户信息，需用户同意
					+ "&state=STATE"
					+ "#wechat_redirect";
			System.out.println("redirect:"+wxUrl);
			response.sendRedirect(wxUrl);
		}else{
			chain.doFilter(servletRequest, servletResponse);
		}
		
		/*if(request.getSession().getAttribute("user")==null){
			String openid="o3HV8wuWpQTW_ugMf7y7R8JCLcUU";
			UserMapper userMapper=(UserMapper)SpringUtil.getBean("userMapper");
			User user=userMapper.selectByOpenid(openid);
			request.getSession().setAttribute("user", user);
		}
		chain.doFilter(servletRequest, servletResponse);*/
	}
	private boolean needLogin(HttpSession session,String uri){
		//静态资源不拦截
		if(uri.endsWith(".js") || uri.endsWith(".gif") || uri.endsWith(".jpg")
				|| uri.endsWith(".png") || uri.endsWith(".css") || uri.endsWith(".ico")
				|| uri.endsWith(".txt"))
			return false;
		//登录交易
		if(uri.equals("/winewx/login")){
			return false;
		}
		//未关注跳转到推广页
		if(uri.equals("/winewx/promote.html")){
			return false;
		}
		//其他的判断session
		if(session.getAttribute("user")==null)
			return true;
		return false;
	}
	public void destroy() {
		
	}

}
