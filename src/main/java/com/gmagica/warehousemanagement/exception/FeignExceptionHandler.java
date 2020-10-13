/**
 * Paycon Framework for Enterprise Application
 * null.java
 * User: Paycon Developer : daya.shanker
 * Date: 2019-11-01
 * Time: 17:46
 */
package com.gmagica.warehousemanagement.exception;


import com.google.gson.Gson;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class FeignExceptionHandler {

    Gson gson = new Gson();
    //@ExceptionHandler({FeignException.class, FeignException.class })
    public Map<String, Object> handleFeignStatusException(FeignException e, HttpServletResponse response) {
        response.setStatus(e.status());
          log.info("Error Content"+ new JSONObject(e.contentUTF8()).toMap());
        return  new JSONObject(e.contentUTF8()).toMap();
    }

}