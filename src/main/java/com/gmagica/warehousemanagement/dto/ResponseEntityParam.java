package com.gmagica.warehousemanagement.dto;

import lombok.Data;

@Data
public class ResponseEntityParam {
    private Integer code;

    private String message;

    private String field;
}
