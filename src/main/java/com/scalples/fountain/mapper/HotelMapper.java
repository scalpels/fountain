package com.scalples.fountain.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.scalples.fountain.domain.Hotel;
@Mapper
@Repository
public interface HotelMapper {

	Hotel selectByCityId(int city_id);

}
