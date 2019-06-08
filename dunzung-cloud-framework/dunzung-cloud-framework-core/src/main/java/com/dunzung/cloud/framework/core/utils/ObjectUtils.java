package com.dunzung.cloud.framework.core.utils;

import java.io.*;

/**
 * Created by Wooola on 2018/10/16.
 */
public final class ObjectUtils {

    public static byte[] serialize(Object obj) {
        ObjectOutputStream obi = null;
        ByteArrayOutputStream bai = null;
        try {
            bai = new ByteArrayOutputStream();
            obi = new ObjectOutputStream(bai);
            obi.writeObject(obj);
            return bai.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != obi) {
                    obi.close();
                }
                if (null != bai) {
                    bai.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Object deserialize(byte[] byt) {
        ObjectInputStream oii = null;
        ByteArrayInputStream bis = null;
        try {
            bis = new ByteArrayInputStream(byt);
            oii = new ObjectInputStream(bis);
            Object obj = oii.readObject();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != bis) {
                    bis.close();
                }
                if (null != oii) {
                    oii.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
