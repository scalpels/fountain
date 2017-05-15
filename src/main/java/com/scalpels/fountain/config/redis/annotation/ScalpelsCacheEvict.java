package com.scalpels.fountain.config.redis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ScalpelsCacheEvict {


	String[] value() default {};


	String key() default "";


	String keyGenerator() default "";

	
	String cacheManager() default "";


	String cacheResolver() default "";


	String condition() default "";


	boolean allEntries() default false;


	boolean beforeInvocation() default false;

}