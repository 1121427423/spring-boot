package com.springboot.security.controller;

import com.springboot.security.service.SysMenuService;
import com.springboot.security.domain.table.SysMenu;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("manage/menu")
public class SysMenuController {

    @Resource(name = "SysMenuService")
    private SysMenuService menuService;

    @PostMapping("/single/{id}")
    private Map<String, Object> selectByRoleId(@PathVariable("id") Long menuId) {
        return getResp("0000", "operation successfully completed", menuService.selectByPrimaryKey(menuId));
    }

    @PostMapping("/list")
    private Map<String, Object> selectMenuList() {
        return getResp("0000", "operation successfully completed", menuService.selectMenuList());
    }

    @PostMapping("/insert")
    private Map<String, Object> insert(@RequestBody SysMenu sysMenu) {
        menuService.insert(sysMenu);
        return getResp("0000", "operation successfully completed", null);
    }

    @PutMapping("/update")
    private Map<String, Object> update(@RequestBody SysMenu sysMenu) {
        menuService.updateByPrimaryKey(sysMenu);
        return getResp("0000", "operation successfully completed", null);
    }

    @PatchMapping("/update/selective")
    private Map<String, Object> updateSelective(@RequestBody SysMenu sysMenu) {
        menuService.updateByPrimaryKeySelective(sysMenu);
        return getResp("0000", "operation successfully completed", null);
    }

    @DeleteMapping("/delete/{id}")
    private Map<String, Object> deleteByRoleId(@PathVariable("id") Long menuId) {
        menuService.deleteByPrimaryKey(menuId);
        return getResp("0000", "operation successfully completed", null);
    }

    private Map<String, Object> getResp(String code, String msg, Object data){
        LinkedHashMap<String, Object> resp = new LinkedHashMap<>();
        resp.put("code", code);
        resp.put("msg", msg);
        if(!Objects.isNull(data)) {
            resp.put("data", data);
        }
        return resp;
    }
}
