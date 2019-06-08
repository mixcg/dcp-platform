package com.dunzung.cloud.bams.entity;

import lombok.Data;

import java.util.Date;

@Data
public class OrganEntity {

    private String orgCode;

    private String orgName;

    private Integer level;

    private OrganEntity p;

    private String orgSeq;

    private String orgType;

    private String status;

    private Long sortNo;

    private String leafFlag;

    private String remark;

    private String creator;

    private Date createdTm;

    private String modifier;

    private Date modifiedTm;

}
