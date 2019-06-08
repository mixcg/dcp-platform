package com.dunzung.cloud.uas.service;

import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by Wooola on 2019/6/7.
 */
public interface SsoService extends UserDetailsService {

    /**
     * 密码修改
     * @param userName
     * @param password
     */
    void updatePassword(String userName, String password);

    /**
     * 密码重置
     * @param username
     */
    void resetPassword(String username);

    /**
     * 解锁用户
     * @param userIds
     */
    void unlock(Long[] userIds);

    /**
     * 密码校验
     * @param username
     * @param password
     * @return
     */
    void validatePassword(String username, String password);

}
