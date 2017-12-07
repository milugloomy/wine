package com.wine.base.dao;

import org.apache.ibatis.annotations.Param;

import com.wine.base.bean.Manager;

public interface ManagerMapper {

    int insert(Manager record);

	Manager selectManagerByUsername(String username);

	void updatePassword(@Param("managerId")int managerId, @Param("pwd")String pwd);

	Manager selectByPrimaryKey(int id);

}