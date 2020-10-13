package com.gmagica.warehousemanagement.dto.response;

import java.util.Date;

import com.gmagica.warehousemanagement.enums.WareHouseStatus;

import lombok.Data;

@Data
public class WareHouseDtoResponse {
	private String depotId;
	private String depotName;
	private WareHouseStatus depotStatus;
	private String createdBy;
    private String modifiedBy;
    private Date createdOn;
    private Date modifiedOn;
    private String depotLatitude;
    private String depotLongitude;
    private String depotNumber;
	private String depotAddress;

}
