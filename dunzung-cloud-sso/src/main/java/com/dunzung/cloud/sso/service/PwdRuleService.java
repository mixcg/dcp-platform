package com.dunzung.cloud.sso.service;

import com.dunzung.cloud.framework.base.service.MybatisService;
import com.dunzung.cloud.sso.entity.PwdRuleEntity;

public interface PwdRuleService extends MybatisService<PwdRuleEntity> {

    /**
     * 查询密码规则
     *
     * @return
     */
    PwdRuleEntity findPwdRule();

    /**
     * 查询默认密码
     *
     * @return
     */
    String findDefaultPassword();

}
