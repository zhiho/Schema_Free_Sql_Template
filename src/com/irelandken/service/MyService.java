package com.irelandken.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MyService implements BusinessService
{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public void service() {
		int num = jdbcTemplate.update("INSERT INTO users (name, age) VALUES ('ken', 18)");
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM users");
		
	}
}
