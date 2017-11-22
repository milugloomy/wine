package com.wine.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wine.base.bean.Product;

public interface ProductMapper {
	List<Product> selectByPageNo(@Param("offset")Integer offset,@Param("length")Integer length);
	
	List<Product> selectProductList(@Param("offset")Integer offset,@Param("length")Integer length, 
			@Param("sortBy")String sortBy, @Param("sortType")String sortType, 
			@Param("type")String type);

	Product selectByPrimaryKey(Integer productId);
    
    //假删除，更新状态
    int deleteByPrimaryKey(Integer productId);
    
    int insert(Product record);

    int insertSelective(Product record);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

	int selectTotal();

}