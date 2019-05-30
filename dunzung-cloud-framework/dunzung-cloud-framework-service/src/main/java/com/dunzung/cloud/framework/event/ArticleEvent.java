package com.dunzung.cloud.framework.event;

import lombok.Data;

/**
 * Created by duanzj on 2018/12/1.
 */
@Data
public class ArticleEvent extends BaseEvent {

    /**
     * 文章id
     */
    private String articleId;

    /**
     * 是否置顶
     */
    private Boolean isTop;

    /**
     * 是否推荐
     */
    private Boolean recommend;

    public ArticleEvent() {

    }

    public ArticleEvent(String action, String articleId) {
        this.setAction(action);
        this.setArticleId(articleId);
    }

    public ArticleEvent(String action, String articleId, boolean isTop) {
        this.setAction(action);
        this.setArticleId(articleId);
        this.setIsTop(isTop);
    }

    public ArticleEvent(String action, boolean recommend,String articleId) {
        this.setAction(action);
        this.setArticleId(articleId);
        this.setRecommend(recommend);
    }

}
