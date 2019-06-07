package com.dunzung.cloud.framework.base;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Wooola on 2018/9/10.
 */
public interface CloudMapper<T> extends BaseMapper<T> {

    int getCount(T entity);

    List<T> getList(T entity);

}
