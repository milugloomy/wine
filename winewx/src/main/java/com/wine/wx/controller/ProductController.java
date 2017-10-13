package com.wine.wx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wine.base.bean.Product;
import com.wine.base.common.MyResEntity;
import com.wine.base.common.Util;
import com.wine.base.common.WineException;
import com.wine.base.dao.ProductMapper;

@RestController
public class ProductController {
	
	@Autowired
	private ProductMapper productMapper;
	
	@RequestMapping("/productDetail")
	public MyResEntity productDetail(int productId) throws WineException{
		Product product=productMapper.selectByPrimaryKey(productId);
		return new MyResEntity(product);
	}
	
	@RequestMapping("/productList")
	public MyResEntity productList(@RequestParam(defaultValue="1")int pageNo){
		int offset=(pageNo-1)*Util.pageSize;
		int length=Util.pageSize;
		List<Product> list=productMapper.selectByPageNo(offset, length);
		return new MyResEntity(list);
	}
	
	@RequestMapping("/productListSize")
	public MyResEntity productListSize(){
		int total=productMapper.selectTotal();
		return new MyResEntity(total);
	}
	
}
