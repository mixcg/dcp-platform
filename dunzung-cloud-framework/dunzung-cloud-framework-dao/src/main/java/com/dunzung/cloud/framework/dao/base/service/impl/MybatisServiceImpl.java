package com.dunzung.cloud.framework.dao.base.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dunzung.cloud.framework.dao.base.CloudMapper;
import com.dunzung.cloud.framework.dao.base.service.MybatisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MybatisServiceImpl<M extends CloudMapper<T>, T> extends ServiceImpl<M, T> implements MybatisService<T> {
    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());
}
