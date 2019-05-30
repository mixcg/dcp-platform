package com.dunzung.cloud.framework.event;

import lombok.Data;

/**
 * Created by syd on 2018/12/1.
 */
@Data
public class BaiduEvent extends BaseEvent {
    /**
     * 应用id
     */
    private String appId;
    /**
     * 任务id
     */
    private String jobId;
    /**
     * 推送数据类型(文章，应用)
     */
    private String type;
    /**
     * 推送数据
     */
    private String content;

    public BaiduEvent() {

    }

    public BaiduEvent(String action, String appId, String jobId, String type, String content) {
        this.setAction(action);
        this.setAppId(appId);
        this.setJobId(jobId);
        this.setType(type);
        this.setContent(content);
    }
}
