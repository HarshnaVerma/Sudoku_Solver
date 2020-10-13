package com.gmagica.warehousemanagement.exception;


import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PayconException extends RuntimeException {


    protected Object[] params = new Object[0];

    protected String field ;

    protected Map<String, Object> namedParams = Collections.emptyMap();

    private Map<String, Object> eventParams = Collections.emptyMap();

    protected Locale locale = Locale.forLanguageTag("en");


    public PayconException(Object... params) {

        this.params=params;
    }


    public PayconException(Throwable cause, Object... params) {
        super(cause);
        this.params=params;

    }

    protected void setParamNames(String... paramNames) {
        if (paramNames.length != params.length) {
            throw new IllegalArgumentException("Parameter names count (" + paramNames.length + ") should be equal to parameter count (" + params.length + ")");
        }

        namedParams = new HashMap<>(params.length, 1);

        for (int i = 0; i < params.length; i++) {
            namedParams.put(paramNames[i], params[i]);
        }
    }


    public Object[] getParams() {

        return params;
    }

    public void setParams(Object[] params) {

        this.params = params;
    }

    public String getField() {

        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Map<String, Object> getNamedParams() {
        return namedParams;
    }

    public void setNamedParams(Map<String, Object> namedParams) {
        this.namedParams = namedParams;
    }

    public Map<String, Object> getEventParams() {
        return eventParams;
    }

    public void setEventParams(Map<String, Object> eventParams) {
        this.eventParams = eventParams;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}