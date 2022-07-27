package com.springboot.oauth.server.domain.table;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Data
@Alias("SysUser")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysUser implements Serializable {

    private static final long serialVersionUID = -2826872245907970145L;

    private Long userId;

    private String userCode;

    private String username;

    @JsonIgnore
    private String password;

    private Integer age;

    private String gender;

    private String nickName;

    private String email;

    private String phoneNo;

    private String avatar;

    private String status;

    @JsonIgnore
    private String token;

    @JsonIgnore
    private String delFlag;

    private String creator;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private String createAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String updater;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private String updateAt;
}