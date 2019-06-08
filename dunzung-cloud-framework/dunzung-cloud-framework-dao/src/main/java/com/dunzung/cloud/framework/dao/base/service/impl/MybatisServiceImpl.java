package com.dunzung.cloud.framework.dao.base.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dunzung.cloud.framework.dao.base.service.MybatisService;
import com.dunzung.cloud.framework.dao.base.CloudMapper;

public class MybatisServiceImpl<M extends CloudMapper<T>, T> extends ServiceImpl<M, T> implements MybatisService<T> {

}
