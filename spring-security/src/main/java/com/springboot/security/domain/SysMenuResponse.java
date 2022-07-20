package com.springboot.security.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.security.domain.table.SysMenu;
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

