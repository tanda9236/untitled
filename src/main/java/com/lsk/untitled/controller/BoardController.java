package com.lsk.untitled.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.lsk.untitled.config.auth.PrincipalDetail;

@Controller
public class BoardController {

	@GetMapping({"","/"}) // 세션접근 어노테이션
	public String index(@AuthenticationPrincipal PrincipalDetail principal) { // 컨트롤러에서 세션 찾기
//		System.out.println("로그인 사용자 아이디:"+principal.getUsername());
		return "index";
	}
}
