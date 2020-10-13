package com.gmagica.warehousemanagement.service;

import java.util.List;

import com.gmagica.warehousemanagement.dto.request.WareHouseDtoRequest;
import com.gmagica.warehousemanagement.dto.response.WareHouseDtoResponse;
import com.gmagica.warehousemanagement.entity.WareHouseEntity;
import com.gmagica.warehousemanagement.enums.WareHouseStatus;

public interface WareHouseService {

	WareHouseDtoResponse registerWareHouse(WareHouseDtoRequest wareHouseDtoRequest);

	WareHouseDtoResponse updateDepotStatus(String depotId, WareHouseStatus depotStatus);

	WareHouseDtoResponse searchWareHouseById(String depotId);

	WareHouseDtoResponse searchWareHouseByLocation(String depotLatitude, String depotLongitude);

	List<WareHouseDtoResponse> searchWareHouseByStatus(WareHouseStatus depotStatus);
	
	List<WareHouseEntity> getWareHouse();

	WareHouseDtoResponse deleteWareHouseById(String depotId);

}
