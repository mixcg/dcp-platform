package com.dunzung.cloud.uas.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by Wooola on 2019/6/7.
 */
@Data
public class AccountEntity {

    private UserEntity user;

    private OrganEntity organ;

    private List<RoleEntity> roles;

    private String password;

    // @Description("{title: '启用&锁定状态'}")
    private Integer userFlag;

    //@Description("{title: '用户锁定时间'}")
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date lockedTime;

    // @Description("{title: '最后登录时间'}")
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date lastLoginTime;

    // @Description("{title: '登录次数'}")
    private Long loginNum;

    // @Description("{title: '是否首次登录'}")
    private Integer isFirstLogin;

    // @Description("{title: '密码修改时间'}")
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date pwdModifyTime;

    //@Description("{title: '密码错误次数'}")
    private Integer pwdErrorNum;

    //@Description("{title: '上次密码'}")
    private String oldPwd;

    // @Description("{title: '历史密码'}")
    private String hisPwd;

}
