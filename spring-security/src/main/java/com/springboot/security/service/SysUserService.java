package com.springboot.security.service;

import com.springboot.security.domain.table.SysUser;

import java.util.List;

public interface SysUserService {

    int deleteByPrimaryKey(Long userId);

    int insert(SysUser row);

    int insertSelective(SysUser row);

    SysUser selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(SysUser row);

    int updateByPrimaryKey(SysUser row);

    List<SysUser> selectUserList();

    SysUser selectUserByUsername(String username);
}
