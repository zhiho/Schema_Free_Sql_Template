package com.irelandken.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class SqlTemplate extends JdbcTemplate implements SqlOperate 
{
	public SqlTemplate(DataSource dataSource)
	{
		super(dataSource);
	}

	private static String join(String glue,Collection<String> strs) {
		
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
		
		final String sql = "INSERT INTO " + table + " (" + join(",",data.keySet()) + ") VALUES (" + placeholders(data.size()) + ")";

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
	public int insert2(String table, Map<String, Object> data)
	{
		String sql = "INSERT INTO " + table + " (" + join(",",data.keySet()) + ") VALUES (" + placeholders(data.size()) + ")";
		
		return super.update(sql, data.values().toArray());
	}

	@Override
	public int updata(String table, Map<String, Object> data, String where)
	{
		String sql = "INSERT INTO " + table + " (" + join(",",data.keySet()) + ") VALUES (" + placeholders(data.size()) + ")";
		
		return super.update(sql, data.values().toArray());
	}

	@Override
	public int updata(String table, Map<String, Object> data, Map<String, Object> where)
	{
		// TODO Auto-generated method stub
		return 0;
	}
}