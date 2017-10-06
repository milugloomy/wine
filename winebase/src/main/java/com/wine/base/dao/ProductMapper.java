package com.wine.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wine.base.bean.Product;

public interface ProductMapper {
	List<Product> selectByPageNo(@Param("offset")Integer offset,@Param("length")Integer length);
	
    Product selectByPrimaryKey(Integer productId);
    
    //假删除，更新状态
    int deleteByPrimaryKey(Integer productId);

    int insert(Product record);

    int insertSelective(Product record);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

	int selectTotal();
}