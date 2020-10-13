package com.gmagica.warehousemanagement.exception;
import lombok.Data;

@Data
public class PayconError
{
    private int code;

    private String message;

    private String field;

}
