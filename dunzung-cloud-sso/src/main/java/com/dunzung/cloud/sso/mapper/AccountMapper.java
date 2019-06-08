package com.dunzung.cloud.sso.mapper;

import com.dunzung.cloud.framework.base.CloudMapper;
import com.dunzung.cloud.sso.entity.AccountEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountMapper extends CloudMapper<AccountEntity> {

    AccountEntity getAccountByUserName(String userName);

}
