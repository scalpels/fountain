package com.scalpels.fountain.config.redis.cache;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.stream.Collectors;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class ScalpelsRedisCache {

	public static final String cacheDelimieter = ":";

	protected ObjectMapper objectMapper = new ObjectMapper();

	public abstract Object operateCache(ProceedingJoinPoint joinPoint) throws Throwable;

	/**
	 * use spring expression to parse key
	 * @param key
	 * @param method
	 * @param args
	 * @return
	 */
	protected String parseKey(String key, Method method, Object[] args) {
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

	/**
	 * convert the object to <String.String> Map
	 * @param returnValue
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Map<Object, Object> objectToStringMap(Object returnValue) {
		Map<Object, Object> returnValueMap = objectMapper.convertValue(returnValue, Map.class);
		returnValueMap = returnValueMap.entrySet().stream().map(entry -> {
			return entry;
		}).collect(Collectors.toMap((Map.Entry entry) -> {
			return entry.getKey().toString();
		}, (Map.Entry entry) -> {
			return entry.getValue().toString();
		}));
		return returnValueMap;
	}
}
