package com.tony.logic.module.test;

import org.springframework.stereotype.Service;

@Service
public class TestService {
	
	public void test(){
		System.out.println(" test msg from service");
	}

	
	public boolean login(String account, String password) {
		return true;
	}
}
