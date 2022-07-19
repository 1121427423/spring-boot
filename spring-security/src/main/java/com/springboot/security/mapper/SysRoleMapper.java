package com.springboot.security.mapper;

import com.springboot.security.domain.table.SysRole;

import java.util.List;
import java.util.Set;

public interface SysRoleMapper {
    int deleteByPrimaryKey(Long roleId);

    int insert(SysRole row);

    int insertSelective(SysRole row);

    SysRole selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(SysRole row);

    int updateByPrimaryKey(SysRole row);

    List<SysRole> selectRoleList();

    List<String> selectRolePermissionByUserId(Long userId);
}