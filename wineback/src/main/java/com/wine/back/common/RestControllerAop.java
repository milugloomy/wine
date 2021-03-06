package com.wine.back.common;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect  
@Component  
public class RestControllerAop {  
	private Logger log = LoggerFactory.getLogger(RestControllerAop.class);

	//controller下的所有@RequestMapping的方法
	@Around("execution(* com.wine.back.controller.*.*(..)) and @annotation(org.springframework.web.bind.annotation.RequestMapping)") 
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

		try {
			Object result = pjp.proceed(pjp.getArgs());
			return result;
		} finally{
			long costMs = System.currentTimeMillis() - beginTime;
			log.info("{}请求结束，耗时：{}ms",uri,costMs);
		}
	}
}