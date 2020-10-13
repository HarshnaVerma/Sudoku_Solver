package com.gmagica.warehousemanagement.service;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gmagica.warehousemanagement.Constants;
import com.gmagica.warehousemanagement.dto.request.WareHouseDtoRequest;
import com.gmagica.warehousemanagement.dto.response.WareHouseDtoResponse;
import com.gmagica.warehousemanagement.entity.WareHouseEntity;
import com.gmagica.warehousemanagement.enums.WareHouseStatus;
import com.gmagica.warehousemanagement.exception.RecordNotFoundException;
import com.gmagica.warehousemanagement.exception.WareHouseControllerException;
import com.gmagica.warehousemanagement.repository.WareHouseRepository;


@Service
public class WareHouseServiceImpl implements WareHouseService{
	
	private WareHouseRepository wareHouseRepository;
	
	@Autowired
    public WareHouseServiceImpl(WareHouseRepository wareHouseRepository) {
        this.wareHouseRepository = wareHouseRepository;
    }
	
	@Override
	public WareHouseDtoResponse registerWareHouse(WareHouseDtoRequest wareHouseDtoRequest) {
		UUID depotId = UUID.randomUUID();
		WareHouseEntity wareHouse = new WareHouseEntity();
		wareHouse.setDepotId(depotId.toString());
		wareHouse.setDepotName(wareHouseDtoRequest.getDepotName());
		wareHouse.setDepotLongitude(wareHouseDtoRequest.getDepotLongitude());
		wareHouse.setDepotLatitude(wareHouseDtoRequest.getDepotLatitude());
		wareHouse.setDepotStatus(wareHouseDtoRequest.getDepotStatus());
		wareHouse.setDepotAddress(wareHouseDtoRequest.getDepotAddress());
		wareHouse.setDepotNumber(wareHouseDtoRequest.getDepotNumber());
		wareHouse.setCreatedBy(Constants.CREATED_BY);
		wareHouse.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		return transformResponseDtoFromEntity(wareHouseRepository.save(wareHouse));
		
	}
	
	@Override
	public WareHouseDtoResponse updateDepotStatus(String depotId, WareHouseStatus depotStatus) {
		WareHouseEntity statusToUpdate = wareHouseRepository.findByDepotId(depotId);
		statusToUpdate.setDepotStatus(depotStatus);
		statusToUpdate.setModifiedBy(Constants.CREATED_BY);
		statusToUpdate.setModifiedOn(new Timestamp(System.currentTimeMillis()));
		return transformResponseDtoFromEntity(wareHouseRepository.save(statusToUpdate));
	}

	@Override
	public WareHouseDtoResponse searchWareHouseById(String depotId) {
			WareHouseEntity wareHouseDetaliByDepotId = wareHouseRepository.findByDepotId(depotId);
			if (wareHouseDetaliByDepotId != null) {
			    return transformResponseDtoFromEntity(wareHouseDetaliByDepotId);
			}
			else {
				throw new RecordNotFoundException(depotId);
			}
	}
	
	@Override
	public WareHouseDtoResponse searchWareHouseByLocation(String depotLatitude, String depotLongitude) {
		WareHouseEntity wareHouseDetalisByCordinates = wareHouseRepository.findByDepotLatitudeAndDepotLongitude(depotLatitude, depotLongitude);
		if (wareHouseDetalisByCordinates != null) {
			return transformResponseDtoFromEntity(wareHouseDetalisByCordinates);
		} else {
			throw new RecordNotFoundException(depotLatitude + depotLongitude);
		}
	}

	@Override
	public List<WareHouseDtoResponse> searchWareHouseByStatus(WareHouseStatus depotStatus) {
		List <WareHouseDtoResponse> wareHouseResponse = new ArrayList<>();
			if(!depotStatus.toString().isEmpty()) {
				List<WareHouseEntity>  wareHouseDetaliByDepotStatus = wareHouseRepository.findByDepotStatus(depotStatus);
				for(WareHouseEntity wareHouseDetails :  wareHouseDetaliByDepotStatus) {
					WareHouseDtoResponse wareHouse = transformResponseDtoFromEntity(wareHouseDetails);
					wareHouseResponse.add(wareHouse);
				}
				
			}
		return wareHouseResponse;
	}
	
	@Override
	public List<WareHouseEntity> getWareHouse(){
		List<WareHouseEntity> retList = new ArrayList<>();
		try {
			List<WareHouseEntity> wareHouseList = wareHouseRepository.findAll();
			for (WareHouseEntity wareHouse : wareHouseList) {
				if(!wareHouse.getDepotStatus().equals(WareHouseStatus.DELETED))
					retList.add(wareHouse);
			}
		}
		catch (Exception e) {
	        String message = "Exception occurred not able to Get the WareHouse Details" + e.getMessage();
	        throw new RecordNotFoundException(message);
	    }
		return retList;
	}
	
	
	private WareHouseDtoResponse transformResponseDtoFromEntity(WareHouseEntity wareHouse) {
		ModelMapper mapper = new ModelMapper();
		return mapper.map(wareHouse, WareHouseDtoResponse.class);
		
	}

	@Override
	public WareHouseDtoResponse deleteWareHouseById(String depotId) {
		WareHouseEntity wareHouseDetail = wareHouseRepository.findByDepotId(depotId);
		if (wareHouseDetail != null) {
		wareHouseDetail.setDepotStatus(WareHouseStatus.DELETED);
		wareHouseDetail.setModifiedBy(Constants.CREATED_BY);
		wareHouseDetail.setModifiedOn(new Timestamp(System.currentTimeMillis()));
		wareHouseRepository.save(wareHouseDetail);
		return transformResponseDtoFromEntity(wareHouseDetail);
		}
		else {
			throw new RecordNotFoundException(depotId);
		}
	}

}
