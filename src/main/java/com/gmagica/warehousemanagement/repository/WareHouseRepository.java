package com.gmagica.warehousemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gmagica.warehousemanagement.entity.WareHouseEntity;
import com.gmagica.warehousemanagement.enums.WareHouseStatus;

@Repository
public interface WareHouseRepository extends JpaRepository<WareHouseEntity, String>{
	public WareHouseEntity findByDepotId(String depotId);
	public WareHouseEntity findByDepotLatitudeAndDepotLongitude(String depotLatitude, String depotLongitude);
	public List<WareHouseEntity> findByDepotStatus(WareHouseStatus depotStatus);
	
}
