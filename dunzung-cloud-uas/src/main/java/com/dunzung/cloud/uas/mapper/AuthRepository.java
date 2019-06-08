package com.dunzung.cloud.uas.mapper;

import com.vpcloud.framework.jpa.base.repository.BaseRepository;
import com.vpcloud.platformwork.security.model.UserLogin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by zhijund on 2017/6/23.
 */
public interface AuthRepository extends BaseRepository<UserLogin, Long> {

    @Query("select ul from UserLogin ul, User u where ul.user.id = u.id and u.userName = :userName ")
    UserLogin findByUsername(@Param("userName") String userName);

    @Query("select ul from UserLogin ul, User u where ul.user.id = u.id and u.id = :userId ")
    UserLogin findByUserId(@Param("userId") Long userId);

}
