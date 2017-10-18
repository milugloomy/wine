package com.wine.base.bean;

public class OrderJnlForm {
    private Integer orderId;

    private String payStatus;

    private String deliComp;
    
    private String deliNo;

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

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

}