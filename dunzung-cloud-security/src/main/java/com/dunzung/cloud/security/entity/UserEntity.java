package com.dunzung.cloud.security.entity;

import com.dunzung.cloud.framework.pagination.PageParam;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class UserEntity extends PageParam {

    private Long userId;

    private String userName;

    private String password;

    private String realName;

    private String orgId;

    private Integer status;

    private String remark;

    private String mobilePhone;

    private String creator;

    private Date createdTm;

    private String modifier;

    private Date modifiedTm;

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserEntity pojo = (UserEntity) o;
        if (userId != null ? !userId.equals(pojo.userId) : pojo.userId != null) {
            return false;
        }
        if (userName != null ? !userName.equals(pojo.userName) : pojo.userName != null) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int result = (userName != null ? userName.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append(" [");
        sb.append("用户主键").append("：'").append(getUserId()).append("', ");
        sb.append("用户名").append("：'").append(getUserName()).append("', ");
        sb.append("姓名").append("：'").append(getRealName()).append("' ");
        sb.append("]");
        return sb.toString();
    }

}
