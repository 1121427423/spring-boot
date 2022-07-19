package com.springboot.security.service.impl;

import com.springboot.security.service.SysMenuService;
import com.springboot.security.service.SysRoleService;
import com.springboot.security.domain.table.SysUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@Service("PermissionService")
public class SysPermissionServiceImpl {

    @Resource(name = "SysMenuService")
    private SysMenuService menuService;

    @Resource(name = "SysRoleService")
    private SysRoleService roleService;

    /**
     * 获取角色数据权限
     *
     * @param user 用户信息
     * @return 角色权限信息
     */
    public Set<String> getRolePermission(SysUser user) {
        return new HashSet<String>(roleService.selectRolePermissionByUserId(user.getUserId()));
    }

    /**
     * 获取菜单数据权限
     *
     * @param user 用户信息
     * @return 菜单权限信息
     */
    public Set<String> getMenuPermission(SysUser user) {
        return new HashSet<String>(menuService.selectMenuPermsByUserId(user.getUserId()));
    }
}
