package com.irelandken.sql;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameterValue;

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
	int select(String table,Collection<String> fields,String where);
	
	/**
	 * update the table with the data
	 * @param table
	 * @param data
	 * @param where 
	 * @return affert rows count
	 */
	int select(String table,Collection<String> fields,Map<String,Object> where);
	
	
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
	 * update the table with the data
	 * @param table
	 * @param where 
	 * @return affert rows count
	 */
	int delete(String table, String where);
	
	/**
	 * update the table with the data
	 * @param table
	 * @param where 
	 * @return affert rows count
	 */
	int delete(String table, Map<String,Object> where);
	
	
	
	//-----------------------------------------------
	//-----以下为通用的SQL操作------------------------------------------
	//-----------------------------------------------
	
	
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
	List<Map<String, Object>> query(String sql) throws DataAccessException;
	
	/**
	 * Query given SQL to create a prepared statement from SQL and a
	 * list of arguments to bind to the query, expecting a result list.
	 * <p>The results will be mapped to a List (one entry for each row) of
	 * Maps (one entry for each column, using the column name as the key).
	 * Each element in the list will be of the form returned by this interface's
	 * queryForMap() methods.
	 * @param sql SQL query to execute
	 * @param args arguments to bind to the query
	 * (leaving it to the PreparedStatement to guess the corresponding SQL type);
	 * may also contain {@link SqlParameterValue} objects which indicate not
	 * only the argument value but also the SQL type and optionally the scale
	 * @return a List that contains a Map per row
	 * @throws DataAccessException if the query fails
	 * @see #queryForList(String)
	 */
	List<Map<String, Object>> query(String sql, Object... args) throws DataAccessException;

	/**
	 * Issue a single SQL update operation (such as an insert, update or delete statement).
	 * @param sql static SQL to execute
	 * @return the number of rows affected
	 */
	int queryUpdate(String sql);
	
	/**
	 * Issue a single SQL update operation (such as an insert, update or delete statement)
	 * via a prepared statement, binding the given arguments.
	 * @param sql SQL containing bind parameters
	 * @param args arguments to bind to the query
	 * (leaving it to the PreparedStatement to guess the corresponding SQL type);
	 * may also contain {@link SqlParameterValue} objects which indicate not
	 * only the argument value but also the SQL type and optionally the scale
	 * @return the number of rows affected
	 */
	int queryUpdate(String sql, Object... args);
	
}
