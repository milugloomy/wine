package com.wine.back.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.wine.base.bean.Image;
import com.wine.base.bean.Product;
import com.wine.base.common.Util;
import com.wine.base.common.WineException;
import com.wine.base.dao.ImageMapper;
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
	private ImageService imageService;
	@Autowired
	private TransactionTemplate transactionTemplate;
	
	public Integer productAdd(Product newProduct, List<Image> imgList) {
		newProduct.setAddTime(new Date());
		newProduct.setStatus("N");
		return transactionTemplate.execute(new TransactionCallback<Integer>(){
			public Integer doInTransaction(TransactionStatus status){
				newProduct.setStatus("N");
				newProduct.setAddTime(new Date());
				productMapper.insertSelective(newProduct);
				int productId=newProduct.getProductId();
				//存图片
				insertImages(imgList, productId);
				return productId;
			}
		});
	}
	
	public void productEdit(Product product, List<Image> imgList) {
		transactionTemplate.execute(new TransactionCallback<Object>(){
			public Object doInTransaction(TransactionStatus status) {
				int productId=product.getProductId();
				//删除旧图片
				imageMapper.deleteByProductId(productId);
				//更新product
				productMapper.updateByPrimaryKeySelective(product);
				//插入新图片
				insertImages(imgList, productId);
				return null;
			}
		});
	}
	
	private void insertImages(List<Image> imgList,int productId){
		for(int i=0;i<imgList.size();i++){
			String imgSrc=imgList.get(i).getImgUrl();
			//base64数据格式的图片，存为图片
			//src为url形式的图片，直接存url
			if(imgSrc.startsWith("data:image")){
				//图片内容
				String imgContent=imgSrc.substring(imgSrc.indexOf(",")+1);
				//图片路径
				String type=imgSrc.substring(imgSrc.indexOf("/")+1,imgSrc.indexOf(";"));
				String fileName=Util.getTimeStr()+"."+type;
				imgSrc=fileName;
				//存储图片
				try {
					//存原图
					imageService.saveOriginal(imgContent, fileName);
					//存压缩图
					imageService.saveCompressed(fileName);
					//存图标
					imageService.saveIcon(fileName);
				} catch (WineException e) {
					e.printStackTrace();
				}
			}
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
	
}
