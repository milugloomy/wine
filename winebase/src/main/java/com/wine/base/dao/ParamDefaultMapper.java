package com.wine.base.dao;

import java.util.List;

import com.wine.base.bean.ParamDefault;

public interface ParamDefaultMapper {
    int deleteByPrimaryKey(Integer paramId);

    int insert(ParamDefault record);

    int insertSelective(ParamDefault record);

	int insertList(List<ParamDefault> editList);

	ParamDefault selectByPrimaryKey(Integer paramId);
    
    List<ParamDefault> select();

    int updateByPrimaryKeySelective(ParamDefault record);

    int updateByPrimaryKey(ParamDefault record);

	int deleteAll();

}