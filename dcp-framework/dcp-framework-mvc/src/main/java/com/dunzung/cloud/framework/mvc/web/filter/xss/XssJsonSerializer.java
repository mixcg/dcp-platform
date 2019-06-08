package com.dunzung.cloud.framework.mvc.web.filter.xss;

import com.dunzung.cloud.framework.mvc.utils.XssUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

/**
 * Created by duanzj on 2018/12/16.
 */
public class XssJsonSerializer extends JsonSerializer<String> {

    @Override
    public Class<String> handledType() {
        return String.class;
    }

    @Override
    public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String portUrl=req.getServerPort()+ req.getRequestURI();
        if (StringUtils.isNotBlank(value) && !XssUtils.hasExceptional(portUrl)) {
            value = StringEscapeUtils.escapeHtml4(value);
        }
        jsonGenerator.writeString(value);
    }

}
