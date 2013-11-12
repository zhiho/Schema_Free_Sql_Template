package com.irelandken.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.Assert;

/**
 * 
 * @author irelandKen
 * TODO: 重构 where_string
 */

public class SqlTemplate extends JdbcTemplate implements SqlOperate 
{
	public SqlTemplate(DataSource dataSource)
	{
		super(dataSource);
	}

	private static String link(String glue,Collection<String> strs) {
		
		StringBuilder builder = new StringBuilder();
		
		int len = strs.size();
		int cur = 0;
		for(String str : strs) {
			cur++;
			builder.append(str);
			if(cur < len) {
				builder.append(glue);
			}
		}
		
		return builder.toString();
	}
	
	private static String placeholders(int num) {
		
		if(num<=0) {
			return "";
		}
		
		StringBuilder builder = new StringBuilder(2*num-1);
		
		for(int i=0; i<num-1; i++) {
			builder.append("?,");
		}
		builder.append("?");
		
		return builder.toString();
	}
	
	@Override
	public Number insert(String table, final Map<String, Object> data)
	{
		
		final String sql = "INSERT INTO " + table + " (" + link(",",data.keySet()) + ") VALUES (" + placeholders(data.size()) + ")";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		super.update(
		    new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps =
		                connection.prepareStatement(sql, new String[] {"id"});
		            
		            (new ArgumentPreparedStatementSetter(data.values().toArray())).setValues(ps);
		            
		            return ps;
		        }
		    },
		    keyHolder
		);

		return keyHolder.getKey();
	}
	
	@Override
	public boolean insert2(String table, Map<String, Object> data)
	{
		String sql = "INSERT INTO " + table + " (" + link(",",data.keySet()) + ") VALUES (" + placeholders(data.size()) + ")";
		
		return super.update(sql, data.values().toArray()) >= 1;
	}

	@Override
	public List<Map<String, Object>> select(String table, Collection<String> fields, String where)
	{
		//SELECT field1,field2.. FROM table WHERE where; 
		
		String sql = null;
		if(fields == null || fields.isEmpty()) {
			sql  = "SELECT * FROM " + table;
		} else {
			sql  = "SELECT " + link(",",fields) + " FROM " + table;
		}
		
		if(where != null) {
			sql += " WHERE " + where;
		}
		
		return super.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> select(String table, Collection<String> fields, Map<String, Object> where)
	{
		//SELECT field1,field2.. FROM table WHERE key1 = ? AND key2 = ? ..;

		String sql = null;
		if(fields == null || fields.isEmpty()) {
			sql  = "SELECT * FROM " + table;
		} else {
			sql  = "SELECT " + link(",",fields) + " FROM " + table;
		}
		
		List<Object> args = null;
		
		if(where != null && !where.isEmpty()) {
			args = new ArrayList<Object>(where.size());
			List<String> condictions = new ArrayList<String>(where.size());
			
			for(Entry<String, Object> entry : where.entrySet()) {
				condictions.add(entry.getKey() + " = ? ");
				args.add(entry.getValue());
			}
			
			sql += " WHERE " + link(" AND ",condictions);
		}
		
		return super.queryForList(sql, args);
	}

	@Override
	public List<Map<String, Object>> select(String table, Collection<String> fields, String where, String orderBy, int start, int limit)
	{
		//SELECT field1,field2.. FROM table WHERE where ORDER BY orderBy LIMIT start,limit;
		
		String sql = null;
		if(fields == null || fields.isEmpty()) {
			sql  = "SELECT * FROM " + table;
		} else {
			sql  = "SELECT " + link(",",fields) + " FROM " + table;
		}
		
		if(where != null) {
			sql += " WHERE " + where;
		}
		
		if(orderBy != null) {
			sql += " ORDER BY " + orderBy;
		}
		
		if(start >=0 && limit > 0) {
			sql += " LIMIT " + start + "," + limit;
		}
		
		return super.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> select(String table, Collection<String> fields, Map<String, Object> where, String orderBy, int start, int limit)
	{
		//SELECT field1,field2.. FROM table WHERE key1 = ? AND key2 = ?.. ORDER BY orderBy LIMIT start,limit;

		String sql = null;
		if(fields == null || fields.isEmpty()) {
			sql  = "SELECT * FROM " + table;
		} else {
			sql  = "SELECT " + link(",",fields) + " FROM " + table;
		}
		
		List<Object> args = null;
		
		//WHERE
		if(where != null && !where.isEmpty()) {
			args = new ArrayList<Object>(where.size());
			List<String> condictions = new ArrayList<String>(where.size());
			
			for(Entry<String, Object> entry : where.entrySet()) {
				condictions.add(entry.getKey() + " = ? ");
				args.add(entry.getValue());
			}
			
			sql += " WHERE " + link(" AND ",condictions);
		}
		
		if(orderBy != null) {
			sql += " ORDER BY " + orderBy;
		}
		
		if(start >=0 && limit > 0) {
			sql += " LIMIT " + start + "," + limit;
		}
		
		return super.queryForList(sql, args);
	}

	@Override
	public int update(String table, Map<String, Object> data, String where)
	{
		//UPDATE table SET field1 = ?, field2 = ?.. WHERE where; 
		
		Assert.notEmpty(data);

		List<String> fieldStrs = new ArrayList<String>(data.size());
		List<Object> args      = new ArrayList<Object>(data.size());
		
		for(Entry<String, Object> entry : data.entrySet()) {
			fieldStrs.add(entry.getKey() + " = ? ");
			args.add(entry.getValue());
		}

		String sql = "UPDATE " + table + " SET " + link(" AND ",fieldStrs);
		
		if(where != null) {
			sql += " WHERE " + where;
		}
		
		return super.update(sql, args);
	}

	@Override
	public int update(String table, Map<String, Object> data, Map<String, Object> where)
	{
		//UPDATE table SET field1 = ?, field2 = ?.. WHERE key1 = ? AND key2 = ?..; ; 
		
		Assert.notEmpty(data);

		List<String> fieldStrs = new ArrayList<String>(data.size());
		List<Object> args      = new ArrayList<Object>(data.size());
		
		for(Entry<String, Object> entry : data.entrySet()) {
			fieldStrs.add(entry.getKey() + " = ? ");
			args.add(entry.getValue());
		}

		String sql = "UPDATE " + table + " SET " + link(" AND ",fieldStrs);
		
		//WHERE
		if(where != null && !where.isEmpty()) {
			List<String> condictions = new ArrayList<String>(where.size());
			
			for(Entry<String, Object> entry : where.entrySet()) {
				condictions.add(entry.getKey() + " = ? ");
				args.add(entry.getValue());
			}
			
			sql += " WHERE " + link(" AND ",condictions);
		}
		
		return super.update(sql, args);
	}

	@Override
	public int delete(String table, String where)
	{
		//DELETE FROM table WHERE where;
		
		String sql = "DELETE FROM " + table;
		
		if(where != null) {
			sql += " WHERE " + where;
		}
		
		return super.update(sql);
	}

	@Override
	public int delete(String table, Map<String, Object> where)
	{
		//DELETE FROM table WHERE key1 = ? AND key2 = ? ..;
		
		String sql = "DELETE FROM " + table;
		
		List<Object> args = null;
		
		//WHERE
		if(where != null && !where.isEmpty()) {
			args = new ArrayList<Object>(where.size());
			List<String> condictions = new ArrayList<String>(where.size());
			
			for(Entry<String, Object> entry : where.entrySet()) {
				condictions.add(entry.getKey() + " = ? ");
				args.add(entry.getValue());
			}
			
			sql += " WHERE " + link(" AND ",condictions);
		}
		
		return super.update(sql,args);
	}

	@Override
	public int count(String table, String where)
	{
		//SELECT COUNT(*) FROM table WHERE where;
		
		String sql = "SELECT COUNT(*) FROM " + table;
		
		if(where != null) {
			sql += " WHERE " + where;
		}

		return super.queryForInt(sql);
	}

	@Override
	public int count(String table, Map<String, Object> where)
	{
		//SELECT COUNT(*) FROM table WHERE key1 = ? AND key2 = ? ..;
		
		String sql = "SELECT COUNT(*) FROM " + table;
		
		List<Object> args = null;
		
		//WHERE
		if(where != null && !where.isEmpty()) {
			args = new ArrayList<Object>(where.size());
			List<String> condictions = new ArrayList<String>(where.size());
			
			for(Entry<String, Object> entry : where.entrySet()) {
				condictions.add(entry.getKey() + " = ? ");
				args.add(entry.getValue());
			}
			
			sql += " WHERE " + link(" AND ",condictions);
		}

		return super.queryForInt(sql,args);
	}
	
	
	//-----------------------------------------------------------
	//-----以下为通用的SQL操作 -----------------------------------------
	//-----------------------------------------------------------
	
	
	@Override
	public List<Map<String, Object>> query(String sql) throws DataAccessException
	{
		return super.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> query(String sql, Object... args) throws DataAccessException
	{
		return super.queryForList(sql, args);
	}

	@Override
	public int queryUpdate(String sql)
	{
		return super.update(sql);
	}

	@Override
	public int queryUpdate(String sql, Object... args)
	{
		return super.update(sql, args);
	}
}