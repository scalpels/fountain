package com.scalples.fountain.mapper;

import com.scalples.fountain.domain.Hotel;
import com.scalples.fountain.domain.HotelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HotelMapper {
    long countByExample(HotelExample example);

    int deleteByExample(HotelExample example);

    int insert(Hotel record);

    int insertSelective(Hotel record);

    List<Hotel> selectByExample(HotelExample example);

    int updateByExampleSelective(@Param("record") Hotel record, @Param("example") HotelExample example);

    int updateByExample(@Param("record") Hotel record, @Param("example") HotelExample example);
}