package com.dunzung.cloud.sso.service.impl;

import com.dunzung.cloud.framework.base.service.impl.MybatisServiceImpl;
import com.dunzung.cloud.framework.exception.PortalException;
import com.dunzung.cloud.framework.utils.CollectionUtil;
import com.dunzung.cloud.sso.entity.PwdRuleEntity;
import com.dunzung.cloud.sso.mapper.PwdRuleMapper;
import com.dunzung.cloud.sso.service.PwdRuleService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class PwdRuleServiceImpl extends MybatisServiceImpl<PwdRuleMapper, PwdRuleEntity> implements PwdRuleService {

    public PwdRuleEntity findPwdRule() {
        List<PwdRuleEntity> list = baseMapper.selectByMap(new HashMap());
        if (CollectionUtil.isBlank(list)) {
            throw new PortalException("密码策略不存在！");
        }
        return list.get(0);
    }

    public String findDefaultPassword() {
        PwdRuleEntity passwordRule = findPwdRule();
        return passwordRule.getDefaultPwd();
    }

}
