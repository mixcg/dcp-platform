package com.dunzung.cloud.framework.mvc.web.filter.xss;

import com.dunzung.cloud.framework.mvc.utils.XssUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by duanzj on 2018/10/11.
 */
@Component
public class UrlsFilterRunnar {
    protected static Logger logger = LoggerFactory.getLogger(UrlsFilterRunnar.class);

//    @Autowired
//    private RedisClient redisClient;

    @Scheduled(fixedDelay = 1000 * 60 * 5)
    public void execute() {
        //XssUtils.EXCEPTIONALS = redisClient.smembers(Const.URLS_FILTER);
        logger.info("XssUtils.EXCEPTIONALS:{}", XssUtils.EXCEPTIONALS);
    }

}
