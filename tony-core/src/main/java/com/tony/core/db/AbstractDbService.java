package com.tony.core.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 * 数据库连接服务抽象对象，各项目需要继承并初始化jdbc
 * @author jahe.me
 *
 */
public abstract class AbstractDbService {


    public Logger log = LoggerFactory.getLogger(getClass());

    public JdbcTemplate jdbc;

//	初始化jdbc
//	@Resource(name="dataSource")
//	public void setDataSource(DataSource dataSource) {
//		jdbc = new JdbcTemplate(dataSource);
//	}


    public <T> List<T> query(String sql, final RowMapper<T> rowMapper) {
        return jdbc.query(sql, rowMapper);
    }


    /**
     * 简单查询,PreparedStatement形式
     * @param <T>
     *
     * @param sql
     *            查询语句
     * @param rowMapper
     * @param values
     *            查询参数
     * @return
     * @throws Exception
     */
    public <T> List<T> query(String sql, final RowMapper<T> rowMapper, Object... values) {
        final List<T> list = new ArrayList<T>();
        jdbc.query(sql, values, new ResultSetExtractor<List<T>>() {
            public List<T> extractData(ResultSet rs) {
                try {
                    int i = 0;
                    while (rs.next()) {
                        list.add(rowMapper.mapRow(rs, i));
                        i++;
                    }
                } catch (SQLException e) {
                    log.error("RowMapper-query error", e);
                }
                return list;
            }
        });
        return list;
    }


    public <T> T queryT(String sql, final RowMapper<T> rowMapper, Object... values) {
        try {
            return jdbc.queryForObject(sql, rowMapper, values);
        } catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public <T> List<T> queryForList(String sql , Class<T> elementType , Object... args){
        try {
            return jdbc.queryForList(sql, elementType, args);
        } catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Map<String, Object>> queryList(String sql, Object... args) {
        try {
            return jdbc.queryForList(sql, args);
        } catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int queryInt(String sql, Object... value) {
        try {
            Integer i = jdbc.queryForObject(sql, value, Integer.class);
            return i == null ? 0 : i.intValue();
        } catch(EmptyResultDataAccessException e) {
            return 0;
        }
    }

    public Long queryLong(String sql, Object... value) {
        try {
            Long l = jdbc.queryForObject(sql, value, Long.class);
            return l==null ? 0 : l;
        } catch(EmptyResultDataAccessException e) {
            return 0L;
        }
    }


    public String queryString(String sql, Object... value) {
        try {
            return jdbc.queryForObject(sql, value, String.class);
        } catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * 执行update / insert 语句
     *
     * @param sql
     *            sql 语句
     * @param args
     *            参数数组
     * @return
     * @throws Exception
     */
    public DbResult update(String sql, Object... args) {
        try {
            int c = jdbc.update(sql, args);
            return DbResult.valueOf(c > 0 ? true : false);
        } catch (DataAccessException dae) {
            log.error("update-" + sql, dae);
            return DbResult.valueOf(false, dae.getMessage());
        }
    }
    

    /**
     * 批量执行update / insert 语句
     * @param sql
     * @param args
     * @return
     */
    public int updateBatch(String sql, List<Object[]> args){
    	 int[] counts = jdbc.batchUpdate(sql, args);
    	 if (null == counts) {
    		 return 0;
    	 }
    	 int c = 0;
    	 for (int i=0; i<counts.length; i++) {
    		 c += counts[i];
    	 }
    	 return c;
    }

    /**
     *  插入数据,返回自增id
     * @param sql
     * @param key 主键字段名
     * @param args	参数列表
     * @return -1-异常
     */
    public int insert(final String sql, final String key , final Object... args ){
        long result = -1L;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc  = new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql,new String[] { key });
                if ( args != null) {
                    for(int i=0; i<args.length;i++ ){
                        ps.setObject(i+1, args[i]);
                    }
                }
                return ps;
            }
        };
        result = jdbc.update(psc, keyHolder);
        return result>0?keyHolder.getKey().intValue():-1;
    }


    /**
     * 翻页计算
     * @param page 0-第一页 1-第二页
     * @param pagesize
     * @return
     */
    public int calStart(int page , int pagesize ) {
        if(page<0) {
            page=0;
        }
        return page*pagesize;
    }
}
