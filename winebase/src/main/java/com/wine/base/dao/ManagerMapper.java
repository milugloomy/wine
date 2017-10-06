package com.wine.base.dao;

import com.wine.base.bean.Manager;

public interface ManagerMapper {

    int insert(Manager record);

	Manager selectManagerByUsername(String username);

}