package com.unicom.portal.components.kafka.dto;

import lombok.Data;

/**
 * Created by Wooola on 2018/10/30.
 */
@Data
public class IntegralDTO {

    /**
     * 用户ID（用户表）
     */
    private String userId;

    /**
     * 动作ID（字典表）
     */
    private Long actionId;

    /**
     * 功能ID（字典表）
     */
    private Long functionId;

    /**
     * 文章、评论、空间、回复的所属人名称
     */
    private String userName;

    /**
     * 文章、评论、空间、回复的所属人Id
     */
    private String ownerId;

    /**
     * 评论Id
     */
    private Long commentId;

    /**
     * 文章或者问题等具体名称
     */
    private String detailsName;

    private String wid;

}
