package com.chc.util.validation;

import com.chc.conf.exception.ParamException;
import com.chc.util.str.StringUtil;
import org.springframework.stereotype.Component;

import javax.validation.*;
import java.util.*;

/**
 * Description: 手动校验参数工具类
 *
 * @author cuihaochong
 * @date 2020/3/6
 */
@Component
public class ValidUtil {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 校验List集合,泛型为T,propertyName不为空时仅校验该属性
     *
     * @param list         校验List
     * @param propertyName 校验类属性名
     * @param <T>          校验类泛型
     */
    public static <T> void validList(List<T> list, String propertyName, Class<?>... groups) {
        list.forEach(object -> valid(object, propertyName, groups));
    }

    /**
     * 校验实体类,类型为T,propertyName不为空时仅校验该属性
     *
     * @param object       校验类
     * @param propertyName 校验类属性名
     * @param <T>          校验类泛型
     */
    public static <T> void valid(T object, String propertyName, Class<?>... groups) {
        Set<ConstraintViolation<T>> set;
        if (StringUtil.isBlank(propertyName)) {
            set = validator.validate(object, groups);
        } else {
            set = validator.validateProperty(object, propertyName, groups);
        }
        Optional<String> msg = set.stream()
            .findFirst()
            .map(ConstraintViolation::getMessage);
        if (msg.isPresent()) {
            throw new ParamException(msg.get());
        }
    }

}
