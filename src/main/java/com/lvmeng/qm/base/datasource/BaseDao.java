package com.lvmeng.qm.base.datasource;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;

public class BaseDao {
	@Resource
	protected JdbcTemplate jdbcTemplate;
}
