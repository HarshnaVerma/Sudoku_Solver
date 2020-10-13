package com.gmagica.warehousemanagement.dto.request;

import com.gmagica.warehousemanagement.enums.WareHouseStatus;

import lombok.Data;

@Data
public class WareHouseDtoRequest {
	private String depotName;
	private String depotLatitude;
	private String depotLongitude;
	private WareHouseStatus depotStatus;
	private String depotNumber;
	private String depotAddress;

}
