package com.chc.conf;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import javax.transaction.UserTransaction;
import java.util.Properties;

@Configuration
public class XaDataSourceConfig {
	@Autowired
	private Environment env;

	@Bean(name = "tran1DataSource")
	@Primary
	public DataSource jamesDataSource(Environment env) {
		AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
		ds.setXaDataSourceClassName("com.alibaba.druid.pool.xa.DruidXADataSource");
		ds.setUniqueResourceName("tran1");
		ds.setPoolSize(5);
		ds.setXaProperties(build("spring.datasource.druid.tran1."));
		return ds;
	}

	@Bean(name = "tran2DataSource")
	public DataSource peterDataSource(Environment env) {
		AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
		Properties prop = build("spring.datasource.druid.tran2.");
		ds.setXaDataSourceClassName("com.alibaba.druid.pool.xa.DruidXADataSource");
		ds.setUniqueResourceName("tran2");
		ds.setPoolSize(5);
		ds.setXaProperties(prop);

		return ds;
	}

	@Bean("tran1JdbcTemplate")
	public JdbcTemplate jamesJdbcTemplate(@Qualifier("tran1DataSource") DataSource ds) {
		return new JdbcTemplate(ds);
	}

	@Bean("tran2JdbcTemplate")
	public JdbcTemplate peterJdbcTemplate(@Qualifier("tran2DataSource") DataSource ds) {
		return new JdbcTemplate(ds);
	}

	@Bean
	public JtaTransactionManager regTransactionManager () {
		UserTransactionManager userTransactionManager = new UserTransactionManager();
		UserTransaction userTransaction = new UserTransactionImp();
		return new JtaTransactionManager(userTransaction, userTransactionManager);
	}

	private Properties build(String prefix) {
		Properties prop = new Properties();
		prop.put("url", env.getProperty(prefix + "url"));
		prop.put("username", env.getProperty(prefix + "username"));
		prop.put("password", env.getProperty(prefix + "password"));

		return prop;
	}

}
