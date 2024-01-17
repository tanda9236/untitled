package com.lsk.untitled.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lsk.untitled.model.User;
import com.lsk.untitled.repository.UserRepository;

import jakarta.transaction.Transactional;

//스프링이 컴포넌트 스캔을 통해서 Bean에 등록해줌. IoC(메모리에 띄워준다)
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public void 회원가입(User user) {
		userRepository.save(user);
	}
	
	
}
