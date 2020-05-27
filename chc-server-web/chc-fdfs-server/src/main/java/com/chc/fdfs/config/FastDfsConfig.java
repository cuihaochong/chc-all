package com.chc.fdfs.config;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.context.annotation.*;
import org.springframework.jmx.support.RegistrationPolicy;

/**
 * Description: FastDfs配置文件
 *
 * @author CuiHaochong
 * @date 2019/8/8
 */
@Configuration
@Import(FdfsClientConfig.class)
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class FastDfsConfig {
}
