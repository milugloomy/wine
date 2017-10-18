package com.wine.wx.common;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.wine.base.bean.User;
import com.wine.base.dao.UserMapper;

@Aspect  
@Component  
public class RestControllerAop {  
	@Autowired
	private UserMapper userMapper;
	private Logger log = LoggerFactory.getLogger(RestControllerAop.class);

	//controller下的所有@RequestMapping的方法
	@Around("execution(* com.wine.wx.controller.*.*(..)) and @annotation(org.springframework.web.bind.annotation.RequestMapping)") 
	public Object Interceptor(ProceedingJoinPoint pjp) throws Throwable{  
		long beginTime = System.currentTimeMillis();
		
		ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();  
		HttpServletRequest request = sra.getRequest();
		String uri=request.getRequestURI();
		log.info("uri:{}",uri);
		
		Enumeration<String> enu=request.getParameterNames();
		StringBuilder param=new StringBuilder();
		while(enu.hasMoreElements()){
			String paraName=(String)enu.nextElement();
			param.append(paraName+"-"+request.getParameter(paraName)+";");  
		}
		log.info("param:{}",param);
		
		if(request.getSession().getAttribute("user")==null){
			String openid="oEiYVv601cx0w3qFcSTxi-dM9mpg";
			User user=userMapper.selectByOpenid(openid);
			request.getSession().setAttribute("user", user);
		}

		try {
			Object result = pjp.proceed(pjp.getArgs());
			return result;
		} finally{
			long costMs = System.currentTimeMillis() - beginTime;
			log.info("{}请求结束，耗时：{}ms",uri,costMs);
		}
	}
}