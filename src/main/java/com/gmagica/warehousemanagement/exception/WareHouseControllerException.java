package com.gmagica.warehousemanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class WareHouseControllerException extends RuntimeException{
	public WareHouseControllerException(String message) {
		super(message);
	}

}
