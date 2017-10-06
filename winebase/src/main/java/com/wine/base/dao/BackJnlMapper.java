package com.wine.base.dao;

import com.wine.base.bean.BackJnl;

public interface BackJnlMapper {
    int deleteByPrimaryKey(Integer bjnlno);

    int insert(BackJnl record);

    int insertSelective(BackJnl record);

    BackJnl selectByPrimaryKey(Integer bjnlno);

    int updateByPrimaryKeySelective(BackJnl record);

    int updateByPrimaryKey(BackJnl record);
}