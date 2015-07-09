package com.tony.logic.db;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tony.core.db.AbstractDbService;

/**
 * 业务定制的数据库连接对象
 * @author jahe.me
 *
 */
@Repository
public class AppDbRepository extends AbstractDbService{
	
//	初始化jdbc
	@Resource(name="dataSource")
	public void setDataSource(DataSource dataSource) {
		jdbc = new JdbcTemplate(dataSource);
	}

}
