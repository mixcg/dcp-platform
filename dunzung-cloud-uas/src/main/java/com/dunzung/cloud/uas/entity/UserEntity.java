package com.dunzung.cloud.uas.entity;

import lombok.Data;

/**
 * Created by Wooola on 2019/6/7.
 */
@Data
public class UserEntity {

    private Long userId;

    private String userName;

    private String realName;

    private String orgId;

    private Integer status;

}
