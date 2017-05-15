package com.scalpels.fountain.config.redis.cache;

import java.lang.reflect.Method;

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
}
