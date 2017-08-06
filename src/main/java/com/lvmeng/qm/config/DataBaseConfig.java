package com.lvmeng.qm.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.lvmeng.qm.base.datasource.ReadWriteDataSource;
import com.lvmeng.qm.base.datasource.ReadWriteDataSourceProcessor;






@Configuration
//@PropertySource("classpath:datasource.properties")
public class DataBaseConfig {
	@Value("${spring.datasource.type}")
	private Class<? extends DataSource> dataSourceType;

	@Bean(name = "writeDataSource", destroyMethod = "close", initMethod = "init")
	@Primary
	@ConfigurationProperties(prefix = "spring.master", ignoreUnknownFields = false)
	public DataSource writeDataSource() {
		return DataSourceBuilder.create().type(dataSourceType).build();
	}

	/**
	 * 有多少个从库就要配置多少�??
	 * 
	 * @return
	 */
	@Bean(name = "readDataSource1")
	@ConfigurationProperties(prefix = "spring.slave1", ignoreUnknownFields = false)
	public DataSource readDataSourceOne() {
		return DataSourceBuilder.create().type(dataSourceType).build();
	}

	//@Bean(name = "readDataSource2")
	@ConfigurationProperties(prefix = "spring.slave2", ignoreUnknownFields = false)
	public DataSource readDataSourceTwo() {
		return DataSourceBuilder.create().type(dataSourceType).build();
	}

	@Bean("readWriteDataSource")
	public ReadWriteDataSource readWriteDataSource() {
		ReadWriteDataSource dataSources = new ReadWriteDataSource();
		Map<String, DataSource> readDataSourceMap = new HashMap<>();
		readDataSourceMap.put("readDataSource1", readDataSourceOne());
		readDataSourceMap.put("readDataSource2", readDataSourceTwo());
		dataSources.setWriteDataSource(writeDataSource());
		dataSources.setReadDataSourceMap(readDataSourceMap);
		return dataSources;
	}

	@Bean(name = "readWriteDataSourceProcessor")
	public ReadWriteDataSourceProcessor readWriteDataSourceProcessor() {
		ReadWriteDataSourceProcessor readWriteDataSourceProcessor = new ReadWriteDataSourceProcessor();
		readWriteDataSourceProcessor.setForceChoiceReadWhenWrite(false);
		return readWriteDataSourceProcessor;
	}

	@Bean(name = "txManager")
	public DataSourceTransactionManager transactionManagers() {
		return new DataSourceTransactionManager(readWriteDataSource());
	}
}