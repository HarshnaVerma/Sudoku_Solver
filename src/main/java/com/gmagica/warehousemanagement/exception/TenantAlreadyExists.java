package com.gmagica.warehousemanagement.exception;

public class TenantAlreadyExists extends PayconException {
    public TenantAlreadyExists(String tenantName) {
            super(tenantName);
            setParamNames("tenantName");
        }
}
