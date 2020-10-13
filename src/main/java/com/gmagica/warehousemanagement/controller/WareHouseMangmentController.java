package com.gmagica.warehousemanagement.controller;

import java.net.HttpURLConnection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gmagica.warehousemanagement.dto.request.WareHouseDtoRequest;
import com.gmagica.warehousemanagement.dto.response.WareHouseDtoResponse;
import com.gmagica.warehousemanagement.entity.WareHouseEntity;
import com.gmagica.warehousemanagement.enums.WareHouseStatus;
import com.gmagica.warehousemanagement.exception.ResourceNotFoundException;
import com.gmagica.warehousemanagement.repository.WareHouseRepository;
import com.gmagica.warehousemanagement.service.WareHouseService;
import com.gmagica.warehousemanagement.service.WareHouseServiceImpl;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/v1/resources")
@CrossOrigin
public class WareHouseMangmentController {

    WareHouseService wareHouseService;

    @Autowired
    public WareHouseMangmentController(WareHouseServiceImpl wareHouseService, WareHouseRepository wareHouseRepository) {
        this.wareHouseService = wareHouseService;
    }

    private static final String SUPPORTED_STATUS_TYPES = "\n<b>Supported WareHouse Status :</b>\n" + "ACTIVE, INACTIVE, HOLD";

    @ApiOperation(nickname = "warehouse", notes = "warehouse Status:" + SUPPORTED_STATUS_TYPES, value = "This is the API for regitser the warehouse")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = WareHouseDtoResponse.class)})
    @PostMapping(value = "/warehouse")
    public WareHouseDtoResponse registerWarehouse(@Valid @RequestBody WareHouseDtoRequest wareHouseRequest) {
        return wareHouseService.registerWareHouse(wareHouseRequest);
    }

    @ApiOperation(nickname = "updateWareHouseStatus", notes = "Update the wareHouse Status", value = "This is the API for update the warehouse status", httpMethod = "PATCH", response = WareHouseDtoResponse.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = WareHouseDtoResponse.class)})
    @PatchMapping(path = "/update-warehouse/{depotId}/{depotStatus}", produces = "application/json")
    public WareHouseDtoResponse updateWareHouseStatus(@Valid @PathVariable String depotId, @Valid @PathVariable WareHouseStatus depotStatus) {
        return wareHouseService.updateDepotStatus(depotId, depotStatus);
    }

    @ApiOperation(nickname = "getWareHouses", notes = "Lists of all WareHouses", value = "This is the API for get all the wareHouses", httpMethod = "GET", response = WareHouseEntity.class)
    @ApiResponses({@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Success", response = WareHouseEntity.class)})
    @GetMapping(path = "/warehouse")
    public List<WareHouseEntity> getWareHouses() {
    	return wareHouseService.getWareHouse();
    }

	@ApiOperation(nickname = "searchWareHouseById", notes = "search WareHouse by DepotId", value = "This is the API for search warehouse by depotId", httpMethod = "GET", response = WareHouseDtoResponse.class)
	@ApiResponses({
			@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Success", response = WareHouseDtoResponse.class) })
	@GetMapping(path = "/warehouse/depoid/{depotId}", produces = "application/json")
	public WareHouseDtoResponse searchWareHouseById(@Valid @PathVariable String depotId)
	throws ResourceNotFoundException {
		return wareHouseService.searchWareHouseById(depotId)
		.orElseThrow(() -> new ResourceNotFoundException("Warehouse not found for this id :: " + depotId));
        }
	
    
	

	@ApiOperation(nickname = "searchWareHouseByLocation", notes = "search WareHouse by DepoLocations", value = "This is the API for serach warehouse by DepoLocation", httpMethod = "GET", response = WareHouseDtoResponse.class)
	@ApiResponses({
			@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Success", response = WareHouseDtoResponse.class) })
	@GetMapping(path = "/warehouse/depolocations/{depotLatitude}/{depotLongitude}", produces = "application/json")
	public WareHouseDtoResponse searchWareHouseByLocation(@Valid @PathVariable String depotLatitude, @Valid @PathVariable String depotLongitude) {
		return wareHouseService.searchWareHouseByLocation(depotLatitude, depotLongitude);
	}


	@ApiOperation(nickname = "searchWareHouseByStatus", notes = "search WareHouse by DepoStatus", value = "This is the API for serach warehouse by DepoStatus", httpMethod = "GET", response = WareHouseDtoResponse.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "Success", response = WareHouseDtoResponse.class) })
	@GetMapping(path = "/warehouse/depostatus/{depotStatus}", produces = "application/json")
	public List<WareHouseDtoResponse> searchWareHouseByStatus(@Valid @PathVariable WareHouseStatus depotStatus) {
		return wareHouseService.searchWareHouseByStatus(depotStatus);
	}
	
	@ApiOperation(nickname = "DeleteWareHouseById", notes = "Delete WareHouse by depotId", value = "This is the API for delete warehouse by depotId", httpMethod = "DELETE", response = WareHouseDtoResponse.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "Success", response = WareHouseDtoResponse.class) })
	@DeleteMapping(path = "/warehouse/depotId/{depotId}", produces = "application/json")
	public WareHouseDtoResponse deleteWareHouseById(@Valid @PathVariable String depotId) {
		return wareHouseService.deleteWareHouseById(depotId);
	}
}
