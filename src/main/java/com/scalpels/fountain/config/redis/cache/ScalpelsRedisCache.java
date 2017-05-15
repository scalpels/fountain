package com.scalpels.fountain.config.redis.cache;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalpels.fountain.config.redis.annotation.ScalpelsCacheable;

import io.jsonwebtoken.lang.Strings;

@Aspect
@Component
public class ScalpelsRedisCache {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	private ObjectMapper objectMapper = new ObjectMapper();

	@Pointcut("@annotation(com.scalpels.fountain.config.redis.annotation.ScalpelsCacheable)")
	public void scalpelsCacheable() {
	}

	@SuppressWarnings("unchecked")
	@Around("scalpelsCacheable()")
	public Object operateCache(ProceedingJoinPoint joinPoint) throws Throwable {
		
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Class<?> returnType = signature.getReturnType();
		Method method = signature.getMethod();
		ScalpelsCacheable cacheableAnnotation = method.getAnnotation(ScalpelsCacheable.class);
		String fieldKey = parseKey(cacheableAnnotation.key(), method, joinPoint.getArgs());
		
		final String  cacheKey = cacheableAnnotation.value()[0].concat(":") + fieldKey;
		logger.info("cache name is {}", fieldKey);

		Object returnValue;
		
		Boolean exists = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.exists((cacheKey.getBytes()));
			}
		});
		
		if (!exists.booleanValue()) {
			returnValue = joinPoint.proceed();
			if(Objects.nonNull(returnValue)){
				Map<Object, Object> returnValueMap = objectMapper.convertValue(returnValue, Map.class);
				returnValueMap = returnValueMap.entrySet().stream().map(entry -> {
					return entry;
				}).collect(Collectors.toMap((Map.Entry entry) ->{
					return entry.getKey().toString();
				}, (Map.Entry entry) ->{
					return entry.getValue().toString();
				}));
				redisTemplate.opsForHash().putAll(cacheKey, returnValueMap);
			}
		}else{
			
			Map<Object, Object> entries = redisTemplate.opsForHash().entries(cacheKey);
			returnValue = objectMapper.convertValue(entries, returnType);

		}
		return returnValue;
	}

	private String parseKey(String key, Method method, Object[] args) {
		// get the parameters name table (support spring expression language)
		LocalVariableTableParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
		String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);

		// create spring expression parser
		ExpressionParser parser = new SpelExpressionParser();
		// create spring expression evaluation context
		StandardEvaluationContext context = new StandardEvaluationContext();
		// set parameters to evaluation context
		for (int i = 0; i < parameterNames.length; i++) {
			context.setVariable(parameterNames[i], args[i]);
		}
		return parser.parseExpression(key).getValue(context, String.class);
	}

}