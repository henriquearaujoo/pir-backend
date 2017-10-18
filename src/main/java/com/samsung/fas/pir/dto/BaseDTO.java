package com.samsung.fas.pir.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * WebAPi Response Class
 * Any response generated must use this container class
 */
public class BaseDTO {
	@JsonProperty("code")
	private		BaseDTO.Code	code;
	@JsonProperty("data")
	private		Object			data;
	
	public enum Code {
		SUCCESS,
		ERROR,
		LOGIN_SUCCESS,
		LOGIN_FAILURE,
	}

	public BaseDTO.Code getCode() {
		return code;
	}

	public Object getData() {
		return data;
	}

	public void setCode(BaseDTO.Code code) {
		this.code = code;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
