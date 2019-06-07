package com.dunzung.cloud.sso.entity;

import org.springframework.data.rest.core.annotation.Description;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Blob;

/**
 * DROP TABLE IF EXISTS `oauth_client_details`;
 * CREATE TABLE `oauth_client_details` (
 * `token_id` varchar(255) DEFAULT NULL,
 * `token` blob,
 * `authentication_id` varchar(255) DEFAULT NULL,
 * `user_name` varchar(255) DEFAULT NULL,
 * `client_id` varchar(255) DEFAULT NULL,
 * `authentication` blob,
 * `refresh_token` varchar(255) DEFAULT NULL
 * ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
 */
@Description("{title: '授权问Token表'}")
@Entity
@Table(name = "oauth_access_token")
public class AuthAccessToken implements Serializable {

    @Description("{title: 'TokenID'}")
    @Id
    @Column(name = "token_id", unique = true, length = 255, nullable = false)
    private String tokenId;

    @Description("{title: 'Token数据'}")
    @Column(name = "token")
    private Blob token;

    @Description("{title: '授权ID'}")
    @Column(name = "authentication_id", length = 255)
    private String authenticationId;

    @Description("{title: '用户名'}")
    @Column(name = "user_name", length = 255)
    private String userName;

    @Description("{title: '客户端ID'}")
    @Column(name = "client_id", length = 255)
    private String clientId;

    @Description("{title: '授权信息'}")
    @Column(name = "authentication")
    private Blob authentication;

    @Description("{title: '刷新Token'}")
    @Column(name = "refresh_token", length = 255)
    private String refreshToken;

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public Blob getToken() {
        return token;
    }

    public void setToken(Blob token) {
        this.token = token;
    }

    public String getAuthenticationId() {
        return authenticationId;
    }

    public void setAuthenticationId(String authenticationId) {
        this.authenticationId = authenticationId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Blob getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Blob authentication) {
        this.authentication = authentication;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((tokenId == null) ? 0 : tokenId.hashCode());
        result = prime * result + ((authenticationId == null) ? 0 : authenticationId.hashCode());
        result = prime * result + ((userName == null) ? 0 : userName.hashCode());
        result = prime * result + ((clientId == null) ? 0 : clientId.hashCode());
        result = prime * result + ((refreshToken == null) ? 0 : refreshToken.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;

        AuthAccessToken other = (AuthAccessToken) obj;
        if (tokenId == null) {
            if (other.tokenId != null)
                return false;
        } else if (!tokenId.equals(other.tokenId))
            return false;

        if (authenticationId == null) {
            if (other.authenticationId != null)
                return false;
        } else if (!authenticationId.equals(other.authenticationId))
            return false;

        if (userName == null) {
            if (other.userName != null)
                return false;
        } else if (!userName.equals(other.userName))
            return false;

        if (clientId == null) {
            if (other.clientId != null)
                return false;
        } else if (!clientId.equals(other.clientId))
            return false;

        if (refreshToken == null) {
            if (other.refreshToken != null)
                return false;
        } else if (!refreshToken.equals(other.refreshToken))
            return false;

        return true;
    }

    /**
     * toString显示名词字段
     */
    public String toString() {
        return "";
    }

}
