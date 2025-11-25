package com.test.resolvers;


import java.lang.reflect.Method;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;


//@Configuration
public class MyCustomRequestMappingHandlerMapping extends RequestMappingHandlerMapping {
	@Override
	protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
		System.out.println("-- "+method.getName()+"::"+handlerType.getName());
		return super.getMappingForMethod(method, handlerType);
	}
}
