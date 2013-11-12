package com.irelandken.test;
import java.util.ArrayList;
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
		
		List<String> fields = new ArrayList();
		
		fields.add("id");
		fields.add("name");
		fields.add("age");
		
		List<Map<String, Object>> list = template.select("users",fields,"name = 'kkk' AND age = 18");
		
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
		
		List<String> fields = new ArrayList();
		
		fields.add("id");
		fields.add("name");
		fields.add("age");
		
		Map<String,Object> where  = new HashMap<String,Object>();
		where.put("name", "kkk");
		where.put("age", 18);
		
		List<Map<String, Object>> list = template.select("users",fields,where);
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
		
		List<String> fields = new ArrayList();
		
		fields.add("name");
		fields.add("age");
		
		List<Map<String, Object>> list = template.select("users",fields,"name != 'ken'","name DESC,age ASC",0,10);
		
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
		List<String> fields = new ArrayList();
		
		fields.add("name");
		fields.add("age");
		
		Map<String,Object> where  = new HashMap<String,Object>();
		where.put("name", "kkk");
		where.put("age", 18);
		
		
		List<Map<String, Object>> list = template.select("users",null,where,"name DESC",0,10);
		
		System.out.println(list);
		
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
		data.put("name", "Ben");
		data.put("age", 20);
		
		Map<String,Object> where  = new HashMap<String,Object>();
		where.put("name", "JACK");
		where.put("age", 19);
		
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

}
