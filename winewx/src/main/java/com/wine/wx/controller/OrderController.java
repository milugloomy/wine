package com.wine.wx.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wine.base.common.MyResEntity;
import com.wine.base.common.Util;
import com.wine.base.dao.OrderJnlMapper;

@RestController
public class OrderController {
	
	@Autowired
	private OrderJnlMapper orderJnlMapper;
	
	@RequestMapping("/expressList")
	public MyResEntity expressList(HttpSession session){
		int userId=Util.getUserId(session);
		List<Order> list=orderJnlMapper.selectByUserId(userId);
		return new MyResEntity(list);
	}
	
}
