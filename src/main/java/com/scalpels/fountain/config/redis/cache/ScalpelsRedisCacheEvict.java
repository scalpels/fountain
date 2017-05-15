package com.scalpels.fountain.config.redis.cache;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.scalpels.fountain.config.redis.annotation.ScalpelsCacheEvict;

@Aspect
@Component
public class ScalpelsRedisCacheEvict extends ScalpelsRedisCache{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Pointcut("@annotation(com.scalpels.fountain.config.redis.annotation.ScalpelsCacheEvict)")
	public void scalpelsCacheEvict() {
	}

	@Override
	@Around("scalpelsCacheEvict()")
	public Object operateCache(ProceedingJoinPoint joinPoint) throws Throwable {

		objectMapper.setSerializationInclusion(Include.NON_NULL);

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		ScalpelsCacheEvict cacheEvictAnnotation = method.getAnnotation(ScalpelsCacheEvict.class);
		String fieldKey = parseKey(cacheEvictAnnotation.key(), method, joinPoint.getArgs());
		
		Object returnValue = joinPoint.proceed();
		
		Arrays.stream(cacheEvictAnnotation.value()).forEach(evictKey -> {
			String cacheName = evictKey.concat(ScalpelsRedisCache.cacheDelimieter) + fieldKey;
			logger.info("cache name is {}", cacheName);
			redisTemplate.delete(cacheName);
		});

		return returnValue;
	}

}