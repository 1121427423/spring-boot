package com.springboot.security.service.impl;

import com.springboot.security.service.SysUserService;
import com.springboot.security.domain.table.SysUser;
import com.springboot.security.mapper.SysUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("SysUserService")
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper userMapper;

    @Override
    public int deleteByPrimaryKey(Long userId) {
        return userMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public int insert(SysUser row) {
        return userMapper.insert(row);
    }

    @Override
    public int insertSelective(SysUser row) {
        return userMapper.insertSelective(row);
    }

    @Override
    public SysUser selectByPrimaryKey(Long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public int updateByPrimaryKeySelective(SysUser row) {
        return userMapper.updateByPrimaryKeySelective(row);
    }

    @Override
    public int updateByPrimaryKey(SysUser row) {
        return userMapper.updateByPrimaryKey(row);
    }

    @Override
    public List<SysUser> selectUserList() { return userMapper.selectUserList(); }

    @Override
    public SysUser selectUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }
}
