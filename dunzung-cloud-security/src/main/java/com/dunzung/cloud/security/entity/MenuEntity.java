package com.dunzung.cloud.security.entity;

import lombok.Data;

@Data
public class MenuEntity {

    private String menuId;

    private String menuCode;

    private String moduleCode;

    private String menuName;

    private String menuUrl;

    private String menuType;

    private MenuEntity parentMenu;

    private Long order;

    private String remark;

    private String status;

    private String iconName;

}
