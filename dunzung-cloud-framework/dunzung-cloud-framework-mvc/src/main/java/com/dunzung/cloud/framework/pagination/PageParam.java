package com.dunzung.cloud.framework.pagination;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 *
 * @author unicom cloud
 * @technology +QQ： 117721385
 * @date 2017-03-14 23:15
 */
public class PageParam extends LinkedHashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    //当前页码
    private int page;

    //每页条数
    private int limit;

    public PageParam(Map<String, Object> params) {
        this.putAll(params);
        this.page = Integer.parseInt(params.get("page").toString());
        this.limit = Integer.parseInt(params.get("limit").toString());
        this.put("offset", (page - 1) * limit);
        this.put("page", page);
        this.put("limit", limit);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
