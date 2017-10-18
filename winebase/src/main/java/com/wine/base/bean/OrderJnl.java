package com.wine.base.bean;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class OrderJnl {
    private Integer orderId;

    private Double amount;

    private User user;
    
    private String nickname;
    
    private Product product;
    
    private String payStatus;

    @JSONField (format="yyyy-MM-dd hh:MM")
    private Date payTime;

    @JSONField (format="yyyy-MM-dd hh:MM")
    private Date sendTime;
    
    private Address address;
    
    private String deliComp;
    
    private String deliNo;
    
    private Integer userId;
    
    private Integer productId;
    
    private Integer addrId;

	public String getDeliComp() {
		return deliComp;
	}

	public void setDeliComp(String deliComp) {
		this.deliComp = deliComp;
	}

	public String getDeliNo() {
		return deliNo;
	}

	public void setDeliNo(String deliNo) {
		this.deliNo = deliNo;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getAddrId() {
		return addrId;
	}

	public void setAddrId(Integer addrId) {
		this.addrId = addrId;
	}

}