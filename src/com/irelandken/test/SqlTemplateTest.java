package com.irelandken.test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.irelandken.sql.SqlOperations;


/**
 * TEST Case for Data Oriented Sql Template 
 *  
 * @author irelandKen
 * @since 2013-12-07
 * @version 0.3.0
 * @see https://github.com/irelandKen/Schema_Free_Sql_Template
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext-db.xml"})
public class SqlTemplateTest {
	
	@Autowired
	private SqlOperations template;
	
	@Test
	public void insert() {
        Map<String, Object> users = new HashMap<String, Object>(2);
        users.put("name", "kkk");
        users.put("age", 18);
        
        Number id = template.insert("users", users);
        
        Assert.assertEquals(id.intValue() > 0, true);
	}
	
	@Test
	public void insert_1() {
        
        Number id = template.insert("INSERT INTO users (name,age) VALUES ('ken', 18)");
        
        Assert.assertEquals(id.intValue() > 0, true);
	}
	
	@Test
	public void insert_2() {
        
        Number id = template.insert("INSERT INTO users (name,age) VALUES (?, ?)", "ken", 18);
        
        Assert.assertEquals(id.intValue() > 0, true);
	}
	
	@Test
	public void insert2() {
        Map<String, Object> users = new HashMap<String, Object>(2);
        users.put("name", "kkk");
        users.put("age", 18);
        
        boolean success = template.insert2("users", users);
        
        Assert.assertEquals(success, true);
	}
	

	
	/**
	 * SELECT field1,field2.. FROM table WHERE where;
	 * <br>
	 * @param table
	 * @param fields
	 * @param where 
	 * @return affert rows count
	 */
	@Test
	public void select() {
		
		List<Map<String, Object>> list = template.select("users",new String[]{"id","name","age"},"name = 'kkk' AND age = 18");
	}
	
	
	/**
	 * SELECT field1,field2.. FROM table WHERE key1 = 'value1' AND key2 = 'value2' ..;
	 * 
	 * @param table
	 * @param fields
	 * @param where 
	 * @return affert rows count
	 */
	@Test
	public void select2() {
		
		Map<String,Object> where  = new HashMap<String,Object>();
		where.put("name", "kkk");
		where.put("age", 18);
		
		List<Map<String, Object>> list = template.select("users",new String[]{"id","name","age"},where);
	}
	
	
	
	/**
	 * SELECT field1,field2.. FROM table WHERE where ORDER BY orderBy LIMIT start,limit;
	 * 
	 * @param table
	 * @param fields
	 * @param where
	 * @param orderBy EX:"field1 DESC, field2 ASC"
	 * @param start
	 * @param limit
	 * @return
	 */
	@Test
	public void select3() {
		
		List<Map<String, Object>> list = template.select("users",new String[]{"name","age"},"name != 'ken'","name DESC,age ASC",0,10);
		
		System.out.println(list);
	}
	
	@Test
	public void select3_1() {
		
		List<Map<String, Object>> list = template.select("users",new String[]{"name","age"},"name != 'ken'","name DESC,age ASC",null,null);
		
		System.out.println(list);
	}
	
	/**
	 * SELECT field1,field2.. FROM table WHERE key1 = 'value1' AND key2 = 'value2'.. ORDER BY orderBy LIMIT start,limit;
	 * 
	 * @param table
	 * @param fields
	 * @param where
	 * @param orderBy EX:"field1 DESC, field2 ASC"
	 * @param start
	 * @param limit
	 * @return
	 */
	@Test
	public void select4() {
		
		Map<String,Object> where  = new HashMap<String,Object>();
		where.put("name", "kkk");
		where.put("age", 18);
		
		
		List<Map<String, Object>> list = template.select("users",null,where,"name DESC",0,10);
		
		System.out.println(list);
		
	}
	
	/**
	 * SELECT field1,field2.. FROM table WHERE where;
	 * <br>
	 * @param table
	 * @param fields
	 * @param where 
	 * @return affert rows count
	 */
	@Test
	public void selectOne() {
		
		Map<String, Object> map = template.selectOne("users",new String[]{"id","name","age"},"name = 'kkk' AND age = 18");
		
		System.out.println(map);
	}
	
	
	/**
	 * SELECT field1,field2.. FROM table WHERE key1 = 'value1' AND key2 = 'value2' ..;
	 * 
	 * @param table
	 * @param fields
	 * @param where 
	 * @return affert rows count
	 */
	@Test
	public void selectOne2() {
		
		Map<String,Object> where  = new HashMap<String,Object>();
		where.put("name", "kkk");
		where.put("age", 18);
		
		List<Map<String, Object>> list = template.select("users",new String[]{"id","name","age"},where);
	}
	
	
	/**
	 * UPDATE table SET field1 = 'value1', field2 = 'value2'.. WHERE where;
	 * <br>
	 * @param table
	 * @param data
	 * @param where 
	 * @return affert rows count
	 */
	@Test
	public void update() {
		
		Map<String,Object> data  = new HashMap<String,Object>();
		data.put("name", "JACK");
		data.put("age", 19);
		
		
		int cnt = template.update("users",data,"name = 'kkk'");
		
		System.out.println(cnt);
		
	}
	
	/**
	 * UPDATE table SET field1 = 'value1', field2 = 'value2'.. WHERE key1 = 'value1' AND key2 = 'value2' ..;
	 * <br>
	 * @param table
	 * @param data
	 * @param where 
	 * @return affert rows count
	 */
	@Test
	public void update2() {
		
		Map<String,Object> data  = new HashMap<String,Object>();
		data.put("name", "JACK");
		data.put("age", 19);
		
		Map<String,Object> where  = new HashMap<String,Object>();
		where.put("name", "JACK2");
		where.put("age", 20);
		
		int cnt = template.update("users",data,where);
		
		System.out.println(cnt);
		
	}
	
	
	/**
	 * DELETE FROM table WHERE where;
	 * 
	 * @param table
	 * @param where 
	 * @return affert rows count
	 */
	//int delete(String table, String where);
	@Test
	public void delete() {
	
		int age = (int)(Math.random() * 40 + 30);
		
        Map<String, Object> user = new HashMap<String, Object>(2);
        user.put("name", "GGG" + age);
        user.put("age", age);
        
        boolean success1 = template.insert2("users", user);
        boolean success2 = template.insert2("users", user);
        boolean success3 = template.insert2("users", user);
        
		
		int cnt1 = template.delete("users","name = '"+"GGG" + age+"' AND age = " + age);
		
		if(cnt1 == 3) 
			System.out.println("SUCCESS");
		
		System.out.println(cnt1);
	}
	
	/**
	 * DELETE FROM table WHERE key1 = 'value1' AND key2 = 'value2' ..;
	 *  
	 * @param table
	 * @param where 
	 * @return affert rows count
	 */
	//int delete(String table, Map<String,Object> where);
	@Test
	public void delete2() {
	
		int age = (int)(Math.random() * 40 + 30);
		
        Map<String, Object> user = new HashMap<String, Object>(2);
        user.put("name", "GGG" + age);
        user.put("age", age);
        
        boolean success1 = template.insert2("users", user);
        boolean success2 = template.insert2("users", user);
        boolean success3 = template.insert2("users", user);
        
		Map<String,Object> where  = new HashMap<String,Object>();
		where.put("name", "GGG" + age);
		where.put("age", age);
		
		int cnt1 = template.delete("users",where);
		
		if(cnt1 == 3) 
			System.out.println("SUCCESS");
		
		System.out.println(cnt1);
	}
	
	/**
	 * SELECT COUNT(*) FROM table WHERE where;
	 * 
	 * @param table
	 * @param where 
	 * @return rows count
	 */
	@Test
	public void count() {
	
		int cnt1 = template.count("users","name = 'ken' AND age = 18");
		int cnt2 = template.count("users","name = 'Ben' AND age = 20");
		int cnt3 = template.count("users","age = 20");
		
		System.out.println(cnt1);
	}
	
	/**
	 * SELECT COUNT(*) FROM table WHERE key1 = 'value1' AND key2 = 'value2' ..;
	 * 
	 * @param table
	 * @param where 
	 * @return affert rows count
	 */
	//int count(String table, Map<String,Object> where);
	@Test
	public void count2() {
	
		Map<String,Object> where  = new HashMap<String,Object>();
		where.put("name", "Ben");
		where.put("age", 20);
		
		int cnt2 = template.count("users",where);
		
		System.out.println(cnt2);
	}
	
	
	//-----------------------------------------------------------
	//-----以下为通用的SQL操作 -----------------------------------------
	//-----------------------------------------------------------
	
	
	@Test
	public void query_test()
	{
		List<Map<String, Object>> users = template.query("SELECT * FROM users WHERE age = 20");
		
		System.out.println(users);
		//[{id=7007, name=Ben, age=20}, {id=7008, name=Ben, age=20}, {id=7009, name=Ben, age=20}]
	}
	
	@Test
	public void query2_test()
	{
		List<Map<String, Object>> users = template.query("SELECT * FROM users WHERE age >= ? ",20);
		
		System.out.println(users);
		//[{id=7007, name=Ben, age=20}, {id=7008, name=Ben, age=20}, {id=7009, name=Ben, age=20}, {id=7010, name=bbb, age=21}, {id=7011, name=aaa, age=21}]
	}
	
	@Test
	public void queryOne_test()
	{
		Map<String, Object> user = template.queryOne("SELECT * FROM users WHERE id = 1");
		
		System.out.println(user);
		//{id=1, name=ken, age=18}
	}
	
	@Test
	public void queryOne2_test()
	{
		Map<String, Object> user = template.queryOne("SELECT * FROM users WHERE id = ? ", 1);
		
		System.out.println(user);
		//{id=1, name=ken, age=18}
	}


	@Test
	public void queryUpdate()
	{
		int rows = template.queryUpdate("UPDATE users SET age = age +1 WHERE age >= 20");
		
		System.out.println("Affect Rows: " + rows);
		//Affect Rows: 5
	}

	@Test
	public void queryUpdate2()
	{
		int rows = template.queryUpdate("UPDATE users SET age = age +1 WHERE age >= ?", 20);
		
		System.out.println("Affect Rows: " + rows);
		//Affect Rows: 5
	}

}
