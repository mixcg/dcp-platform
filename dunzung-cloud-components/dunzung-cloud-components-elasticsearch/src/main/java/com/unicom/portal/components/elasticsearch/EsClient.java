package com.unicom.portal.components.elasticsearch;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by duanzj on 2018/11/12.
 */
@Component
public class EsClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(EsClient.class);

    @Autowired
    private TransportClient transportClient;

    private static TransportClient client;

    @PostConstruct
    public void init() {
        client = this.transportClient;
    }

    public static TransportClient getInstance() {
        return client;
    }

    //字符串分隔符
    private static final String SEPARATE = ",";

    /**
     * 创建索引
     *
     * @param index
     * @return
     */
    public static boolean createIndex(String index) {
        if (!isIndexExist(index)) {
            LOGGER.info("Index is not exits!");
        }
        CreateIndexResponse indexresponse = client.admin().indices().prepareCreate(index).execute().actionGet();
        LOGGER.info("执行建立成功？" + indexresponse.isAcknowledged());
        return indexresponse.isAcknowledged();
    }

    /**
     * 删除索引
     *
     * @param index
     */
    public static boolean deleteIndex(String index) {
        if (!isIndexExist(index)) {
            LOGGER.info("Index is not exits!");
        }
        DeleteIndexResponse dResponse = client.admin().indices().prepareDelete(index).execute().actionGet();
        if (dResponse.isAcknowledged()) {
            LOGGER.info("delete index " + index + "  successfully!");
        } else {
            LOGGER.info("Fail to delete index " + index);
        }
        return dResponse.isAcknowledged();
    }

    /**
     * 判断索引是否存在
     *
     * @param index
     * @return
     */
    public static boolean isIndexExist(String index) {
        IndicesExistsResponse inExistsResponse = client.admin().indices().exists(new IndicesExistsRequest(index)).actionGet();
        if (inExistsResponse.isExists()) {
            LOGGER.info("Index [" + index + "] is exist!");
        } else {
            LOGGER.info("Index [" + index + "] is not exist!");
        }
        return inExistsResponse.isExists();
    }

    /**
     * 数据添加，正定ID
     *
     * @param jsonObject 要增加的数据
     * @param index      索引，类似数据库
     * @param type       类型，类似表
     * @param id         数据ID
     * @return
     */
    public static String addData(JSONObject jsonObject, String index, String type, String id) {
        IndexResponse response = client.prepareIndex(index, type, id).setSource(jsonObject).get();
        LOGGER.info("addData response status:{},id:{}", response.status().getStatus(), response.getId());
        return response.getId();
    }

    /**
     * 数据添加，正定ID
     *
     * @param jsonStr 要增加的数据
     * @param index   索引，类似数据库
     * @param type    类型，类似表
     * @return
     */
    public static String addDataInJSON(String jsonStr, String index, String type, String id) {
        return addDataInJSON(jsonStr, index, type, id, XContentType.JSON);
    }

    /**
     * 数据添加，正定ID
     *
     * @param jsonStr 要增加的数据
     * @param index   索引，类似数据库
     * @param type    类型，类似表
     * @return
     */
    public static String addDataInJSON(String jsonStr, String index, String type) {
        return addDataInJSON(jsonStr, index, type, null, XContentType.JSON);
    }

    /**
     * 数据添加，正定ID
     *
     * @param jsonStr 要增加的数据
     * @param index   索引，类似数据库
     * @param type    类型，类似表
     * @return
     */
    public static String addDataInJSON(String jsonStr, String index, String type, String id, XContentType contentType) {
        if (null == contentType) {
            contentType = XContentType.JSON;
        }
        IndexResponse response;
        if (StringUtils.isEmpty(id)) {
            response = client.prepareIndex(index, type).setSource(jsonStr, contentType).get();
        } else {
            response = client.prepareIndex(index, type, id).setSource(jsonStr, contentType).get();
        }
        LOGGER.info("addData response status:{},id:{}", response.status().getStatus(), response.getId());
        return response.getId();
    }

    /**
     * 通过ID删除数据
     *
     * @param index 索引，类似数据库
     * @param type  类型，类似表
     * @param id    数据ID
     */
    public static void deleteById(String index, String type, String id) {
        DeleteResponse response = client.prepareDelete(index, type, id).execute().actionGet();
        LOGGER.info("deleteDataById response status:{},id:{}", response.status().getStatus(), response.getId());
    }

    /**
     * 通过ID 更新数据
     *
     * @param jsonObject 要增加的数据
     * @param index      索引，类似数据库
     * @param type       类型，类似表
     * @param id         数据ID
     * @return
     */
    public static void updateById(JSONObject jsonObject, String index, String type, String id) {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(index).type(type).id(id).doc(jsonObject);
        client.update(updateRequest);
    }

    /**
     * 通过ID获取数据
     *
     * @param index  索引，类似数据库
     * @param type   类型，类似表
     * @param id     数据ID
     * @param fields 需要显示的字段，逗号分隔（缺省为全部字段）
     * @return
     */
    public static Map<String, Object> searchById(String index, String type, String id, String fields) {
        GetRequestBuilder getRequestBuilder = client.prepareGet(index, type, id);
        if (StringUtils.isNotEmpty(fields)) {
            getRequestBuilder.setFetchSource(fields.split(SEPARATE), null);
        }
        GetResponse getResponse = getRequestBuilder.execute().actionGet();
        return getResponse.getSource();
    }


    /**
     * 使用分词查询
     *
     * @param index    索引名称
     * @param type     类型名称,可传入多个type逗号分隔
     * @param fields   需要显示的字段，逗号分隔（缺省为全部字段）
     * @param matchStr 过滤条件（xxx=111,aaa=222）
     * @return
     */
    public static List<Map<String, Object>> searchForList(String index, String type, String fields, String matchStr) {
        return searchForList(index, type, 0, 0, null, fields, null, false, null, matchStr);
    }

    /**
     * 使用分词查询
     *
     * @param index       索引名称
     * @param type        类型名称,可传入多个type逗号分隔
     * @param fields      需要显示的字段，逗号分隔（缺省为全部字段）
     * @param sortField   排序字段
     * @param matchPhrase true 使用，短语精准匹配
     * @param matchStr    过滤条件（xxx=111,aaa=222）
     * @return
     */
    public static List<Map<String, Object>> searchForList(String index, String type, String fields, String sortField, boolean matchPhrase, String matchStr) {
        return searchForList(index, type, 0, 0, null, fields, sortField, matchPhrase, null, matchStr);
    }


    /**
     * 使用分词查询
     *
     * @param index          索引名称
     * @param type           类型名称,可传入多个type逗号分隔
     * @param size           文档大小限制
     * @param fields         需要显示的字段，逗号分隔（缺省为全部字段）
     * @param sortField      排序字段
     * @param matchPhrase    true 使用，短语精准匹配
     * @param highlightField 高亮字段
     * @param matchStr       过滤条件（xxx=111,aaa=222）
     * @return
     */
    public static List<Map<String, Object>> searchForList(String index, String type, Integer size, String fields, String sortField, boolean matchPhrase, String highlightField, String matchStr) {
        return searchForList(index, type, 0, 0, size, fields, sortField, matchPhrase, highlightField, matchStr);
    }


    /**
     * 使用分词查询
     *
     * @param index          索引名称
     * @param type           类型名称,可传入多个type逗号分隔
     * @param startTime      开始时间
     * @param endTime        结束时间
     * @param size           文档大小限制
     * @param fields         需要显示的字段，逗号分隔（缺省为全部字段）
     * @param sortField      排序字段
     * @param matchPhrase    true 使用，短语精准匹配
     * @param highlightField 高亮字段
     * @param matchStr       过滤条件（xxx=111,aaa=222）
     * @return
     */
    public static List<Map<String, Object>> searchForList(String index, String type, long startTime, long endTime, Integer size, String fields, String sortField, boolean matchPhrase, String highlightField, String matchStr) {
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index);
        if (StringUtils.isNotEmpty(type)) {
            searchRequestBuilder.setTypes(type.split(SEPARATE));
        }
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        /*
         *  2019-4-29 liwang修改
        */
        if (startTime > 0 || endTime > 0) {
            //只填写了开始时间,默认当天结束时候
            if (startTime > 0 && endTime <= 0) {
                endTime = getDefaultEndTime();
            }
            //只填写了结束时间,默认是java最早时间
            if (startTime <= 0 && endTime > 0) {
                startTime = getDefaultStartTime();
            }
            boolQuery.must(QueryBuilders.rangeQuery("processTime")
                    .format("epoch_millis")
                    .from(startTime)
                    .to(endTime)
                    .includeLower(true)
                    .includeUpper(true));
        }

        //搜索的的字段
        if (StringUtils.isNotEmpty(matchStr)) {
            for (String s : matchStr.split(SEPARATE)) {
                String[] ss = s.split("=");
                if (ss.length > 1) {
                    if (matchPhrase == Boolean.TRUE) {
                        boolQuery.must(QueryBuilders.matchPhraseQuery(s.split("=")[0], s.split("=")[1]));
                    } else {
                        boolQuery.must(QueryBuilders.matchQuery(s.split("=")[0], s.split("=")[1]));
                    }
                }
            }
        }

        // 高亮（xxx=111,aaa=222）
        if (StringUtils.isNotEmpty(highlightField)) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            //highlightBuilder.preTags("<span style='color:red' >");//设置前缀
            //highlightBuilder.postTags("</span>");//设置后缀
            // 设置高亮字段
            highlightBuilder.field(highlightField);
            searchRequestBuilder.highlighter(highlightBuilder);
        }

        searchRequestBuilder.setQuery(boolQuery);

        if (StringUtils.isNotEmpty(fields)) {
            searchRequestBuilder.setFetchSource(fields.split(SEPARATE), null);
        }
        searchRequestBuilder.setFetchSource(true);

        if (StringUtils.isNotEmpty(sortField)) {
            searchRequestBuilder.addSort(sortField, SortOrder.DESC);
        }

        if (size != null && size > 0) {
            searchRequestBuilder.setSize(size);
        }

        //打印的内容 可以在 Elasticsearch head 和 Kibana  上执行查询
        LOGGER.info("\n{}", searchRequestBuilder);

        SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();

        long totalHits = searchResponse.getHits().totalHits;
        long length = searchResponse.getHits().getHits().length;

        LOGGER.info("共查询到[{}]条数据,处理数据条数[{}]", totalHits, length);

        if (searchResponse.status().getStatus() == 200) {
            return setSearchResponse(searchResponse, highlightField);
        }

        return null;
    }

    /**
     * 使用分词查询,并分页
     *
     * @param index          索引名称
     * @param type           类型名称,可传入多个type逗号分隔
     * @param currentPage    当前页
     * @param pageSize       每页显示条数
     * @param startTime      开始时间
     * @param endTime        结束时间
     * @param fields         需要显示的字段，逗号分隔（缺省为全部字段）
     * @param sortField      排序字段
     * @param sortDirection  排序方向 asc 或者desc
     * @param matchPhrase    true 使用，短语精准匹配
     * @param highlightField 高亮字段
     * @param matchStr       过滤条件（xxx=111,aaa=222）
     * @return
     */
    public static EsPagination searchForPage(String index, String type, int currentPage, int pageSize, String timeField, long startTime, long endTime, String fields, String sortField, String sortDirection, boolean matchPhrase, String highlightField, String matchStr, String blurField, String blurValue) {
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index);
        if (StringUtils.isNotEmpty(type)) {
            searchRequestBuilder.setTypes(type.split(SEPARATE));
        }
        searchRequestBuilder.setSearchType(SearchType.QUERY_THEN_FETCH);

        //排序字段
        if (StringUtils.isNotEmpty(sortField)) {
            if ("asc".equals(sortDirection))
                searchRequestBuilder.addSort(sortField + ".keyword", SortOrder.ASC);
            else if ("desc".equals(sortDirection))
                searchRequestBuilder.addSort(sortField + ".keyword", SortOrder.DESC);
        }

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        //根据标题模糊搜索
        if (StringUtils.isNotEmpty(blurValue)) {
            boolQuery.must(QueryBuilders.wildcardQuery(blurField + ".keyword", "*" + blurValue + "*"));
        }
        /*
         *  2019-4-29 liwang修改
        */
        if (startTime > 0 || endTime > 0) {
            //高级搜索只填写了开始时间,默认当天结束时候
            if (startTime > 0 && endTime <= 0) {
                endTime = getDefaultStartTime();
            }
            //高级搜索只填写了结束时间,默认是java最早时间
            if (startTime <= 0 && endTime > 0) {
                startTime = getDefaultEndTime();
            }
            boolQuery.must(QueryBuilders.rangeQuery(timeField)
                    .from(startTime)
                    .to(endTime));
        }

        // 查询字段
        if (StringUtils.isNotEmpty(matchStr)) {
            for (String s : matchStr.split(SEPARATE)) {
                if (matchPhrase == Boolean.TRUE) {
                    boolQuery.must(QueryBuilders.matchPhraseQuery(s.split("=")[0]+ ".keyword", s.split("=")[1]));
                } else {
                    boolQuery.must(QueryBuilders.matchQuery(s.split("=")[0]+ ".keyword", s.split("=")[1]));
                }
            }
        }

        searchRequestBuilder.setQuery(boolQuery);

        // 分页应用
        searchRequestBuilder.setFrom((currentPage - 1) * pageSize).setSize(pageSize);
        // 执行搜索,返回搜索响应信息
        SearchResponse searchResponseAll = searchRequestBuilder.execute().actionGet();

        long totalHits = searchResponseAll.getHits().totalHits;
        long length = searchResponseAll.getHits().getHits().length;

        LOGGER.debug("共查询到[{}]条数据,处理数据条数[{}]", totalHits, length);

        if (searchResponseAll.status().getStatus() == 200) {
            List<Map<String, Object>> sourceList = setSearchResponse(searchResponseAll, null);
            return new EsPagination(currentPage, pageSize, (int) totalHits, sourceList);
        }
        return null;
    }

    /**
     * 高亮结果集 特殊处理
     *
     * @param searchResponse
     * @param highlightField
     */
    private static List<Map<String, Object>> setSearchResponse(SearchResponse searchResponse, String highlightField) {
        List<Map<String, Object>> sourceList = new ArrayList<>();
        StringBuffer stringBuffer = new StringBuffer();
        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
            searchHit.getSourceAsMap().put("id", searchHit.getId());
            if (StringUtils.isNotEmpty(highlightField)) {
                System.out.println("遍历 高亮结果集，覆盖 正常结果集" + searchHit.getSourceAsMap());
                Text[] text = searchHit.getHighlightFields().get(highlightField).getFragments();
                if (text != null) {
                    for (Text str : text) {
                        stringBuffer.append(str.string());
                    }
                    //遍历 高亮结果集，覆盖 正常结果集
                    searchHit.getSourceAsMap().put(highlightField, stringBuffer.toString());
                }
            }
            sourceList.add(searchHit.getSourceAsMap());
        }
        return sourceList;
    }


    /**
     * top10方法
     *
     * @param index    索引
     * @param type     相当于表
     * @param queryMap 查询条件
     * @param sortList 排序
     * @param size     top数大小
     * @return
     */
    public static List<Map<String, Object>> setSearchTopN(String index, String type, Map<String, Object> queryMap, List<String> sortList, Integer size) {
        SearchRequestBuilder responsebuilder = client.prepareSearch(index).setTypes(type);
        for (String sortStr : sortList) {
            responsebuilder.addSort(sortStr, SortOrder.DESC);
        }
        QueryBuilder qb = QueryBuilders.boolQuery();
        if (!CollectionUtils.isEmpty(queryMap)) {
            for (String key : queryMap.keySet()) {
                MatchPhraseQueryBuilder mpq1 = QueryBuilders.matchPhraseQuery(key, queryMap.get(key).toString());
                ((BoolQueryBuilder) qb).must(mpq1);
            }
        }
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(qb);
        SearchResponse myresponse = responsebuilder.setFrom(0).setQuery(qb).setSize(size).setExplain(true).execute().actionGet();
        SearchHits hits = myresponse.getHits();
        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        for (SearchHit hit : hits) {
            listMap.add(hit.getSourceAsMap());
        }
        return listMap;
    }

    private static long getDefaultStartTime() {
        LocalDateTime todayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        return Long.valueOf(todayEnd.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
    }

    private static long getDefaultEndTime() {
        Instant instant = new Date(0).toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime defaultend = instant.atZone(zoneId).toLocalDateTime();
        return Long.valueOf(defaultend.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
    }
}

