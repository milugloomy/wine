package com.wine.back.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.wine.base.bean.Image;
import com.wine.base.bean.Param;
import com.wine.base.bean.Product;
import com.wine.base.dao.ImageMapper;
import com.wine.base.dao.ParamMapper;
import com.wine.base.dao.ProductMapper;

/**
 * business中的方法为事务方法
 * @author Administrator
 *
 */

@Service
public class ProductService {
	@Value("${img.path}")
    private String imgPath;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private ImageMapper imageMapper;
	@Autowired
	private ParamMapper paramMapper;
	@Autowired
	private TransactionTemplate transactionTemplate;
	
	public Integer productAdd(Product newProduct, List<Param> paramList, List<Image> imgList, List<Image> detailImgList) {
		newProduct.setAddTime(new Date());
		newProduct.setStatus("N");
		return transactionTemplate.execute(new TransactionCallback<Integer>(){
			public Integer doInTransaction(TransactionStatus status){
				newProduct.setStatus("N");
				newProduct.setAddTime(new Date());
				productMapper.insertSelective(newProduct);
				int productId=newProduct.getProductId();
				//商品详情参数
				insertParams(paramList,productId);
				//存图片
				insertImages(imgList, productId);
				//存详情图片
				insertDetailImages(detailImgList, productId);
				return productId;
			}
		});
	}
	
	public void productEdit(Product product, List<Param> paramList, List<Image> imgList, List<Image> detailImgList) {
		transactionTemplate.execute(new TransactionCallback<Object>(){
			public Object doInTransaction(TransactionStatus status) {
				int productId=product.getProductId();
				//删除旧图片
				imageMapper.deleteByProductId(productId);
				//更新product
				productMapper.updateByPrimaryKeySelective(product);
				//更新商品参数
				updateParams(paramList, productId);
				//插入新图片
				insertImages(imgList, productId);
				//插入详情图片
				insertDetailImages(detailImgList, productId);
				return null;
			}
		});
	}
	
	protected void updateParams(List<Param> paramList, int productId) {
		for(Param param:paramList){
			paramMapper.updateByPrimaryKeySelective(param);
		}
	}
	
	protected void insertParams(List<Param> paramList, int productId) {
		for(Param param:paramList){
			param.setProductId(productId);
		}
		paramMapper.insertList(paramList);
	}

	protected void insertDetailImages(List<Image> detailImgList, int productId) {
		for(int i=0;i<detailImgList.size();i++){
			String imgSrc=detailImgList.get(i).getImgUrl();
			//插入数据库，和productId关联起来
			Image image=new Image();
			image.setMainPic(2);//详情图片，类型为2
			image.setImgUrl(imgSrc);
			image.setProductId(productId);
			imageMapper.insert(image);
		}
	}

	private void insertImages(List<Image> imgList,int productId){
		for(int i=0;i<imgList.size();i++){
			String imgSrc=imgList.get(i).getImgUrl();
			//插入数据库，和productId关联起来
			Image image=new Image();
			//第一张设为主图片
			if(i==0){
				image.setMainPic(1);
			}else{
				image.setMainPic(0);
			}
			image.setImgUrl(imgSrc);
			image.setProductId(productId);
			imageMapper.insert(image);
		}
	}

	public Product productDetail(int productId) {
		Product product=productMapper.selectByPrimaryKey(productId);
		List<Image> detailImgList=new ArrayList<Image>();
		List<Image> imgList=product.getImgList();
		for(int i=0;i<imgList.size();i++){//main_pic 是2 的是参数图片
			Image image=imgList.get(i);
			if(image.getMainPic()==2){
				detailImgList.add(image);
				imgList.remove(i);
				i--;
			}
		}
		product.setDetailImgList(detailImgList);
		//查询上商品详情
		List<Param> paramList=paramMapper.selectByProductId(productId);
		product.setParamList(paramList);
		return product;
	}
	
}
