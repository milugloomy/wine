package com.wine.wx.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wine.base.bean.Product;
import com.wine.base.dao.ProductMapper;

@Service
public class ProductCache extends AutoDataRefresh{

	private List<Product> baseList;
	//按价格排序商品list
	private List<Product> priceList;
	private List<Product> priceDescList;
	//按销量排序商品list
	private List<Product> saleList;
	private List<Product> saleDescList;
	//按时间排序商品list
	private List<Product> timeList;
	private List<Product> timeDescList;
	
	@Autowired
	private ProductMapper productMapper;
	
	@Override
	public void setPeriod() {
		period=30*60*1000l;//默认30分钟
	}
	@Override
	public void updateCache() {
		baseList=productMapper.selectAllProduct();
		priceList=new ArrayList<Product>();
		priceDescList=new ArrayList<Product>();
		saleList=new ArrayList<Product>();
		saleDescList=new ArrayList<Product>();
		timeList=new ArrayList<Product>();
		timeDescList=new ArrayList<Product>();
		//复制
		baseList.forEach(product->{
			priceList.add(product);
			saleList.add(product);
			timeList.add(product);
		});
		//价格升序
		for(int i=0;i<priceList.size();i++){
			for(int j=i+1;j<priceList.size();j++){
				if(priceList.get(i).getVipPrice()>priceList.get(j).getVipPrice()){
					Collections.swap(priceList, i, j);
				}
			}
		}
		//价格降序
		for(int i=priceList.size()-1;i>=0;i--){
			priceDescList.add(priceList.get(i));
		}
		//销量升序
		for(int i=0;i<saleList.size();i++){
			for(int j=i+1;j<saleList.size();j++){
				Product productI=saleList.get(i);
				Product productJ=saleList.get(j);
				if((productI.getTotalQuantity()-productI.getRemain())>
						(productJ.getTotalQuantity()-productJ.getRemain())){
					Collections.swap(saleList, i, j);
				}
			}
		}
		//销量降序
		for(int i=saleList.size()-1;i>=0;i--){
			saleDescList.add(saleList.get(i));
		}
		//时间升序
		for(int i=0;i<timeList.size();i++){
			for(int j=i+1;j<timeList.size();j++){
				if(timeList.get(i).getAddTime().after(timeList.get(j).getAddTime())){
					Collections.swap(timeList, i, j);
				}
			}
		}
		//时间降序
		for(int i=timeList.size()-1;i>=0;i--){
			timeDescList.add(timeList.get(i));
		}
	}
	public List<Product> getPriceList() {
		try{
			lockCache();
			return priceList;
		}finally{
			unlockCache();
		}
	}
	public List<Product> getPriceDescList() {
		try{
			lockCache();
			return priceDescList;
		}finally{
			unlockCache();
		}
	}
	public List<Product> getSaleList() {
		try{
			lockCache();
			return saleList;
		}finally{
			unlockCache();
		}
	}
	public List<Product> getSaleDescList() {
		try{
			lockCache();
			return saleDescList;
		}finally{
			unlockCache();
		}
	}
	public List<Product> getTimeList() {
		try{
			lockCache();
			return timeList;
		}finally{
			unlockCache();
		}
	}
	public List<Product> getTimeDescList() {
		try{
			lockCache();
			return timeDescList;
		}finally{
			unlockCache();
		}
	}
}
