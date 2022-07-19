package com.springboot.security.domain.table;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.ibatis.type.Alias;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Alias("SysMenu")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 3501209770417702594L;

    private Long menuId;

    private String menuCode;

    private String menuName;

    private String parentName;

    private Long parentId;

    private Integer orderNum;

    private String path;

    private String component;

    @JsonIgnore
    private String permission;

    private String isFrame;

    private String menuType;

    private String visible;

    private String icon;

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

    private List<SysMenu> children = new ArrayList<SysMenu>();

}