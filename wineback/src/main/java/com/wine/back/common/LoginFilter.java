package com.wine.back.common;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;

public class LoginFilter implements Filter{

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)servletRequest;
		HttpServletResponse response=(HttpServletResponse)servletResponse;
		HttpSession session=request.getSession();

		String uri=request.getRequestURI();

		//访问登录页判断跳转到service标签的url页
		if(uri.equals("/wineback/login.html")){
			if(session.getAttribute("manager")!=null){
				String redirect=request.getParameter("service");
				redirect=redirect==null?"/wineback/index.html":redirect;
				response.sendRedirect(redirect);
			}else{
				chain.doFilter(servletRequest, servletResponse);
			}
			return;
		}

		String service=uri;
		if(request.getQueryString()!=null)
			service+="?"+request.getQueryString();

		if(needLogin(session, uri)){
			//ajax请求
			if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
				response.setContentType("text/html;charset=UTF-8");
				JSONObject jo=new JSONObject();
				jo.put("retcode", "302");
				jo.put("body", "/wineback/login.html");
				PrintWriter pw=response.getWriter();
				pw.write(jo.toJSONString());
				pw.flush();
				pw.close();
				//页面请求
			}else{
				response.sendRedirect("/wineback/login.html?service="+service);
			}
		}else{
			chain.doFilter(servletRequest, servletResponse);
		}
	}
	private boolean needLogin(HttpSession session,String uri){
		//静态资源不拦截
		if(uri.endsWith(".js") || uri.endsWith(".gif") || uri.endsWith("jpg")
				|| uri.endsWith(".png") || uri.endsWith(".css") || uri.endsWith("ico") )
			return false;
		//登录交易
		if(uri.equals("/wineback/login")){
			return false;
		}
		if(uri.equals("/wineback/checkSignature")){
			return false;
		}
		//其他的判断session
		if(session.getAttribute("manager")==null)
			return true;
		return false;
	}
	public void destroy() {}

}
