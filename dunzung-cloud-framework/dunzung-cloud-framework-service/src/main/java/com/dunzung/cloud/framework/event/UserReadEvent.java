package com.dunzung.cloud.framework.event;

import lombok.Data;

/**
 * Created by duanzj on 2018/12/1.
 */
@Data
public class UserReadEvent extends ArticleEvent {

    private String userId;

    private String siteId;

    public UserReadEvent() {
    }

    public UserReadEvent(String action, String userId, String siteId, String articleId) {
        this.setUserId(userId);
        this.setSiteId(siteId);
        this.setAction(action);
        this.setArticleId(articleId);
    }

}
