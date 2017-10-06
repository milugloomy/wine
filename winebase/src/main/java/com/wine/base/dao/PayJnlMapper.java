package com.wine.base.dao;

import com.wine.base.bean.PayJnl;

public interface PayJnlMapper {
    int deleteByPrimaryKey(Integer pjnlno);

    int insert(PayJnl record);

    int insertSelective(PayJnl record);

    PayJnl selectByPrimaryKey(Integer pjnlno);

    int updateByPrimaryKeySelective(PayJnl record);

    int updateByPrimaryKey(PayJnl record);
}