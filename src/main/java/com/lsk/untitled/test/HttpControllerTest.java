package com.lsk.untitled.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// postman 테스트
// Data응답 : RestController, HTML응답 : Controller
@RestController
public class HttpControllerTest {
	
	private static final String TAG = "HttpControllerTest";
	
	@GetMapping("/http/lombok")
	public String lombokTest() {
		Member m = Member.builder().username("ssar").password("1234").email("ssar@nate.com").build();
		System.out.println(TAG+"getter:"+m.getUsername());
		m.setUsername("danda");
		System.out.println(TAG+"setter:"+m.getUsername());
		return "lombok test 완료";
	}
	
	//(select) 인터넷 브라우저 요청은 무조건 get
	@GetMapping("/http/get") 
	public String getTest(Member m) { // @RequestParam int id,@RequestParam String username
		return "get 요청:" + m.getId()+","+m.getUsername()+","+m.getPassword()+","+m.getEmail();
	}
	//(insert)
	@PostMapping("/http/post")
	public String postTest(@RequestBody Member m) { //MessageConverter(스프링부트)
		return "post 요청:" + m.getId()+","+m.getUsername()+","+m.getPassword()+","+m.getEmail();
	}
	//(update)
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "put 요청"  + m.getId()+","+m.getUsername()+","+m.getPassword()+","+m.getEmail();
	}
	//(delete)
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}
}
