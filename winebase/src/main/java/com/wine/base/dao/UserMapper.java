package com.wine.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wine.base.bean.User;

public interface UserMapper {
	List<User> selectByPageNo(@Param("offset")Integer offset,@Param("length")Integer length);

	int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

	User selectByOpenid(String openid);

	int selectTotal();

}