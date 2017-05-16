package com.scalpels.fountain.config.redis.cache;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.scalpels.fountain.config.redis.annotation.ScalpelsCacheable;

@Aspect
@Component
public class ScalpelsRedisCacheable extends ScalpelsRedisCache {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Pointcut("@annotation(com.scalpels.fountain.config.redis.annotation.ScalpelsCacheable)")
	public void scalpelsCacheable() {
	}

	@Override
	@Around("scalpelsCacheable()")
	public Object operateCache(ProceedingJoinPoint joinPoint) throws Throwable {

		objectMapper.setSerializationInclusion(Include.NON_NULL);

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Class<?> returnType = signature.getReturnType();
		Method method = signature.getMethod();
		ScalpelsCacheable cacheableAnnotation = method.getAnnotation(ScalpelsCacheable.class);

		String fieldKey = parseKey(cacheableAnnotation.key(), method, joinPoint.getArgs());

		final String cacheKey = cacheableAnnotation.value()[0].concat(ScalpelsRedisCache.cacheDelimieter) + fieldKey;
		logger.info("cacheKey is {}", cacheKey);
		Object returnValue;

		Boolean exists = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.exists((cacheKey.getBytes()));
			}
		});

		if (!exists.booleanValue()) {
			returnValue = joinPoint.proceed();
			if (Objects.nonNull(returnValue)) {
				Map<Object, Object> returnValueMap = objectToStringMap(returnValue);
				redisTemplate.opsForHash().putAll(cacheKey, returnValueMap);
			}
		} else {
			Map<Object, Object> entries = redisTemplate.opsForHash().entries(cacheKey);
			returnValue = objectMapper.convertValue(entries, returnType);
		}
		return returnValue;
	}

}