package com.irelandken.sql;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 *  
 * @author irelandKen
 * @since 2013-11-10
 */

public interface SqlOperate
{
	/**
	 * insert a row to table with the data and return id
	 * @param table
	 * @param data
	 * @return id
	 */
	Number insert(String table, Map<String, Object> data);
	
	/**
	 * insert a row to table with the data
	 * @param table
	 * @param data
	 * @return affert rows count
	 */
	int insert2(String table, Map<String, Object> data);

	
	/**
	 * update the table with the data
	 * @param table
	 * @param data
	 * @param where 
	 * @return affert rows count
	 */
	int updata(String table,Map<String, Object> data,String where);
	
	/**
	 * update the table with the data
	 * @param table
	 * @param data
	 * @param where 
	 * @return affert rows count
	 */
	int updata(String table,Map<String, Object> data,Map<String,Object> where);
	
	/**
	 * Issue a single SQL update operation (such as an insert, update or delete statement).
	 * @param sql static SQL to execute
	 * @return the number of rows affected
	 */
	int update(String sql);
	
	/**
	 * Execute a query for a result list, given static SQL.
	 * <p>Uses a JDBC Statement, not a PreparedStatement. If you want to
	 * execute a static query with a PreparedStatement, use the overloaded
	 * {@code queryForList} method with {@code null} as argument array.
	 * <p>The results will be mapped to a List (one entry for each row) of
	 * Maps (one entry for each column using the column name as the key).
	 * Each element in the list will be of the form returned by this interface's
	 * queryForMap() methods.
	 * @param sql SQL query to execute
	 * @return an List that contains a Map per row
	 * @throws DataAccessException if there is any problem executing the query
	 * @see #queryForList(String, Object[])
	 */
	List<Map<String, Object>> queryForList(String sql) throws DataAccessException;
}
