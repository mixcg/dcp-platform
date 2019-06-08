package com.dunzung.cloud.uam.service;

import com.dunzung.cloud.framework.dao.base.service.MybatisService;
import com.dunzung.cloud.framework.mvc.pagination.PageEntity;
import com.dunzung.cloud.uam.entity.UserEntity;

public interface UserService extends MybatisService<UserEntity> {

    void pageUser(PageEntity<UserEntity> page, UserEntity user);

    boolean checkUserName(UserEntity user);
}
