package com.lsk.untitled.controller.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lsk.untitled.dto.ResponseDto;
import com.lsk.untitled.model.User;
import com.lsk.untitled.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;
	
	// SecurityConfig에 만들어둔거(스프링시큐리티 구조 보기)
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) { // username, password, email
		System.out.println("UserApiController : save 호출");
		userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user){ // key=value, x-www-form-urlencoded
		userService.회원수정(user);
		// 트랜잭션이 종료되기 때문에 DB값은 변경됨
		// 하지만 세션값은 변경되지 않은 상태, 직접 세션값 변경시키기
		
		// 세션 등록 (강제로 로그인처리)
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
}
