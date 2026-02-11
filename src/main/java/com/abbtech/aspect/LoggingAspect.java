package com.abbtech.aspect;

import com.abbtech.annotation.LogIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Configuration
@Slf4j
public class LoggingAspect {

    private final ObjectMapper mapper;

    public LoggingAspect() {
        this.mapper = new ObjectMapper();
        this.mapper.setSerializerFactory(
                this.mapper.getSerializerFactory()
                        .withSerializerModifier(new BeanSerializerModifier() {
                            @Override
                            public List<BeanPropertyWriter> changeProperties(
                                    com.fasterxml.jackson.databind.SerializationConfig config,
                                    com.fasterxml.jackson.databind.BeanDescription beanDesc,
                                    List<BeanPropertyWriter> beanProperties) {

                                List<BeanPropertyWriter> filtered = new ArrayList<>();

                                for (BeanPropertyWriter writer : beanProperties) {
                                    try {
                                        Field field = beanDesc.getBeanClass()
                                                .getDeclaredField(writer.getName());

                                        if (!field.isAnnotationPresent(LogIgnore.class)) {
                                            filtered.add(writer);
                                        }
                                    } catch (NoSuchFieldException e) {
                                        filtered.add(writer);
                                    }
                                }
                                return filtered;
                            }
                        })
        );
    }

    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object logApi(ProceedingJoinPoint joinPoint) throws Throwable {

        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        try {
            String request = mapper.writeValueAsString(joinPoint.getArgs());
            log.info("Incoming request", className, methodName, request);
        } catch (Exception e) {
            log.warn("Request log error");
        }

        Object result = joinPoint.proceed();


        try {
            String response = mapper.writeValueAsString(result);
            log.info("Outgoing response", className, methodName, response);
        } catch (Exception e) {
            log.warn("Response log error");
        }

        return result;
    }
}

