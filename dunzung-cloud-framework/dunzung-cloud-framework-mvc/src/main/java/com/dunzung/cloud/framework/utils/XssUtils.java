package com.dunzung.cloud.framework.utils;

import java.util.HashSet;
import java.util.Set;

public class XssUtils {
    public volatile static Set<String> EXCEPTIONALS = new HashSet<>();

    static {
        EXCEPTIONALS.add("/mypanel/v1/");
        EXCEPTIONALS.add("/selfpanel/v1/mysetup/");
        EXCEPTIONALS.add("/recommend/v1");
        EXCEPTIONALS.add("/fullsearch/v1");
        EXCEPTIONALS.add("/dicts/v1/dictnoes/items");
        EXCEPTIONALS.add("/appConfig/v1/");
        EXCEPTIONALS.add("/appCenter/v1/");
        EXCEPTIONALS.add("/article");
        EXCEPTIONALS.add("/site/news/v1/");
        EXCEPTIONALS.add("/tempflow");
        EXCEPTIONALS.add("/msarticle/v1");
        EXCEPTIONALS.add("/news");
        EXCEPTIONALS.add("/ejectPic/ejectpic");
        EXCEPTIONALS.add("/sys/role/info/");
    }

    /**
     * 过滤例外地址
     * <p>
     * add by duanzj
     *
     * @param requestUri
     * @return
     */
    public static boolean hasExceptional(String requestUri) {
        for (String url : EXCEPTIONALS) {
            if (requestUri.contains(url)) {
                return true;
            }
        }
        return false;
    }
}