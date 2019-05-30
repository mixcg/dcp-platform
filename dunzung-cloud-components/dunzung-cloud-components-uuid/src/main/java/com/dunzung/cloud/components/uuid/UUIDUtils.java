package com.dunzung.cloud.components.uuid;


/**
 * Created by duanzj on 2018/9/15.
 */
public final class UUIDUtils {

    public static String genUUID(String prefix) throws Exception {
        Long uuid = AIUUID.getInstance().nextId();
        return prefix + uuid;
    }

}
