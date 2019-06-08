package com.dunzung.cloud.framework.mvc.pagination;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class PageParam {

    //当前页码
    @JsonIgnore
    private int page;

    //每页条数
    @JsonIgnore
    private int limit;

}
