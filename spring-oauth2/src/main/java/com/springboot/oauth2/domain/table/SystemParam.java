package com.springboot.oauth2.domain.table;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SystemParam {

    private Long paramId;

    private String projectName;

    private String moduleName;

    private String paramCode;

    private String paramValue;

    private String paramName;

    private String description;


}
