package com.springboot.oauth.server.domain.table;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Data
@Alias("SysRole")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysRole implements Serializable {

    private static final long serialVersionUID = 6081986428606525326L;

    private Long roleId;

    private String roleCode;

    private String roleName;

    private String roleType;

    private String roleDesc;

    private String status;

    private String remark;

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