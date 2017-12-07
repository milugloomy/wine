package com.wine.back.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.wine.back.service.ProductService;
import com.wine.base.bean.Image;
import com.wine.base.bean.Product;
import com.wine.base.bean.ProductForm;
import com.wine.base.common.MyResEntity;
import com.wine.base.common.Util;
import com.wine.base.common.WineException;
import com.wine.base.dao.ProductMapper;

@RestController
public class ProductController {
	
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductMapper productMapper;
	
	@RequestMapping("/productDetail")
	public MyResEntity productDetail(int productId) throws WineException{
		Product product=productService.productDetail(productId);
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
	
	@RequestMapping("/productAdd")
	public MyResEntity productAdd(ProductForm productForm,String imgStr,String detailImgSrc){
		List<Image> imgList=JSON.parseArray(imgStr, Image.class);
		List<Image> detailImgList=JSON.parseArray(detailImgSrc, Image.class);
		Product product=new Product();
		BeanUtils.copyProperties(productForm, product);
		int productId=productService.productAdd(product,imgList,detailImgList);
		return new MyResEntity(productId);
	}
	
	@RequestMapping("/productEdit")
	public MyResEntity productEdit(ProductForm productForm,String imgStr,String detailImgSrc){
		List<Image> imgList=JSON.parseArray(imgStr, Image.class);
		List<Image> detailImgList=JSON.parseArray(detailImgSrc, Image.class);
		Product product=new Product();
		BeanUtils.copyProperties(productForm, product);
		productService.productEdit(product,imgList,detailImgList);
		return new MyResEntity();
	}
	
	@RequestMapping("/productDelete")
	public MyResEntity productDelete(int productId){
		productMapper.deleteByPrimaryKey(productId);
		return new MyResEntity();
	}
	
}
