package com.dunzung.cloud.sso.entity.vo;

import lombok.Data;

/**
 * Created by Wooola on 2019/6/7.
 */
@Data
public class AccountVO {

    private Long[] userIds;

    private Long userId;

    private String password;

}