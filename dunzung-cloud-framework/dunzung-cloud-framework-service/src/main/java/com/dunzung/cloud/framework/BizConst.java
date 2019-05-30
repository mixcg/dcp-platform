package com.dunzung.cloud.framework;

/**
 * Created by duanzj on 2018/12/8.
 */
public class BizConst {

    /**
     * 主站点ID main_site_id
     */
    public static final String MWID = "WS593987800898998272";

    public static class STATUS {

        /**
         * 禁用
         */
        public static final int OFF = 0;

        /**
         * 启用
         */
        public static final int ON = 1;

    }

    public static class ACTION {

        /**
         * 添加
         */
        public static final String ADD = "add";

        /**
         * 修改
         */
        public static final String UPDATE = "update";

        /**
         * 删除
         */
        public static final String DEL = "del";

        /**
         * 启用
         */
        public static final String ON = "on";

        /**
         * 撤销
         */
        public static final String OFF = "off";

        public static final String USER_APP_INIT = "user#app#init";

    }

}
