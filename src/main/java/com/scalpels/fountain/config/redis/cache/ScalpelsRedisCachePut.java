//package com.scalpels.fountain.config.redis.cache;
//
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Method;
//import java.lang.reflect.Parameter;
//import java.util.Arrays;
//import java.util.Map;
//import java.util.Objects;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.expression.ExpressionParser;
//import org.springframework.expression.spel.standard.SpelExpressionParser;
//import org.springframework.expression.spel.support.StandardEvaluationContext;
//import org.springframework.security.core.parameters.AnnotationParameterNameDiscoverer;
//import org.springframework.stereotype.Component;
//
//import com.fasterxml.jackson.annotation.JsonInclude.Include;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.scalpels.fountain.config.redis.annotation.ScalpelsCacheBody;
//import com.scalpels.fountain.config.redis.annotation.ScalpelsCachePut;
//
//@Aspect
//@Component
//public class ScalpelsRedisCachePut {
//
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//	@Autowired
//	private RedisTemplate<String, String> redisTemplate;
//
//	private ObjectMapper objectMapper = new ObjectMapper();
//
//	@Pointcut("@annotation(com.scalpels.fountain.config.redis.annotation.ScalpelsCachePut)")
//	public void scalpelsCachePut() {
//	}
//
//	@Around("scalpelsCachePut()")
//	public Object operateCache(ProceedingJoinPoint joinPoint) throws Throwable {
//
//		objectMapper.setSerializationInclusion(Include.NON_NULL);
//
//		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//		Method method = signature.getMethod();
//		ScalpelsCachePut cachePutAnnotation = method.getAnnotation(ScalpelsCachePut.class);
//		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
//		method.getAnnotatedParameterTypes();
//
//		AnnotationParameterNameDiscoverer annotationParameterNameDiscoverer = new AnnotationParameterNameDiscoverer(ScalpelsCacheBody.class.toString());
//		annotationParameterNameDiscoverer.getParameterNames(method);
//		Class[] parameterTypes = method.getParameterTypes();
//		
//		Parameter[] parameter = method.getParameters();
//		Optional<Annotation> cacheBodyAnnotation = Arrays.stream(parameterAnnotations).map(annotations -> {
//			 return Arrays.stream(annotations).filter(annotation -> (annotation instanceof ScalpelsCacheBody)).findFirst().orElse(null);
//		 }).findFirst();
//
//		cacheBodyAnnotation.ifPresent(annotation -> {
//			
//		});
//		
//		int i = 0;
//		for (Annotation[] annotations : parameterAnnotations) {
//			Class parameterType = parameterTypes[i++];
//			for (Annotation annotation : annotations) {
//				if (annotation instanceof ScalpelsCacheBody) {
//					ScalpelsCacheBody myAnnotation = (ScalpelsCacheBody) annotation;
//				}
//			}
//		}
//
//		String fieldKey = parseKey(cachePutAnnotation.key(), method, joinPoint.getArgs());
//
//		final String cacheKey = cachePutAnnotation.value()[0].concat(":") + fieldKey;
//		logger.info("cache name is {}", fieldKey);
//
//		Object returnValue = joinPoint.proceed();
//		
//		if(Objects.nonNull(returnValue)){
//			Map<Object, Object> returnValueMap = objectMapper.convertValue(returnValue, Map.class);
//			returnValueMap = returnValueMap.entrySet().stream().map(entry -> {
//				return entry;
//			}).collect(Collectors.toMap((Map.Entry entry) ->{
//				return entry.getKey().toString();
//			}, (Map.Entry entry) ->{
//				return entry.getValue().toString();
//			}));
//			redisTemplate.opsForHash().putAll(cacheKey, returnValueMap);
//		}
//
//		return returnValue;
//	}
//
//	private String parseKey(String key, Method method, Object[] args) {
//		// get the parameters name table (support spring expression language)
//		LocalVariableTableParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
//		String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
//		// create spring expression parser
//		ExpressionParser parser = new SpelExpressionParser();
//		// create spring expression evaluation context
//		StandardEvaluationContext context = new StandardEvaluationContext();
//		// set parameters to evaluation context
//		for (int i = 0; i < parameterNames.length; i++) {
//			context.setVariable(parameterNames[i], args[i]);
//		}
//		return parser.parseExpression(key).getValue(context, String.class);
//	}
//
//}