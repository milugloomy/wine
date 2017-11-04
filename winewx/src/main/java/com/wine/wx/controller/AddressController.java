package com.wine.wx.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wine.base.bean.Address;
import com.wine.base.bean.AddressForm;
import com.wine.base.common.MyResEntity;
import com.wine.base.common.Util;
import com.wine.base.dao.AddressMapper;
import com.wine.wx.service.AddressService;

@RestController
public class AddressController {
	
	@Autowired
	private AddressMapper addressMapper;
	@Autowired
	private AddressService addressService;
	
	@RequestMapping("/address")
	public MyResEntity address(HttpSession session){
		int userId=Util.getUserId(session);
		Address address=addressMapper.selectByUserId(userId);
		return new MyResEntity(address);
	}
	
	@RequestMapping("/addAddress")
	public MyResEntity addAddress(HttpSession session,AddressForm addressForm){
		int userId=Util.getUserId(session);
		Address address=new Address();
		BeanUtils.copyProperties(addressForm, address);
		address=addressService.addressAdd(userId, address);
		return new MyResEntity(address);
	}
	
}
