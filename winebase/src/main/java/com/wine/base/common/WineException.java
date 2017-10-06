package com.wine.base.common;

public class WineException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String code;
	
	public WineException(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
