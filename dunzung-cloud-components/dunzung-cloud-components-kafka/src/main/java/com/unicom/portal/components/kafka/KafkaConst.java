package com.unicom.portal.components.kafka;

/**
 * Created by duanzj on 2018/10/10.
 */
public final class KafkaConst {

    public static class Topics {

        /**
         * 平台消息TOPIC
         */
        public static final String PM_TOPIC = "portal-message-topic";

        /**
         * 积分统计TOPIC
         */
        public static final String PI_TOPIC = "portal-integral-event-topic";
//        /**
//         * 积分统计TOPIC
//         */
//        public static final String PIG_TOPIC = "portal-integral-topic";
        /**
         * 大屏展示数据统计TOPIC
         */
        public static final String DATAV_TOPIC = "portal-pdv-topic";

        /**
         * 短信发送TOPIC
         */
        public static final String SMS_TOPIC = "portal-sms-topic";

        /**
         * 用户用户已读
         */
        public static final String USEREAD_EVENT_TOPIC = "portal-useread-event-topic";

        /**
         * 文章更新TOPIC
         */
        public static final String ARTICLE_EVENT_TOPIC = "portal-article-event-topic";

        /**
         * 用户事件TOPIC
         */
        public static final String USER_EVENT_TOPIC = "portal-user-event-topic";

        /**
         * 自定义面板TOPIC
         */
        public static final String SELFPANEL_EVENT_TOPIC = "portal-selfpanel-event-topic";

        /**
         * 百度推送时间TOPIC
         */
        public static final String BAIDU_EVENT_TOPIC = "portal-baidu-event-topic";

        /**
         * 用户#应用#缓存初始化
         */
        public static final String USER_APP_INIT_TOPIC = "portal-userappinit-event-topic";
        /**
         * 用户登录记录TOPIC
         */
        public static final String USER_LOGIN_RECORD_TOPIC = "user_login_record_topic";

        /**
         * 文章待办
         */
        public static final String ARTICLE_PENDING_TOPIC = "article_pending_topic";
    }

    public static final String MSG_PLT_WP_CN = "智慧门户";
}
