package com.dunzung.cloud.uas.service.impl;

import com.dunzung.cloud.components.oauth2.details.AuthUserInfo;
import com.dunzung.cloud.framework.dao.base.service.impl.MybatisServiceImpl;
import com.dunzung.cloud.uas.common.Const;
import com.dunzung.cloud.uas.entity.AccountEntity;
import com.dunzung.cloud.uas.entity.RoleEntity;
import com.dunzung.cloud.uas.entity.UserEntity;
import com.dunzung.cloud.uas.mapper.AccountMapper;
import com.dunzung.cloud.uas.service.UasService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义用户登录验证
 * <p>
 * Created by Wooola on 2019/6/07.
 */
@Component
public class UasServiceImpl extends MybatisServiceImpl<AccountMapper, AccountEntity> implements UasService {

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountEntity account = baseMapper.getAccountByUserName(username);
        if (null == account) {
            throw new UsernameNotFoundException("用户[" + username + "]不存在！");
        }
        if (Const.Status.LOCK == Const.Status.valueOf(account.getUserFlag())) {
            throw new UsernameNotFoundException("用户[" + username + "]已锁定！");
        }
        logger.info("用户名[" + username + "]登录成功！");
        List<GrantedAuthority> authorities = new ArrayList<>();

        UserEntity user = account.getUser();
        List<RoleEntity> roles = account.getRoles();
        for (RoleEntity role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleCode()));
        }
        if (authorities.isEmpty()) {
            throw new UsernameNotFoundException("用户[" + user.getUserName() + "]未授权！");
        }
        AuthUserInfo authUser = new AuthUserInfo(user.getUserName(), account.getPassword(), authorities);
        authUser.setOrgCode(account.getOrgan().getOrgId());
        authUser.setOrgName(account.getOrgan().getOrgName());
        return authUser;
    }


    @Override
    public void updatePassword(String userName, String password) {

    }

    @Override
    public void resetPassword(String username) {

    }

    @Override
    public void unlock(Long[] userIds) {

    }

    @Override
    public void validatePassword(String username, String password) {

    }
}
