package com.dunzung.cloud.uas.entity;

import org.springframework.data.rest.core.annotation.Description;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Blob;

/**
 * DROP TABLE IF EXISTS `oauth_code`;
 * CREATE TABLE `oauth_code` (
 * `code` varchar(256) DEFAULT NULL,
 * `authentication` blob
 * ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
 */
@Description("{title: '授权Code'}")
@Entity
@Table(name = "oauth_code")
public class AuthCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Description("{title: 'TokenID'}")
    @Id
    @Column(name = "code", unique = true, length = 255)
    private String code;

    @Description("{title: '授权信息'}")
    @Column(name = "authentication")
    private Blob authentication;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
        result = prime * result + ((code == null) ? 0 : code.hashCode());
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

        AuthCode other = (AuthCode) obj;
        if (code == null) {
            if (other.code != null)
                return false;
        } else if (!code.equals(other.code))
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
