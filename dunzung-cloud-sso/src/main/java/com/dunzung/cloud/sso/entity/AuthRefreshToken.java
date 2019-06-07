package com.dunzung.cloud.sso.entity;

import org.springframework.data.rest.core.annotation.Description;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Blob;

/**
 * DROP TABLE IF EXISTS `oauth_refresh_token`;
 * CREATE TABLE `oauth_refresh_token` (
 * `token_id` varchar(256) DEFAULT NULL,
 * `token` blob,
 * `authentication` blob
 * ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
 */
@Description("{title: '授权刷新Token表'}")
@Entity
@Table(name = "oauth_refresh_token")
public class AuthRefreshToken implements Serializable {

    private static final long serialVersionUID = 1L;

    @Description("{title: 'TokenID'}")
    @Id
    @Column(name = "token_id", unique = true, length = 255)
    private String tokenId;

    @Description("{title: 'Token数据'}")
    @Column(name = "token")
    private Blob token;

    @Description("{title: '授权信息'}")
    @Column(name = "authentication")
    private Blob authentication;

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

    public Blob getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Blob authentication) {
        this.authentication = authentication;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((tokenId == null) ? 0 : tokenId.hashCode());
        result = prime * result + ((token == null) ? 0 : token.hashCode());
        result = prime * result + ((authentication == null) ? 0 : authentication.hashCode());
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

        AuthRefreshToken other = (AuthRefreshToken) obj;
        if (tokenId == null) {
            if (other.tokenId != null)
                return false;
        } else if (!tokenId.equals(other.tokenId))
            return false;

        if (token == null) {
            if (other.token != null)
                return false;
        } else if (!token.equals(other.token))
            return false;

        if (authentication == null) {
            if (other.authentication != null)
                return false;
        } else if (!authentication.equals(other.authentication))
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
