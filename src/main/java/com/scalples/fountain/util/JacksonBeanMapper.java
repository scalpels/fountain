package com.scalples.fountain.util;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class JacksonBeanMapper implements BeanMapper {
	private static ObjectMapper mapper = new ObjectMapper();

	public JacksonBeanMapper() {
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	@Override
	public <T> T map(Object fromValue, Class<T> toValueType) {
		return mapper.convertValue(fromValue, toValueType);
	}

	@Override
	public <T> List<T> mapList(Collection fromValueCollection, Class<T> toValueType) {
		@SuppressWarnings("unchecked")
		List<T> toValueList = (List<T>) fromValueCollection.stream().map(fromValue -> {
			return mapper.convertValue(fromValue, toValueType);
		}).collect(Collectors.toList());
		return toValueList;
	}
}