package com.wine.base.dao;

import com.wine.base.bean.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userseq);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userseq);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}