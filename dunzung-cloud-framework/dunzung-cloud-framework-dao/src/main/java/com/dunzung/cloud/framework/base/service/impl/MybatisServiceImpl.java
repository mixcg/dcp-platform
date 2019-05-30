package com.dunzung.cloud.framework.base.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dunzung.cloud.framework.base.service.MybatisService;
import com.dunzung.cloud.framework.base.PortalMapper;

public class MybatisServiceImpl<M extends PortalMapper<T>, T> extends ServiceImpl<M, T> implements MybatisService<T> {

}
