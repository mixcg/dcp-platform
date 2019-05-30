package com.dunzung.cloud.framework;

import com.dunzung.cloud.framework.exception.PortalException;
import com.dunzung.cloud.framework.security.SecConst;
import com.dunzung.cloud.framework.security.SecurityClient;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by duanzj on 2018/9/12.
 */
public final class RequestHolder {
    /**
     * 获取当前线程的Request对象
     */
    public static ThreadLocal<ServletRequest> requestLocal = new ThreadLocal<>();

    /**
     * 获取当前线程的Response对象
     */
    public static ThreadLocal<ServletResponse> responseLocal = new ThreadLocal<>();

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) requestLocal.get();
    }

    public static HttpServletResponse getResponse() {
        return (HttpServletResponse) responseLocal.get();
    }


    public static String getUserId() {
        String uid = SecurityClient.getUserId(getPid());
        if (StringUtils.isNotEmpty(uid)) {
            return uid;
        }
        throw new PortalException("用户不存在！");
    }

    /**
     * 获取岗位ID
     *
     * @return
     */
    public static String getSiteId() {
        String sid = SecurityClient.getSiteId(getPid());
        if (StringUtils.isNotEmpty(sid)) {
            return sid;
        }
        throw new PortalException("岗位ID不存在！");
    }

    /**
     * 获取部门ID
     *
     * @return
     */
    public static String getDeptId() {
        String deptId = SecurityClient.getDeptId(getPid());
        if (StringUtils.isNotEmpty(deptId)) {
            return deptId;
        }
        throw new PortalException("部门ID不存在！");
    }

    /**
     * 获取部门PATH
     *
     * @return
     */
    public static String getPath() {
        String path = SecurityClient.getPath(getPid());
        if (StringUtils.isNotEmpty(path)) {
            return path;
        }
        throw new PortalException("部门Path不存在！");
    }

    public static String getUserTrueName() {
        String userTrueName = SecurityClient.getUserTrueName(getPid());
        if (StringUtils.isNotEmpty(userTrueName)) {
            return userTrueName;
        }
        return "";
    }

    /**
     * 系统管理员、集团管理员
     *
     * @return
     */
    public static String getGlobalSign() {
        String globalSign = SecurityClient.getGlobalSign(getPid());
        if (StringUtils.isNotEmpty(globalSign)) {
            return globalSign;
        }
        throw new PortalException("globalSign不存在！");
    }

    /**
     * 站点ID
     *
     * @return
     */
    public static String getWid() {
        String wid = getRequest().getHeader("wid");
        if (StringUtils.isNotEmpty(wid)) {
            return wid;
        }
        throw new PortalException("站点ID不存在！");
    }

    public static String getPid() {
        return getRequest().getHeader(SecConst.HEADER.PID);
    }
}

