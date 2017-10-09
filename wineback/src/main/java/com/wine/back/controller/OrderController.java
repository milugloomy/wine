package com.wine.back.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wine.base.bean.OrderJnl;
import com.wine.base.common.MyResEntity;
import com.wine.base.common.Util;
import com.wine.base.common.WineException;
import com.wine.base.dao.OrderJnlMapper;

@RestController
public class OrderController {
	
	@Autowired
	private OrderJnlMapper orderJnlMapper;
	
	@RequestMapping("/orderDetail")
	public MyResEntity ordertDetail(int orderId) throws WineException{
		
		OrderJnl orderJnl=orderJnlMapper.selectByPrimaryKey(orderId);
		return new MyResEntity(orderJnl);
	}
	
	@RequestMapping("/orderList")
	public MyResEntity ordertList(String payStatus,@RequestParam(defaultValue="1")int pageNo,
			@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
			@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate){
		int offset=(pageNo-1)*Util.pageSize;
		int length=Util.pageSize;
		//查询全部
		if(payStatus.equals("ALL")){
			payStatus=null;
		}
		//设置结束时间一天后
		endDate.setTime(endDate.getTime()+1000*3600*24);
		List<OrderJnl> list=orderJnlMapper.selectByPageNo(payStatus, startDate, endDate, offset, length);
		return new MyResEntity(list);
	}
	
	@RequestMapping("/orderListSize")
	public MyResEntity ordertListSize(String payStatus,
			@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
			@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate){
		//查询全部
		if(payStatus.equals("ALL")){
			payStatus=null;
		}
		//设置结束时间一天后
		endDate.setTime(endDate.getTime()+1000*3600*24);
		int total=orderJnlMapper.selectTotal(payStatus, startDate, endDate);
		return new MyResEntity(total);
	}
	
	@RequestMapping("/orderChangeState")
	public MyResEntity orderChangeState(int orderId,String payStatus){
		orderJnlMapper.updateState(orderId, payStatus);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:MM");
		if(payStatus.equals("AS")){
			return new MyResEntity(sdf.format(new Date()));
		}else{
			return new MyResEntity();
		}
	}
	
}
