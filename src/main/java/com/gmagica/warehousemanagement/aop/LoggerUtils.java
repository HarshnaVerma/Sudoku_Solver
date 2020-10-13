package com.gmagica.warehousemanagement.aop;

import com.gmagica.warehousemanagement.annotation.ParameterExclude;
import com.gmagica.warehousemanagement.annotation.ParameterLog;
import com.rits.cloning.Cloner;
import org.apache.commons.codec.digest.DigestUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class LoggerUtils {
    private static final Cloner CLONER = new Cloner();
    public static Object[] filterMethodParameters(Method method, Object[] args) {
        Parameter[] parameters = method.getParameters();
        Object[] filteredObjects = new Object[args.length];

        for (int i = 0; i < parameters.length; i++) {
            if (args[i] != null) {
                if (parameters[i].getAnnotation(ParameterLog.class) != null) {
                    filteredObjects[i] = encodeValue(CLONER.deepClone(args[i]));
                } else if (parameters[i].getAnnotation(ParameterExclude.class) != null)
                {
                    filteredObjects[i] = args[i].getClass();
                } else
                {
                    filteredObjects[i] = args[i];
                }
            }
        }
        return filteredObjects;
    }

    public static String encodeValue(Object value) {
        return DigestUtils.sha256Hex(value.toString());
}


}