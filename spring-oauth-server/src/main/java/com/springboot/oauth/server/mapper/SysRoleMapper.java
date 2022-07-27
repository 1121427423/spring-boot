package com.springboot.oauth.server.mapper;


import com.springboot.oauth.server.domain.table.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
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