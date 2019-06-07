package com.dunzung.cloud.sso.common;

public final class Const {

    public enum Status {
        LOCK(1), UNLOCK(0);

        private int status;
        Status(int status){
            this.status = status;
        }

        public static Status valueOf(int value){
            switch (value){
                case 0:
                    return UNLOCK;
                case 1:
                    return LOCK;
                default:
                    return null;
            }
        }
    }

    public static final class PwdTip {
        public static final String VALID_LENGTH = "密码长度应在min至max之间";
        public static final String VALID_CONTENT_AZ = "密码至少包含A-Z中任一大写字母";
        public static final String VALID_CONTENT_az = "密码至少包含a-z中任一小写字母";
        public static final String VALID_CONTENT_09 = "密码至少包含0-9中任一数字";
        public static final String VALID_CONTENT_SPECIAL_CHAR = "密码至少包含special_char任一字符";
        public static final String VALID_NO_USERNAME = "密码不能包含用户名";
        public static final String VALID_NO_HIS_NUMBS = "密码不能与前n次重复";
    }

    public static final class PwdRule {

        public static final String FIELD_MIN_LENGTH = "min_length";
        public static final String FIELD_MAX_LENGTH = "max_length";
        public static final String FIELD_CONTENT_AZ = "content_AZ";
        public static final String FIELD_CONTENT_az = "content_az";
        public static final String FIELD_CONTENT_09 = "content_09";
        public static final String FIELD_CONTENT_SPECIAL_CHAR = "content_special_char";
        public static final String FIELD_NOT_USERNAME = "no_username";
        public static final String FIELD_NO_HIS_NUMBS = "no_his_numbs";
        public static final String FIELD_PASSWORD = "password";

        public static final String RULE_CONTENT_AZ = ".*[A-Z]+.*";
        public static final String RULE_CONTENT_az = ".*[a-z]+.*";
        public static final String RULE_CONTENT_09 = ".*[0-9]+.*";

        public static final String RULE_LENGTH = "^.{min,max}$";
        public static final String RULE_CONTENT_SPECIAL_CHAR = ".*[special_char]+.*";
        public static final String RULE_NO_USERNAME = "^((?!username).)*$";
        public static final String RULE_NO_HISNUMBS = "^((?!pwd).)*$";
    }


}
