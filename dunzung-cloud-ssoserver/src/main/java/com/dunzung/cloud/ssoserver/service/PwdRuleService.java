package com.dunzung.cloud.ssoserver.service;

import com.dunzung.cloud.framework.dao.base.service.MybatisService;
import com.dunzung.cloud.ssoserver.entity.PwdRuleEntity;

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
