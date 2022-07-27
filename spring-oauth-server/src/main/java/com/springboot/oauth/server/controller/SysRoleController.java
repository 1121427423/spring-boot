package com.springboot.oauth.server.controller;

import com.springboot.oauth.server.domain.table.SysRole;
import com.springboot.oauth.server.service.SysRoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("manage/role")
public class SysRoleController {

    @Resource(name = "SysRoleService")
    private SysRoleService roleService;

    @PostMapping("/single/{id}")
    private Map<String, Object> selectByRoleId(@PathVariable("id") Long roleId) {
        return getResp("0000", "operation successfully completed", roleService.selectByPrimaryKey(roleId));
    }

    @PostMapping("/list")
    private Map<String, Object> selectRoleList() {
        return getResp("0000", "operation successfully completed", roleService.selectRoleList());
    }

    @PostMapping("/insert")
    private Map<String, Object> insert(@RequestBody SysRole sysRole) {
        roleService.insert(sysRole);
        return getResp("0000", "operation successfully completed", null);
    }

    @PutMapping("/update")
    private Map<String, Object> update(@RequestBody SysRole sysRole) {
        roleService.updateByPrimaryKey(sysRole);
        return getResp("0000", "operation successfully completed", null);
    }

    @PatchMapping("/update/selective")
    private Map<String, Object> updateSelective(@RequestBody SysRole sysRole) {
        roleService.updateByPrimaryKeySelective(sysRole);
        return getResp("0000", "operation successfully completed", null);
    }

    @DeleteMapping("/delete/{id}")
    private Map<String, Object> deleteByRoleId(@PathVariable("id") Long roleId) {
        roleService.deleteByPrimaryKey(roleId);
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
