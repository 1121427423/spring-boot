package com.springboot.oauth.server.mapper;


import com.springboot.oauth.server.domain.table.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface SysMenuMapper {
    int deleteByPrimaryKey(Long menuId);

    int insert(SysMenu row);

    int insertSelective(SysMenu row);

    SysMenu selectByPrimaryKey(Long menuId);

    int updateByPrimaryKeySelective(SysMenu row);

    int updateByPrimaryKey(SysMenu row);

    List<SysMenu> selectMenuList();
    List<SysMenu> selectMenuListById(Long userId);

    List<String> selectMenuPermsByUserId(Long userId);
}