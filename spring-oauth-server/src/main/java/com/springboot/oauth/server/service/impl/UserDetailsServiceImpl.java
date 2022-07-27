package com.springboot.oauth.server.service.impl;

import com.springboot.oauth.server.domain.LoginUser;
import com.springboot.oauth.server.domain.table.SysUser;
import com.springboot.oauth.server.service.SysUserService;
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
@Service("UserDetailsService")
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
