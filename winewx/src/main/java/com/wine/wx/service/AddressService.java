package com.wine.wx.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.wine.base.bean.Address;
import com.wine.base.dao.AddressMapper;

@Service
public class AddressService {
	@Autowired
	private AddressMapper addressMapper;
	@Autowired
	private TransactionTemplate transactionTemplate;
	
	public Address addressAdd(int userId, Address address) {
		address.setAddTime(new Date());
		address.setStatus("N");
		address.setUserId(userId);
		//事务，先将其他地址更新状态为C，再插入新地址
		return transactionTemplate.execute(new TransactionCallback<Address>(){
			public Address doInTransaction(TransactionStatus status) {
				addressMapper.updateStatusByUserId(userId);
				addressMapper.insertSelective(address);
				return address;
			}
		});
	}
}
