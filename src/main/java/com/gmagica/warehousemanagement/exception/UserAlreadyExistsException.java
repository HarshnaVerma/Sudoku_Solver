package com.gmagica.warehousemanagement.exception;


public class UserAlreadyExistsException extends PayconException {

        public UserAlreadyExistsException(String userAlreadyExist){
            super(userAlreadyExist);
            setParamNames("userAlreadyExist");
        }

}
