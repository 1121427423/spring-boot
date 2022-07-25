package com.springboot.oauth2.mapper;

import com.springboot.oauth2.domain.table.SystemParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SystemParamMapper {

    @Select("select * from meta_data where project_name = #{name}")
    List<SystemParam> selectSystemParamsByName(@Param("name") String name);
}
