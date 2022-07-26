package com.springboot.oauth.server.service.impl;


import com.springboot.oauth.server.domain.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //实际是根据用户名去数据库查，这里就直接用静态数据了
        if(!username.equals("86547462")) {
            throw new UsernameNotFoundException("用户名不存在！");
        }
        // 密码加密
        String password = passwordEncoder.encode("123456");
        //创建User用户，自定义的User
        return new LoginUser(username,password, AuthorityUtils.
                commaSeparatedStringToAuthorityList("admin"));
    }
}
