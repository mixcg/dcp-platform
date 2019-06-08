package com.dunzung.cloud.uas.service.impl;

import com.dunzung.cloud.framework.dao.base.service.impl.MybatisServiceImpl;
import com.dunzung.cloud.framework.core.exception.PortalException;
import com.dunzung.cloud.framework.core.utils.CollectionUtil;
import com.dunzung.cloud.uas.entity.PwdRuleEntity;
import com.dunzung.cloud.uas.mapper.PwdRuleMapper;
import com.dunzung.cloud.uas.service.PwdRuleService;
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
