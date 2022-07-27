package com.springboot.oauth.server.service.impl;

import com.springboot.oauth.server.domain.table.SysRole;
import com.springboot.oauth.server.mapper.SysRoleMapper;
import com.springboot.oauth.server.service.SysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("SysRoleService")
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleMapper roleMapper;

    @Override
    public int deleteByPrimaryKey(Long roleId) {
        return roleMapper.deleteByPrimaryKey(roleId);
    }

    @Override
    public int insert(SysRole row) {
        return roleMapper.insert(row);
    }

    @Override
    public int insertSelective(SysRole row) {
        return roleMapper.insertSelective(row);
    }

    @Override
    public SysRole selectByPrimaryKey(Long roleId) {
        return roleMapper.selectByPrimaryKey(roleId);
    }

    @Override
    public int updateByPrimaryKeySelective(SysRole row) {
        return roleMapper.updateByPrimaryKeySelective(row);
    }

    @Override
    public int updateByPrimaryKey(SysRole row) {
        return roleMapper.updateByPrimaryKey(row);
    }

    @Override
    public List<SysRole> selectRoleList() {
        return roleMapper.selectRoleList();
    }

    @Override
    public Set<String> selectRolePermissionByUserId(Long userId) {
        return new HashSet<>(roleMapper.selectRolePermissionByUserId(userId));
    }
}
