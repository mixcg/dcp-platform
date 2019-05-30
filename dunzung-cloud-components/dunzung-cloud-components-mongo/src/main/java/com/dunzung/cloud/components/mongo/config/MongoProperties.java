package com.dunzung.cloud.components.mongo.config;

import com.mongodb.ServerAddress;
import lombok.Data;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by duanzj on 2018/10/23.
 */
@Data
public class MongoProperties {

    private String urls;

    private String alias;

    private String userid;

    private String password;

    private String db;

    private int port;

    private Integer connPerHost;

    private Integer socketTimeout;

    private Boolean sockKeepLive;

    private Integer connTimeout;

    private Integer maxWaitTime;

    private String verificationDb;

    private String verification;

    public List<ServerAddress> getAddressList() throws UnknownHostException {
        String[] adds = urls.split(",");
        List<ServerAddress> addList = new ArrayList<ServerAddress>(adds.length);
        for (String add : adds) {
            addList.add(new ServerAddress(add, port));
        }
        return addList;
    }
}
