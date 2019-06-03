package com.dunzung.cloud.components.mongo.service.impl;

import com.dunzung.cloud.components.mongo.service.MongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by duanzj on 2018/9/10.
 */
public class MongoServiceImpl implements MongoService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public MongoTemplate getTemplate() {
        return mongoTemplate;
    }
}
