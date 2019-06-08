package com.dunzung.cloud.uas.mapper;

import com.dunzung.cloud.framework.dao.base.CloudMapper;
import com.dunzung.cloud.uas.entity.AccountEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountMapper extends CloudMapper<AccountEntity> {

    AccountEntity getAccountByUserName(String userName);

}

