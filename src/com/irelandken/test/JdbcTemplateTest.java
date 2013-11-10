package com.irelandken.test;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.irelandken.service.BusinessService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext-db.xml"})
public class JdbcTemplateTest {
	
	@Autowired
	private BusinessService businessService;

	
	@Test
	public void test() throws InstantiationException, IllegalAccessException
	{
		businessService.service();
		
		businessService.service();
		
	}

}
