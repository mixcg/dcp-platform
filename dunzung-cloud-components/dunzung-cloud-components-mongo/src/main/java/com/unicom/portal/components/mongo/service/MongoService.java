package com.unicom.portal.components.mongo.service;

import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Created by daunzj on 2018/9/10.
 */
public interface MongoService {

    MongoTemplate getTemplate();

}
