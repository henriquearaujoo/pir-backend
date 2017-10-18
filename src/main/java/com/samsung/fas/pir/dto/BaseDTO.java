package com.samsung.fas.pir.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * WebAPi Response Class
 * Any response generated will use this class
 */
public class BaseDTO {
	@JsonProperty("code")
	private		BaseDTO.Code	code;
	@JsonProperty("data")
	private		Object			data;
	
	public enum Code {
		SUCCESS,
		FAILURE,
		ERROR
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
