package com.wine.wx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wine.base.bean.Product;
import com.wine.base.common.MyResEntity;
import com.wine.base.common.WineException;
import com.wine.wx.service.ProductService;

@RestController
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping("/productDetail")
	public MyResEntity productDetail(int productId) throws WineException{
		Product product=productService.getDetail(productId);
		return new MyResEntity(product);
	}
	
	/**
	 * @param pageNo 页码
	 * @param sortBy 按照type排序 时间、销量、价格
	 * @param sortType acs升序 desc降序
	 * @param type 红酒rw 白酒ww 全部all
	 */
	@RequestMapping("/productList")
	public MyResEntity productList(@RequestParam(defaultValue="1")int pageNo,
			@RequestParam(defaultValue="sale")String sortBy,
			@RequestParam(defaultValue="desc")String sortType,
			@RequestParam(defaultValue="all")String type){
		
		List<Product> list=productService.getList(pageNo, sortBy, sortType, type);
		return new MyResEntity(list);
	}
	
}
