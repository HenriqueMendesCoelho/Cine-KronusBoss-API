package com.kronusboss.cine.application.spring.handler.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HandlerErrorDto implements Serializable {
	
	private static final long serialVersionUID = 9212459265740475543L;
	
	String key;

	String error;

}
