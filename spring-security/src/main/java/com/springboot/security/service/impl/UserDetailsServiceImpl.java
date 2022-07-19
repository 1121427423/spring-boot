package com.springboot.security.service.impl;

import com.springboot.security.service.SysUserService;
import com.springboot.security.domain.LoginUser;
import com.springboot.security.domain.table.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

/**
 * @author king
 * @version 1.0
 * @className UserService
 * @description TODO
 * @date 2022/7/9
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource(name = "SysUserService")
    private SysUserService userService;
    @Resource(name = "PermissionService")
    private SysPermissionServiceImpl permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 查询数据库信息
        SysUser sysUser = userService.selectUserByUsername(username);

        if (ObjectUtils.isEmpty(sysUser)) {
            log.info("登录用户：{} 不存在.", username);
            throw new UsernameNotFoundException("登录用户：" + username+ " 不存在");
        }

        return new LoginUser(sysUser, permissionService.getMenuPermission(sysUser), permissionService.getRolePermission(sysUser));
    }
}
