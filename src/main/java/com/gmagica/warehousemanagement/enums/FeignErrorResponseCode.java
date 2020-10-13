package com.gmagica.warehousemanagement.enums;

import static com.gmagica.warehousemanagement.enums.FeignErrorResponseCode.Category.*;

public enum FeignErrorResponseCode {
    USER_NOT_FOUND(USER_NOT_EXIST,5001),
    ACCOUNT_NOT_FOUND(ACCOUNT_NOT_EXIST, 1708),
    ACCOUNT_ACTIVE(ACCOUNT_ACTIVE_ALREADY, 1777),
    ACCOUNT_INACTIVE(ACCOUNT_INACTIVE_ALREADY, 1778);;




    private int feignResponseCode;
    private Category category;

    private FeignErrorResponseCode(Category category, int feignResponseCode) {
        this.feignResponseCode = feignResponseCode;
        this.category = category;
    }

    public int getFeignResponseCode() {
        return feignResponseCode;
    }



    public Category getCategory() {
        return category;
    }


    public enum Category {

        USER_NOT_EXIST,
        ACCOUNT_NOT_EXIST,
        ACCOUNT_ACTIVE_ALREADY,
        ACCOUNT_INACTIVE_ALREADY

    }
}