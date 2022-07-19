package com.springboot.security.service;

import com.springboot.security.domain.table.SysRole;

import java.util.List;
import java.util.Set;

public interface SysRoleService {

    int deleteByPrimaryKey(Long roleId);

    int insert(SysRole row);

    int insertSelective(SysRole row);

    SysRole selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(SysRole row);

    int updateByPrimaryKey(SysRole row);

    List<SysRole> selectRoleList();

    Set<String> selectRolePermissionByUserId(Long userId);
}
