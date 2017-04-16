package com.scalples.fountain.util;

import java.util.Collection;
import java.util.List;

public interface BeanMapper {
	public <T> T map(Object fromValue, Class<T> toValueType);
	
	public <T> List<T> mapList(Collection fromValue, Class<T> toValueType);
}