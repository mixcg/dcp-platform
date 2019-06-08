package com.dunzung.cloud.framework.mvc.web.filter;

import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wooola on 2018/9/13.
 */
public class DruidConfigFilter extends TypeExcludeFilter {

    private static List<String> excludes = new ArrayList<>();

    static {
        excludes.add("com.unicom.portal.framework.datasource.druid.DruidConfig");
    }

    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) {
        for (String excludeClass : excludes) {
            if (excludeClass.equals(metadataReader.getClassMetadata().getClassName())) {
                return true;
            }
        }
        return false;
    }

}
