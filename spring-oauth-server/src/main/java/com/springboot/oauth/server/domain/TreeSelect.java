package com.springboot.oauth.server.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.springboot.oauth.server.domain.table.SysMenu;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class TreeSelect {

    private Long id;
    private String label;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelect> children;

    public TreeSelect(SysMenu menu) {
        this.id = menu.getMenuId();
        this.label = menu.getMenuName();
        this.children = menu.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }
}
