package com.scalpels.fountain.config.redis;

import java.lang.reflect.Method;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Aspect
@Component
public class ScalpelsRedisCache {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	RedisTemplate<String, String> redisTemplate;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Pointcut("@annotation(com.scalpels.fountain.config.redis.ScalpelsCacheable)")
	public void scalpelsCacheable() {
	}

	@Around("scalpelsCacheable()")
	public Object operateCache(ProceedingJoinPoint joinPoint) throws Throwable {

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Class<?> returnType = signature.getReturnType();
		Method method = signature.getMethod();
		ScalpelsCacheable cacheableAnnotation = method.getAnnotation(ScalpelsCacheable.class);
		String fieldKey = parseKey(cacheableAnnotation.key(), method, joinPoint.getArgs());
		logger.info("fieldKey is {}", fieldKey);

		Map<Object, Object> entries = redisTemplate.opsForHash().entries(fieldKey);
		Object returnValue = objectMapper.convertValue(entries, returnType);
	    returnValue = joinPoint.proceed();

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