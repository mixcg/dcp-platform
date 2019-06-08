package com.dunzung.cloud.framework.mvc.security;

import com.alibaba.fastjson.JSON;
import com.dunzung.cloud.framework.core.security.AES;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by duanzj on 2018/12/18.
 */
public final class SecurityClient {
    protected static Logger logger = LoggerFactory.getLogger(SecurityClient.class);

    public static String getUserId(String pid) {
        return getHeadValue(pid, SecConst.HEADER.UID);
    }

    public static String getSiteId(String pid) {
        return getHeadValue(pid, SecConst.HEADER.SID);
    }

    public static String getDeptId(String pid) {
        return getHeadValue(pid, SecConst.HEADER.DID);
    }

    public static String getPath(String pid) {
        return getHeadValue(pid, SecConst.HEADER.PATH);
    }

    public static String getGlobalSign(String pid) {
        return getHeadValue(pid, SecConst.HEADER.GS);
    }

    public static String getWid(String pid) {
        return getHeadValue(pid, SecConst.HEADER.WID);
    }

    public static String getUserTrueName(String pid) {
        return getHeadValue(pid, SecConst.HEADER.TRUENAME);
    }

    /**
     * {"deptId":"00371608443","email":"mali3@chinaunicom.cn","globalId":"0000","globalSign":false,"isParent":0,
     * "mobile":"18653590789","orgName":"烟台市分公司社会渠道营销中心","path":"00,0000,0037,003716,00371608443",
     * "provinceId":"0037","provinceName":"山东省分公司","sid":"564361","siteName":"渠道支撑",
     * "uid":"mali3","uname":"mali3","userTruename":"马莉"}"
     *
     * @param pid
     * @param fieldName
     * @return
     */
    public static String getHeadValue(String pid, String fieldName) {
        if (StringUtils.isBlank(pid)) {
            return null;
        }
        try {
            Map map = JSON.parseObject(AES.decrypt(pid));
            return String.valueOf(map.get(fieldName));
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        String pid = "uid=changp3;sid=1000000;path=00,000;deptId=00100000200;wid=PSM1001;globalSign=ABC";
        String eid = AES.encrypt(pid);
        System.out.println(eid);
//        System.out.println("es:" + es);
//        String ds = AES.decrypt(es);
//        System.out.println("ds:" + ds);
//        System.out.println("uid:" + getUserId(eid));
//        System.out.println("sid:" + getSiteId(eid));
//        System.out.println("deptId:" + getDeptId(eid));
//        System.out.println("path:" + getPath(eid));
//        System.out.println("wid:" + getWid(eid));
//        System.out.println("gs:" + getGlobalSign(eid));
    }
}
