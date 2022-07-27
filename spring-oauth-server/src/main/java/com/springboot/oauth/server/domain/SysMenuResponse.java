package com.springboot.oauth.server.domain;

import lombok.Data;

import java.util.List;

@Data
public class SysMenuResponse {

    private String code;

    private String msg;

    private List<TreeSelect> menuList;

    public SysMenuResponse(String code, String msg, List<TreeSelect> treeSelects){
        this.code = code;
        this.msg = msg;
        this.menuList = treeSelects;
    }
}

