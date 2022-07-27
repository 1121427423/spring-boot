package com.springboot.oauth.server.mapper;


import com.springboot.oauth.server.domain.table.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
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