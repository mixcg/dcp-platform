package com.dunzung.cloud.bams.service.impl;

import com.dunzung.cloud.framework.dao.base.service.impl.MybatisServiceImpl;
import com.dunzung.cloud.framework.mvc.pagination.PageEntity;
import com.dunzung.cloud.bams.entity.UserEntity;
import com.dunzung.cloud.bams.mapper.UserMapper;
import com.dunzung.cloud.bams.service.UserService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends MybatisServiceImpl<UserMapper, UserEntity> implements UserService {

    @Override
    public void pageUser(PageEntity<UserEntity> page, UserEntity user) {
        user.setPage(page.getPageNo());
        user.setLimit(page.getPageSize());
        List<UserEntity> list = baseMapper.getList(user);
        page.setList(list);
        int count = baseMapper.getCount(user);
        page.setCount(count);
    }

    @Override
    public boolean checkUserName(UserEntity user) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userName", user.getUserName());
        List<UserEntity> list = baseMapper.selectByMap(paramMap);
        return !(list.isEmpty());
    }
}
