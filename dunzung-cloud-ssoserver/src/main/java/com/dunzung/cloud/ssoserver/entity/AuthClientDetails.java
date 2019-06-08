package com.dunzung.cloud.ssoserver.entity;

import org.springframework.data.rest.core.annotation.Description;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * DROP TABLE IF EXISTS `oauth_client_details`;
 * CREATE TABLE `oauth_client_details` (
 * `client_id` varchar(255) NOT NULL,
 * `resource_ids` varchar(255) DEFAULT NULL,
 * `client_secret` varchar(255) DEFAULT NULL,
 * `scope` varchar(255) DEFAULT NULL,
 * `authorized_grant_types` varchar(255) DEFAULT NULL,
 * `web_server_redirect_uri` varchar(255) DEFAULT NULL,
 * `authorities` varchar(255) DEFAULT NULL,
 * `access_token_validity` int(11) DEFAULT NULL,
 * `refresh_token_validity` int(11) DEFAULT NULL,
 * `additional_information` varchar(4096) DEFAULT NULL,
 * `autoapprove` varchar(255) DEFAULT NULL,
 * PRIMARY KEY (`client_id`)
 * ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
 */
@Description("{title: '授权客户端信息表'}")
@Entity
@Table(name = "oauth_client_details")
public class AuthClientDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Description("{title: '客户端ID'}")
    @Id
    @Column(name = "client_id", unique = true, nullable = false, length = 255)
    private String clientId;

    @Description("{title: '资源ID'}")
    @Column(name = "resource_ids", length = 255)
    private String resourceIds;

    @Description("{title: '客户端密码'}")
    @Column(name = "client_secret", length = 255)
    private String clientSecret;

    @Description("{title: '范围'}")
    @Column(name = "scope", length = 255)
    private String scope;

    @Description("{title: '授权类型'}")
    @Column(name = "authorized_grant_types", length = 255)
    private String authorizedGrantTypes;

    @Description("{title: '回调地址'}")
    @Column(name = "web_server_redirect_uri", length = 255)
    private String redirectUri;

    @Description("{title: '权限'}")
    @Column(name = "authorities", length = 255)
    private String authorities;

    @Description("{title: '访问Token验证")
    @Column(name = "access_token_validity", length = 11)
    private Integer accessTokenValidity;

    @Description("{title: 'Token刷新验证'}")
    @Column(name = "refresh_token_validity", length = 11)
    private Integer refreshTokenValidity;

    @Description("{title: '附加信息'}")
    @Column(name = "additional_information", length = 4096)
    private String additionalInformation;

    @Description("{title: '自动审核'}")
    @Column(name = "autoapprove", length = 255)
    private String autoApprove;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public Integer getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public void setAccessTokenValidity(Integer accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }

    public Integer getRefreshTokenValidity() {
        return refreshTokenValidity;
    }

    public void setRefreshTokenValidity(Integer refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public String getAutoApprove() {
        return autoApprove;
    }

    public void setAutoApprove(String autoApprove) {
        this.autoApprove = autoApprove;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((clientId == null) ? 0 : clientId.hashCode());
        result = prime * result + ((resourceIds == null) ? 0 : resourceIds.hashCode());
        result = prime * result + ((clientSecret == null) ? 0 : clientSecret.hashCode());
        result = prime * result + ((redirectUri == null) ? 0 : redirectUri.hashCode());
        result = prime * result + ((scope == null) ? 0 : scope.hashCode());
        result = prime * result + ((accessTokenValidity == null) ? 0 : accessTokenValidity.hashCode());
        result = prime * result + ((refreshTokenValidity == null) ? 0 : refreshTokenValidity.hashCode());
        result = prime * result + ((autoApprove == null) ? 0 : autoApprove.hashCode());
        result = prime * result + ((authorities == null) ? 0 : authorities.hashCode());
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
        AuthClientDetails other = (AuthClientDetails) obj;
        if (clientId == null) {
            if (other.clientId != null)
                return false;
        } else if (!clientSecret.equals(other.clientSecret))
            return false;
        if (resourceIds == null) {
            if (other.resourceIds != null)
                return false;
        } else if (!resourceIds.equals(other.resourceIds))
            return false;
        if (redirectUri == null) {
            if (other.redirectUri != null)
                return false;
        } else if (!redirectUri.equals(other.redirectUri))
            return false;
        if (scope == null) {
            if (other.scope != null)
                return false;
        } else if (!scope.equals(other.scope))
            return false;
        if (accessTokenValidity == null) {
            if (other.accessTokenValidity != null)
                return false;
        } else if (!accessTokenValidity.equals(other.accessTokenValidity))
            return false;
        if (refreshTokenValidity == null) {
            if (other.refreshTokenValidity != null)
                return false;
        } else if (!refreshTokenValidity.equals(other.refreshTokenValidity))
            return false;
        if (autoApprove == null) {
            if (other.autoApprove != null)
                return false;
        } else if (!authorities.equals(other.authorities))
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
