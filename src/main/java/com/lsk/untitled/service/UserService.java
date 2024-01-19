package com.lsk.untitled.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lsk.untitled.model.RoleType;
import com.lsk.untitled.model.User;
import com.lsk.untitled.repository.UserRepository;

//스프링이 컴포넌트 스캔을 통해서 Bean에 등록해줌. IoC(메모리에 띄워준다)
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional
	public int 회원가입(User user) {
		String rawPassword = user.getPassword(); // 1234 원문
		String encPassword = encoder.encode(rawPassword); // 해쉬가됨
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		try {
			userRepository.save(user);
			return 1;
		} catch (Exception e) {
			return -1;
		}
	}
	
	@Transactional
	public void 회원수정(User user) {
		// 수정시에는 영속성 컨텍스트 User 오브젝트를 영속화시키고, 영속화된 User 오브젝트를 수정 하기
		// select를 해서 User오브젝트를 DB로부터 가져오는 이유는 영속화를 하기 위해서
		// 영속화된 오브젝트를 변경하면 자동으로DB에 update문을 날려준다.
		User persistance = userRepository.findById(user.getId())
				.orElseThrow(()->{
					return new IllegalArgumentException("회원 찾기 실패");
				});
		String rawPassword = user.getPassword();
		String encPassword = encoder.encode(rawPassword);
		persistance.setPassword(encPassword);
		persistance.setEmail(user.getEmail());
		
		// 회원수정 함수 종료 -> 서비스종료 -> 트랜잭션종료 -> 자동 commit
		// 영속화된 persistance객체의 변화 감지 -> 더티체킹 -> 자동 update문
		// >> db값은 변경됨 세션값 변경하기 만들기
	}
}
