package com.dunzung.cloud.uam.entity;

import lombok.Data;

import java.util.Date;

@Data
public class RoleEntity {

    private String roleId;

    private String roleName;

    private String roleType;

    private String admFlg;

    private int level;

    private String mgrRoleNames;

    private String remark;

    private String status;

    private String creator;

    private Date createdTm;

    private String modifier;

    private Date modifiedTm;

}
