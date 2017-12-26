package com.wine.base.dao;

import java.util.List;

import com.wine.base.bean.Param;

public interface ParamMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Param record);

    int insertSelective(Param record);

    int insertList(List<Param> paramList);

	Param selectByPrimaryKey(Integer id);

	List<Param> selectByProductId(int productId);

	int updateByPrimaryKeySelective(Param record);

    int updateByPrimaryKey(Param record);

}