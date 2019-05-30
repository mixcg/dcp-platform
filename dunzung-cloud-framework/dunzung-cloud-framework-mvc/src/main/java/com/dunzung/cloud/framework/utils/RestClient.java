package com.dunzung.cloud.framework.utils;

import org.apache.commons.collections.MapUtils;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by duanzj on 2018/11/10.
 */
@Component
@Lazy(false)
public class RestClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestClient.class);

    private static RestTemplate restTemplate;

    private RestClient() {

    }

    static {
        // 长连接保持30秒
        PoolingHttpClientConnectionManager pollingConnectionManager = new PoolingHttpClientConnectionManager(30, TimeUnit.SECONDS);
        // 总连接数
        pollingConnectionManager.setMaxTotal(1000);
        // 同路由的并发数
        pollingConnectionManager.setDefaultMaxPerRoute(1000);

        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        httpClientBuilder.setConnectionManager(pollingConnectionManager);
        // 重试次数，默认是3次，没有开启
        httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(2, true));
        // 保持长连接配置，需要在头添加Keep-Alive
        httpClientBuilder.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy());

        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.16 Safari/537.36"));
        headers.add(new BasicHeader("Accept-Encoding", "gzip,deflate"));
        headers.add(new BasicHeader("Accept-Language", "zh-CN"));
        headers.add(new BasicHeader("Connection", "Keep-Alive"));

        httpClientBuilder.setDefaultHeaders(headers);

        HttpClient httpClient = httpClientBuilder.build();

        // httpClient连接配置，底层是配置RequestConfig
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        // 连接超时
        clientHttpRequestFactory.setConnectTimeout(5000);
        // 数据读取超时时间，即SocketTimeout
        clientHttpRequestFactory.setReadTimeout(5000);
        // 连接不够用的等待时间，不宜过长，必须设置，比如连接不够用时，时间过长将是灾难性的
        clientHttpRequestFactory.setConnectionRequestTimeout(200);
        // 缓冲请求数据，默认值是true。通过POST或者PUT大量发送数据时，建议将此属性更改为false，以免耗尽内存。
        clientHttpRequestFactory.setBufferRequestBody(false);

        // 添加内容转换器
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        messageConverters.add(new FormHttpMessageConverter());
        messageConverters.add(new MappingJackson2XmlHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());

        restTemplate = new RestTemplate(messageConverters);
        restTemplate.setRequestFactory(clientHttpRequestFactory);
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());

        LOGGER.info("RestClient初始化完成");
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    /**
     * get请求
     *
     * @param url
     * @return
     */
    public static String getWithHeaders(String url, Map<String, String> headMap) {
        try {
            HttpHeaders headers = new HttpHeaders();
            if (MapUtils.isNotEmpty(headMap)) {
                headMap.keySet().stream().forEach(key -> headers.add(key, headMap.get(key)));
            }
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>
                    (headers), String.class);
            String responseStr = response.getBody();
            return responseStr;
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return null;
    }

    public String postWithHeaders(String url, Map<String, String> headers) {
        return postWithHeaders(url, null, headers);
    }

    public String postWithHeaders(String url, Map<String, Object> params, Map<String, String> headMap) {
        try {
            HttpHeaders headers = new HttpHeaders();
            if (MapUtils.isNotEmpty(headMap)) {
                headMap.keySet().stream().forEach(key -> headers.add(key, headMap.get(key)));
            }
            HttpEntity<Object> requestEntity = null;
            if (MapUtils.isEmpty(params)) {
                requestEntity = new HttpEntity<>(headers);
            } else {
                MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
                params.keySet().stream().forEach(key -> multiValueMap.add(key, MapUtils.getString(params, key, "")));
                requestEntity = new HttpEntity<>(multiValueMap, headers);
            }
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            String responseStr = response.getBody();
            System.out.println(responseStr);
            return responseStr;
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return null;
    }

    public static String postWithJson(String url, String json) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            HttpEntity<Object> requestEntity = new HttpEntity<>(json, headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            String returnValue = response.getBody();
            LOGGER.debug("responseStr:" + returnValue);
            return returnValue;
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return null;
    }

    public static String httpPostWithParam(String url, MultiValueMap<String, Object> postParameters) {
        return httpPostWithParam(url, postParameters, "application/x-www-form-urlencoded");
    }


    public static String httpPostWithParam(String url, MultiValueMap<String, Object> postParameters, String cntype) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", cntype);
        HttpEntity<MultiValueMap<String, Object>> r = new HttpEntity<>(postParameters, headers);
        String data = restTemplate.postForObject(url, r, String.class);
        return data;
    }

    public static String postWithForm(String url, String str) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<String> requestEntity = new HttpEntity<String>(str, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
            String returnValue = response.getBody();
            LOGGER.debug("responseStr:" + returnValue);
            return returnValue;
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return null;
    }

}
