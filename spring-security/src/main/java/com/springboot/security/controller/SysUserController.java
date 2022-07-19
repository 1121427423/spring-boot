package com.springboot.security.controller;

import com.springboot.security.service.SysUserService;
import com.springboot.security.domain.table.SysUser;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("manage/user")
public class SysUserController {

    @Resource(name = "SysUserService")
    private SysUserService userService;

    @PostMapping("/single/{id}")
    private Map<String, Object> selectByUserId(@PathVariable("id") Long userId) {
        return getResp("0000", "operation successfully completed", userService.selectByPrimaryKey(userId));
    }

    @PostMapping("/list")
    private Map<String, Object> selectUserList() {
        return getResp("0000", "operation successfully completed", userService.selectUserList());
    }

    @PostMapping("/insert")
    private Map<String, Object> insert(@RequestBody SysUser sysUser) {
        userService.insert(sysUser);
        return getResp("0000", "operation successfully completed", null);
    }

    @PutMapping("/update")
    private Map<String, Object> update(@RequestBody SysUser sysUser) {
        userService.updateByPrimaryKey(sysUser);
        return getResp("0000", "operation successfully completed", null);
    }

    @PatchMapping("/update/selective")
    private Map<String, Object> updateSelective(@RequestBody SysUser sysUser) {
        userService.updateByPrimaryKeySelective(sysUser);
        return getResp("0000", "operation successfully completed", null);
    }

    @DeleteMapping("/delete/{id}")
    private Map<String, Object> deleteByUserId(@PathVariable("id") Long userId) {
        userService.deleteByPrimaryKey(userId);
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
