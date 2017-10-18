package com.wine.wx.controller;

import java.util.ArrayList;
import java.util.Collections;
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
		List<Product> baseList=productCache.getBaseList();
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
		
		//排序依据
		List<Product> baseList = null;
		switch(sortBy){
		case "time":
			baseList=productCache.getTimeList();
			break;
		case "sale":
			baseList=productCache.getSaleList();
			break;
		case "price":
			baseList=productCache.getPriceList();
			break;
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
			for(Product product:baseList){
				typeList.add(product);
			}
//			typeList=baseList;
		}
		//排序、分页
		List<Product> retList=new ArrayList<Product>();
		//升序
		if(sortType.equals("asc")){
			int fromIndex=(pageNo-1)*Util.pageSize;
			int toIndex=pageNo*Util.pageSize;
			if(fromIndex<typeList.size()){
				if(toIndex>typeList.size()){
					toIndex=typeList.size();
				}
				retList=typeList.subList(fromIndex, toIndex);
			}
		//降序
		}else{
			int fromIndex=typeList.size()-pageNo*Util.pageSize;
			int toIndex=typeList.size()-(pageNo-1)*Util.pageSize;
			if(toIndex>0){
				if(fromIndex<0){
					fromIndex=0;
				}
				retList=typeList.subList(fromIndex, toIndex);
				Collections.reverse(retList);
			}
		}
		return new MyResEntity(retList);
	}
	
}
