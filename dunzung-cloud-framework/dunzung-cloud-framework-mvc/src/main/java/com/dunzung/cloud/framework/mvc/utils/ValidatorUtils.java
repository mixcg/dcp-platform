package com.dunzung.cloud.framework.mvc.utils;

import com.dunzung.cloud.framework.core.exception.PortalException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * hibernate-validator校验工具类
 * <p>
 * 参考文档：http://docs.jboss.org/hibernate/validator/5.4/reference/en-US/html_single/
 *
 * @author unicom cloud
 * @technology +QQ： 117721385
 * @date 2017-03-15 10:50
 */
public class ValidatorUtils {
    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 校验对象
     *
     * @param object 待校验对象
     * @param groups 待校验的组
     * @throws PortalException 校验不通过，则报RRException异常
     */
    public static void validateEntity(Object object, Class<?>... groups) throws PortalException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            StringBuilder msg = new StringBuilder();
            for (ConstraintViolation<Object> constraint : constraintViolations) {
                msg.append(constraint.getMessage()).append("<br>");
            }
            throw new PortalException(msg.toString());
        }
    }
}