package com.gmagica.warehousemanagement.aop;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
import com.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.gmagica.warehousemanagement.annotation.ParameterLog;

import lombok.extern.slf4j.Slf4j;


@Aspect
@Component
@Slf4j
public class UserAccessAspect {

    /**
     * private static final String CORRELATION_ID = "CorrelationId : ";
     */
    private static final String INPUT_ARGUMENTS = " Input arguments : ";
    private static final String OUTPUT_ARGUMENTS = " Results : ";
    private static final String METHOD_NAME = " Method : ";
    private static final String START_TIME = " Start time : ";

    /**
     * private static final String END_TIME = " End time : ";
     * private static final String TOTAL_TIME = " Total time taken: ";
     * private static final String ERROR_RCVD = " Error Received : " ;
     * private static final String ERROR_RCVD1 = " Error Received1 : " ;
     */


    @Pointcut("execution(* com.gmagica.warehousemanagement.*.*.*(..)) ")
    //@Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void controller() {
    }

    @Pointcut("execution(* *..*(..))")
    protected void allMethod() {
    }

    @Pointcut("execution(public * *(..))")
    protected void loggingPublicOperation() {
    }

    @Pointcut("execution(* *.*(..))")
    protected void loggingAllOperation() {
    }

    @Pointcut("within(com.gmagica.tsdrivertrackingservice..*)")
    private void logAnyFunctionWithinResource() {
    }


    private String objectToJson(Object object) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        AnnotationIntrospector annotationIntrospector = objectMapper.getSerializationConfig().getAnnotationIntrospector();
        AnnotationIntrospector annotationIntrospectorPair = AnnotationIntrospectorPair.pair(annotationIntrospector, new SanitizeAnnotationIntrospector());
        objectMapper.setAnnotationIntrospector(annotationIntrospectorPair);

        SimpleModule module = new SimpleModule("Custom serializer");
        module.addSerializer(byte[].class, new CustomByteSerializer(byte[].class));
        module.addSerializer(Date.class, new CustomDateSerializer(Date.class));
        objectMapper.registerModule(module);
        StringWriter stringEmp = new StringWriter();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.writeValue(stringEmp, object);
        return stringEmp.toString();
    }


    @Before("controller() ")
    public void beforeAdvice(JoinPoint joinPoint) {
        long startTime = System.currentTimeMillis();
        Date date = new Date();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(START_TIME);
        stringBuilder.append(startTime + "\n");
        stringBuilder.append(METHOD_NAME);
        stringBuilder.append(joinPoint.getSignature().getName());
        stringBuilder.append(INPUT_ARGUMENTS);
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Object[] args = LoggerUtils.filterMethodParameters(signature.getMethod(),
                joinPoint.getArgs());

        if (args != null && args.length > 0) {
            try {

                logInputArgs(args, stringBuilder);
            } catch (IOException e) {
                log.info("Error in argument logging....");
            }
        }

        log.info(stringBuilder.toString());
        /** // System.out.println("Before method:" + joinPoint.getSignature());
         //log.info("Entering in Method :  " + joinPoint.getSignature().getName());
         /*     log.info("Class Name :  " + joinPoint.getSignature().getDeclaringTypeName());
         log.info("Arguments :  " + Arrays.toString(joinPoint.getArgs()));
         log.info("Target class : " + joinPoint.getTarget().getClass().getName());
         */
    }

    @AfterReturning(pointcut = "controller() &&  allMethod()", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        String returnValue = this.getValue(result);
        log.info("Method Return value : " + returnValue);
    }

    @Around("controller() && allMethod()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();
        try {
            String className = joinPoint.getSignature().getDeclaringTypeName();
            String methodName = joinPoint.getSignature().getName();
            Object result = joinPoint.proceed();
            long elapsedTime = System.currentTimeMillis() - start;
            log.info("Method " + className + "." + methodName + " ()" + " execution time : "
                    + elapsedTime + " ms");

            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument " + Arrays.toString(joinPoint.getArgs()) + " in "
                    + joinPoint.getSignature().getName() + "()");
            throw e;
        }
    }

    private String getValue(Object result) {
        String returnValue = null;
        if (null != result) {
            if (result.toString().endsWith("@" + Integer.toHexString(result.hashCode()))) {
                returnValue = ReflectionToStringBuilder.toString(result);
            } else {
                returnValue = result.toString();
            }
        }
        return returnValue;
    }

    private void logInputArgs(Object[] args, StringBuilder stringBuilder) throws IOException {
        stringBuilder.append(INPUT_ARGUMENTS);
        stringBuilder.append(objectToJson(args) + "\n");
    }

    private void logReturnResult(Object result, StringBuilder stringBuilder) throws IOException {
        stringBuilder.append(OUTPUT_ARGUMENTS);
        stringBuilder.append(objectToJson(result) + "\n");
    }

    private static class SanitizeSerializer extends StdSerializer<Object> {

        public SanitizeSerializer() {
            super(Object.class);
        }

        @Override
        public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            jgen.writeString(LoggerUtils.encodeValue(value));
        }
    }

    private class SanitizeAnnotationIntrospector extends NopAnnotationIntrospector {

        @Override
        public Object findSerializer(Annotated am) {


            ParameterLog annotation = am.getAnnotation(ParameterLog.class);
            if (annotation != null) {
                return SanitizeSerializer.class;
            }
            return null;
        }
    }

    //Serializers
    private class CustomByteSerializer extends StdSerializer<byte[]> {

        public CustomByteSerializer(Class<byte[]> t) {
            super(t);
        }

        @Override
        public void serialize(byte[] value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            jgen.writeString(value.length + " bytes");
        }

    }

    private class CustomDateSerializer extends StdSerializer<Date> {

        public CustomDateSerializer(Class<Date> t) {
            super(t);
        }

        @Override
        public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            jgen.writeString(value.toString());
        }

    }
}



