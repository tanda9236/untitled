package com.lsk.untitled.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogControllerTest {
	
	//http://localhost:8080/test/test1
	@GetMapping("/test/test1")
	public String test1() {
		return "<h1>테스트</h1>";
	}
}
