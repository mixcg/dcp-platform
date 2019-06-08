package com.dunzung.cloud.framework.oauth.details;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Created by Wooola on 2019/6/7.
 */
@Data
public class AuthUserInfo extends User {

    private String orgCode;

    private String orgName;

    public AuthUserInfo(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

}
