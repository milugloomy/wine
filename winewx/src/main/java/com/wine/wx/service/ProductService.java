package com.wine.wx.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.wine.base.bean.Product;
import com.wine.base.common.Util;
import com.wine.base.dao.ProductMapper;

@Service
public class ProductService{
	
	private static final Logger log = LoggerFactory.getLogger(ProductService.class);
	@Autowired
	private ProductMapper productMapper;
//	@Autowired
//	private CacheManager cacheManager;
	
	@Cacheable(value = "product",key="#pageNo+'_'+#sortBy+'_'+#sortType+'_'+#type")
	public List<Product> getList(int pageNo,String sortBy,String sortType,String type){
		int offset=(pageNo-1)*Util.pageSize;
		int length=Util.pageSize;
		List<Product> list=productMapper.selectProductList(offset,length,
				sortBy,sortType,type);
		log.info("-----从数据库里查询产品列表-----");
		return list;
	}

	@Cacheable(value = "product",key="#productId")
	public Product getDetail(int productId) {
		Product product=productMapper.selectByPrimaryKey(productId);
		return product;
	}
	
}
