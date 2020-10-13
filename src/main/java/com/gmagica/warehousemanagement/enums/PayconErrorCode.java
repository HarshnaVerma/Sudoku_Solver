package com.gmagica.warehousemanagement.enums;


import static com.gmagica.warehousemanagement.enums.PayconErrorCode.Category.*;

public enum PayconErrorCode {


    UNKNOWN(OTHER, 5050),
    USER_NOT_FOUND(USER, 1001),
    RECORD_NOT_FOUND(USER,1002 ),
    TENANT_NOT_FOUND(TENANT, 500),
    INVALID_CREDENTIALS(SECURITY, 1707),
    ACCOUNT_NOT_FOUND(SECURITY, 1708),
    TRSACTION_FAILED(TRANSACTIONS, 4001),
    TRANSACTION_NOT_FOUND(TRANSACTIONS, 4002),
    SERVICE_TYPE_NOT_FOUND(SERVICE, 4003),
    SUBSCRIBER_NOT_FOUND(SUBSCRIBER, 2101),
    PENDING_SUBSCRIPTION_REQUEST(SUBSCRIPTION, 2102),
    PRODUCT_NOT_FOUND(PRODUCT, 2103),
    WAREHOUSE_NOT_FOUND(WAREHOUSE, 4004),
    SUBSCRIPTION_ALREADY_EXISTS(SUBSCRIPTION, 2104);


    private int payconErrorCode;
    private Category category;


    private PayconErrorCode(Category category, int payconErrorCode) {
        this.category = category;
        this.payconErrorCode = payconErrorCode;
    }

    public int getPayconErrorCode() {
        return payconErrorCode;
    }

    public Category getCategory() {
        return category;
    }

    /**
     * THIS IS EXCEPTION CATEGORIES
     * Most of the exceptions (expected ones) belong to one of the items listed.
     */
    public enum Category {
        USER,
        TENANT,
        OTHER,
        SECURITY,
        SUBSCRIBER,
        TRANSACTIONS,
        SERVICE,
        SUBSCRIPTION,
        PRODUCT,
        WAREHOUSE
    }
}
