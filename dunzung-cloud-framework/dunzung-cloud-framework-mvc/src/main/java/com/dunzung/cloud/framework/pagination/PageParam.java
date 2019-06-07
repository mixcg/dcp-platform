package com.dunzung.cloud.framework.pagination;

import lombok.Data;

@Data
public class PageParam {

    //当前页码
    private int page;

    //每页条数
    private int limit;

}
