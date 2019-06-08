package com.dunzung.cloud.uam.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author
 */
public final class Const {

    public static final String WS_PREFIX = "WS";

    // 不是 系统管理员 和 全国管理员 和省分管理员 和市级管理员
    public static final String NONE_MANAGER_ROLE = "-1";
    // 系统管理员
    public static final String SYSTEM_MANAGER_ROLE = "0";
    // 1,全国管理角色,2,省级管理角色,3,市级管理角色,4,部门管理角色
    public static final String QUANGUO_MANAGER_ROLE = "1";

    public static final String SHENGFEN_MANAGER_ROLE = "2";

    public static final String SHIJI_MANAGER_ROLE = "3";

    public static final String BUMEN_MANAGER_ROLE = "4";

    public static final String SITE_ROLE_NAME = "站点管理员";

    public static List<Long> SITE_MENUID_LIST = new ArrayList<Long>(Arrays.asList(1l, 3l, 19l, 20l, 21l, 22l, 4l, 23l, 24l, 25l, 26l, 42l, 43l, 44l, 45l, 46l, 47l,48l,49l,50l,51l,52l,68l,81l,85l,86l));

}
