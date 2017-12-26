package com.wine.back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wine.base.bean.ParamDefault;
import com.wine.base.common.MyResEntity;
import com.wine.base.dao.ParamDefaultMapper;

@RestController
public class ParamController {
	
	@Autowired
	private ParamDefaultMapper paramDefaultMapper;
	@Autowired
	private TransactionTemplate transactionTemplate;
	
	@RequestMapping("/paramDefault")
	public MyResEntity paramDefault(){
		List<ParamDefault> list=paramDefaultMapper.select();
		return new MyResEntity(list);
	}
	
	@RequestMapping("/paramEdit")
	public MyResEntity paramEdit(@RequestBody List<ParamDefault> editList){
		transactionTemplate.execute(new TransactionCallback<Object>(){
			public Object doInTransaction(TransactionStatus status){
				paramDefaultMapper.deleteAll();
				paramDefaultMapper.insertList(editList);
				return null;
			}
		});
		return new MyResEntity();
	}
	
}
