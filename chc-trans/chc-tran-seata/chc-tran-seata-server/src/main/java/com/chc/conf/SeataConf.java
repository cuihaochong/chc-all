package com.chc.conf;

import com.alibaba.druid.pool.DruidDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Description:
 *
 * @author cuihaochong
 * @date 2019/12/14
 */
@Configuration
public class SeataConf {
//    @Value("${tx-seata.applicationId}")
//    private String applicationId;
//    @Value("${tx-seata.txServiceGroup}")
//    private String txServiceGroup;
//
//    @Bean
//    public GlobalTransactionScanner globalTransactionScanner() {
//        return new GlobalTransactionScanner(applicationId, txServiceGroup);
//    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    @Bean
    public DataSourceProxy dataSourceProxy(DataSource dataSource) {
        return new DataSourceProxy(dataSource);
    }

    @Bean
    public JdbcTemplate JdbcTemplate(@Qualifier("dataSourceProxy") DataSourceProxy ds) {
        return new JdbcTemplate(ds);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean(DataSourceProxy dataSourceProxy) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSourceProxy);
//        sqlSessionFactoryBean.setTypeAliasesPackage("com.chc.pojo.entity");
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
            .getResources("classpath*:mapper/**/*Mapper.xml"));
        sqlSessionFactoryBean.setTransactionFactory(new JdbcTransactionFactory());
        return sqlSessionFactoryBean.getObject();
    }

}
