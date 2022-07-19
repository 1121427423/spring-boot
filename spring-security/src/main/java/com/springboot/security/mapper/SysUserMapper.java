package com.springboot.security.mapper;

import com.springboot.security.domain.table.SysUser;

import java.util.List;

public interface SysUserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(SysUser row);

    int insertSelective(SysUser row);

    SysUser selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(SysUser row);

    int updateByPrimaryKey(SysUser row);

    List<SysUser> selectUserList();

    SysUser selectByUsername(String username);
}