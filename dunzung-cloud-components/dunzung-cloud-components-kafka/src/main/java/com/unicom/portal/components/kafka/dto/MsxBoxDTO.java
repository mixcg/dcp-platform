package com.unicom.portal.components.kafka.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

/**
 * Created by Wooola on 2018/10/12.
 */
@Data
public class MsxBoxDTO {

    private String userId;
    /**
     * 文章或是评论标题
     */
    private String title;
    /**
     * 内容可以为空
     */
    private String content;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date receiveDate;
    /**
     * 消息类型（0：文章:1：评论:2：访客）
     */
    private Integer msgType;
    /**
     * 消息子类型（0：浏览，1：点赞，2：收藏，3：评论）
     */
    private Integer msgSubType;
    /**
     * 消息来源类型 0:我收到的消息1：平台推送的消息
     */
    private Integer dataSources;
    /**
     * 发送平台（应用）
     */
    private String sendPlatform;
    /**
     * （点击查看）评论Url/文章Url
     */
    private String msgUrl;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTm;
    /**
     * 业务id（评论id/文章id）
     */
    private Long bizId;

    private String wid;

}
