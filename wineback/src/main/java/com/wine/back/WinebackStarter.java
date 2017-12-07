package com.wine.back;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.util.ResourceUtils;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.wine.back.common.LoginFilter;

@ComponentScan(basePackages={"com.wine","mybatch"})  
@SpringBootApplication
public class WinebackStarter {
	
	@Autowired
	private Environment env;
	
	Logger log=LoggerFactory.getLogger(WinebackStarter.class);
	public static void main(String[] args) {
		SpringApplication.run(WinebackStarter.class, args);
	}
	
	//登录过滤器
	@Bean
    public FilterRegistrationBean myFilter1() {
    	FilterRegistrationBean f = new FilterRegistrationBean(new LoginFilter());
        f.addUrlPatterns("/*");  
        f.setName("loginFilter");
        return f;  
    }
	//druid过滤器
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean f = new FilterRegistrationBean();
		f.setFilter(new WebStatFilter());
		f.addUrlPatterns("/*");
		f.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
		f.addInitParameter("profileEnable", "true");
		f.addInitParameter("principalCookieName", "USER_COOKIE");
		f.addInitParameter("principalSessionName", "USER_SESSION");
		f.setName("druidFilter");
		return f;
	}
	//druid servlet
	@Bean
	public ServletRegistrationBean druidServlet() {
		ServletRegistrationBean reg = new ServletRegistrationBean();
		reg.setServlet(new StatViewServlet());
		reg.addUrlMappings("/druid/*");
		
		reg.addInitParameter("loginUsername", env.getProperty("druid.username"));
		reg.addInitParameter("loginPassword", env.getProperty("druid.password"));
		return reg;
	}
	
	//初始化bean读取err.properties
    @Bean
    public Properties errConfig() throws IOException {
    	URL u=ResourceUtils.getURL("classpath:err.properties");
    	InputStream stream=u.openStream();
    	Properties pro = new Properties();
    	InputStreamReader isr = null;
		try {
			isr = new InputStreamReader(stream,"UTF-8");
	    	pro.load(isr);
		} finally{
			isr.close();
		}
        return pro;
    }
    
}
