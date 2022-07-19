package com.springboot.security.mapper;

import com.springboot.security.domain.table.SysMenu;

import java.util.List;

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