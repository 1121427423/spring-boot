package com.springboot.security.service;

import com.springboot.security.domain.TreeSelect;
import com.springboot.security.domain.table.SysMenu;

import java.util.List;
import java.util.Set;

public interface SysMenuService {

    int deleteByPrimaryKey(Long menuId);

    int insert(SysMenu row);

    int insertSelective(SysMenu row);

    SysMenu selectByPrimaryKey(Long menuId);

    int updateByPrimaryKeySelective(SysMenu row);

    int updateByPrimaryKey(SysMenu row);

    List<SysMenu> selectMenuList();

    List<SysMenu> selectMenuListById(Long userId);

    List<SysMenu> buildMenuTree(List<SysMenu> menus);

    List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus);

    Set<String> selectMenuPermsByUserId(Long userId);
}
