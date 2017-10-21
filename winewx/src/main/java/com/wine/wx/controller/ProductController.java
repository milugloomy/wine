package com.wine.wx.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wine.base.bean.Product;
import com.wine.base.common.MyResEntity;
import com.wine.base.common.Util;
import com.wine.base.common.WineException;
import com.wine.wx.cache.ProductCache;

@RestController
public class ProductController {
	
	@Autowired
	private ProductCache productCache;
	
	@RequestMapping("/productDetail")
	public MyResEntity productDetail(int productId) throws WineException{
		List<Product> baseList=productCache.getSaleDescList();
		for(Product product:baseList){
			if(product.getProductId()==productId){
				return new MyResEntity(product);
			}
		}
		return new MyResEntity();
	}
	
	/**
	 * @param pageNo 页码
	 * @param type 按照type排序 时间、销量、价格
	 * @param turn acs升序 desc降序
	 */
	@RequestMapping("/productList")
	public MyResEntity productList(@RequestParam(defaultValue="1")int pageNo,
			@RequestParam(defaultValue="sale")String sortBy,
			@RequestParam(defaultValue="desc")String sortType,
			@RequestParam(defaultValue="all")String type){
		
		List<Product> baseList = null;
		//时间
		if(sortBy.equals("time")){
			if("desc".equals(sortType)){
				baseList=productCache.getTimeDescList();
			}else{
				baseList=productCache.getTimeList();
			}
		//金额
		}else if(sortBy.equals("price")){
			if("desc".equals(sortType)){
				baseList=productCache.getPriceDescList();
			}else{
				baseList=productCache.getPriceList();
			}
		//销量
		}else{
			if("desc".equals(sortType)){
				baseList=productCache.getSaleDescList();
			}else{
				baseList=productCache.getSaleList();
			}
		}
		
		//酒类型
		List<Product> typeList=new ArrayList<Product>();
		if(type.equals("ww") || type.equals("rw")){
			for(Product product:baseList){
				if(product.getProductType().equals(type)){
					typeList.add(product);
				}
			}
		}else{
			typeList=baseList;
		}
		
		//分页
		List<Product> retList=new ArrayList<Product>();
		int fromIndex=(pageNo-1)*Util.pageSize;
		int toIndex=pageNo*Util.pageSize;
		if(fromIndex<typeList.size()){
			if(toIndex>typeList.size()){
				toIndex=typeList.size();
			}
			retList=typeList.subList(fromIndex, toIndex);
		}
		return new MyResEntity(retList);
	}
	
}
