package com.test.resolvers;

import com.test.interf.ApiVersion;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

public class ApiVersionHandlerMapping extends RequestMappingHandlerMapping {
    // We no longer need to override getMappingForMethod. Instead, we provide a custom condition.
    @Override
    protected RequestCondition<?> getCustomMethodCondition(Method method) {
        ApiVersion apiVersion = AnnotatedElementUtils.findMergedAnnotation(method, ApiVersion.class);
        return (apiVersion != null) ? new ApiVersionCondition(apiVersion.value()) : null;
    }
    /*
    @Override
    protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
        ApiVersion apiVersion = AnnotatedElementUtils.findMergedAnnotation(handlerType, ApiVersion.class);
        return (apiVersion != null) ? new ApiVersionCondition(apiVersion.value()) : null;
    }
    */
}
