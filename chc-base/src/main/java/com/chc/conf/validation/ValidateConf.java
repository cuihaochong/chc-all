package com.chc.conf.validation;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.*;

/**
 * Description: validation配置
 *
 * @author cuihaochong
 * @date 2020/3/3
 */
@Configuration
public class ValidateConf {

    /**
     * 校验模式：校验一个出错后，后面的不继续校验，默认为校验完所有
     */
    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
            .configure()
            .failFast(true)
            .buildValidatorFactory();
        return validatorFactory.getValidator();
    }
}
