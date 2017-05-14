package com.scalpels.fountain.config;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ScalpelsRedisCache {

	@Pointcut("@annotation(com.scalpels.fountain.config.ScalpelsCacheable)")
    public void scalpelsCacheable(){
    	
    }
    
	@Before("scalpelsCacheable()")
	public void getFromCache(JoinPoint joinPoint) {
	    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
	    Method method = signature.getMethod();
	    ScalpelsCacheable cacheableAnnotation = method.getAnnotation(ScalpelsCacheable.class);
		System.out.println("Completed: " + joinPoint+cacheableAnnotation.value());
	}
	
	@AfterReturning("scalpelsCacheable()")
	public void sutToCache(JoinPoint joinPoint) {
	    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
	    Method method = signature.getMethod();
	    ScalpelsCacheable cacheableAnnotation = method.getAnnotation(ScalpelsCacheable.class);
		System.out.println("Completed: " + joinPoint+cacheableAnnotation.value());
	}

}