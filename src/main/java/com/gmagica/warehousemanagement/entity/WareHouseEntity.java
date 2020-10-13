package com.gmagica.warehousemanagement.entity;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gmagica.warehousemanagement.enums.WareHouseStatus;

import lombok.Data;

@Entity
@Data
@Table(name ="warehouse_details")
public class WareHouseEntity {

    @Id
    private String depotId;
    private String depotName;
    private String depotLatitude;
    private String depotLongitude;
    private WareHouseStatus depotStatus;
    private String createdBy;
    private String modifiedBy;
    private Date createdOn;
    private Date modifiedOn;
    private String depotNumber;
	private String depotAddress;

}
