package com.wine.base.common;

public class WineException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String code;

	private String message;
	
	public WineException(String code) {
		this.code = code;
	}
	public WineException(String code,String message) {
		this.code = code;
		this.message=message;
	}

	public String getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}

}
