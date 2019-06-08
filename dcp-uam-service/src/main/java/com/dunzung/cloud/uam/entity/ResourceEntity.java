package com.dunzung.cloud.uam.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Wooola on 2019/6/6.
 */
@Data
public class ResourceEntity {

    private String resId;

    /**
     * 资源名称
     */
    private String resName;

    /**
     * 资源路径
     */
    private String resPath;

    /**
     * 资源类型
     * 0：目录
     * 1：jsp文件
     * 2：表格（grid）
     * 3：按钮（button）
     */
    private String resType;

    /**
     * 父节点
     */
    private ResourceEntity parentResource;

    private List<String> roleIds = new ArrayList<>();

    private String creator;

    private Date createdTm;

    private String modifier;

    private Date modifiedTm;

}
