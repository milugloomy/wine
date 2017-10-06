package com.wine.base.dao;

import com.wine.base.bean.Jnl;

public interface JnlMapper {
    int deleteByPrimaryKey(Integer jnlno);

    int insert(Jnl record);

    int insertSelective(Jnl record);

    Jnl selectByPrimaryKey(Integer jnlno);

    int updateByPrimaryKeySelective(Jnl record);

    int updateByPrimaryKey(Jnl record);
}