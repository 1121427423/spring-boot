package com.springboot.security.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.security.domain.table.SysRole;
import com.springboot.security.domain.table.SysUser;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class LoginUser implements UserDetails, Serializable {

    private static final long serialVersionUID = 2112304150406901511L;

    private SysUser sysUser;

    private Set<String> permissions;

    private Set<String> roles;

    @JSONField(serialize = false)
    @JsonIgnore
    private List<SimpleGrantedAuthority> authorities;

    private Long expireTime;

    private Long loginTime;

    private String token;
    public LoginUser() {}


    public LoginUser(SysUser sysUser, Set<String> permissions, Set<String> roles) {
        this.sysUser = sysUser;
        this.permissions = permissions;
        this.roles = roles;
        this.loginTime = System.currentTimeMillis();
        this.expireTime = System.currentTimeMillis() + 120 * 60 * 1000;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(!Objects.isNull(authorities)) {
            return authorities;
        }
        authorities = permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    @Override
    public String getUsername() { return sysUser.getUsername(); }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return "0".equals(sysUser.getStatus()); }

    @Override
    public boolean isCredentialsNonExpired() {
        return expireTime > System.currentTimeMillis();
    }

    @Override
    public boolean isEnabled() {
        return "0".equals(sysUser.getDelFlag());
    }
}
