package com.dunzung.cloud.bams.entity;

import lombok.Data;

import java.util.Date;

@Data
public class MenuEntity {

    private String menuId;

    private String moduleCode;

    private String menuName;

    private String menuUrl;

    private String menuType;

    private Integer status;

    private String iconName;

    private Integer order;

    private String remark;


    private MenuEntity p;

    private String creator;

    private Date createdTm;

    private String modifier;

    private Date modifiedTm;

}
