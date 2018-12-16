package com.scalpels.fountain.mapper;

import com.scalpels.fountain.entity.Configuration;
import com.scalpels.fountain.entity.ConfigurationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ConfigurationMapper {
    long countByExample(ConfigurationExample example);

    int deleteByExample(ConfigurationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Configuration record);

    int insertSelective(Configuration record);

    List<Configuration> selectByExample(ConfigurationExample example);

    Configuration selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Configuration record, @Param("example") ConfigurationExample example);

    int updateByExample(@Param("record") Configuration record, @Param("example") ConfigurationExample example);

    int updateByPrimaryKeySelective(Configuration record);

    int updateByPrimaryKey(Configuration record);
}