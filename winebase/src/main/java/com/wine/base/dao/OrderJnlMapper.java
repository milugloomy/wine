package com.wine.base.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.core.annotation.Order;

import com.wine.base.bean.OrderJnl;

public interface OrderJnlMapper {
    int deleteByPrimaryKey(Integer orderId);

    int insert(OrderJnl record);

    int insertSelective(OrderJnl record);

    OrderJnl selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKeySelective(OrderJnl record);

    int updateByPrimaryKey(OrderJnl record);

	List<OrderJnl> selectByPageNo(@Param("payStatus")String payStatus,
			@Param("startDate")Date startDate,@Param("endDate")Date endDate,
			@Param("offset")int offset,@Param("length")int length);

	int selectTotal(@Param("payStatus")String payStatus,
			@Param("startDate")Date startDate,@Param("endDate")Date endDate);

	int updateState(@Param("orderId")int orderId, @Param("payStatus")String payStatus);

	List<Order> selectByUserId(int userId);
}