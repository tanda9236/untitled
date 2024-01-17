package com.lsk.untitled.test;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lsk.untitled.model.RoleType;
import com.lsk.untitled.model.User;
import com.lsk.untitled.repository.UserRepository;

import jakarta.transaction.Transactional;

@RestController
public class DummyControllerTest {
	
	@Autowired //의존성 주입(DI)
	private UserRepository userRepository;
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
	    
	    if (user.isPresent()) {
	        userRepository.deleteById(id);
	        return "삭제되었습니다. id:" + id;
	    } else {
	        return "삭제할 대상이 없습니다. id:" + id;
	    }
	}// 수정본
	
//	@DeleteMapping("/dummy/user2/{id}")
//	public String delete2(@PathVariable int id) {
//		try {
//			userRepository.deleteById(id);
//		} catch (EmptyResultDataAccessException e) {
//			return "삭제에실패. 해당 id는 DB에 없습니다.";
//		}
//		return "삭제되었습니다. id:"+id;
//	}// 원본 작동X
	
	// save() : id 전달하지 않으면 insert
	// 전달하면 해당 id 데이터가 있으면 update , 전달하면 해당 id 데이터가 없으면 insert
	@Transactional // 함수종료 시 자동 commit, save() 쓰지 않아도 update 된다
	@PutMapping("/dummy/user/{id}") //매핑이 달라서 주소가 같아도 알아서 구분됨
	//json데이터 요청 -> Java Object(Message Converter의 Jackson라이브러리가 변환해서 받아준다)
	public User updateUser(@PathVariable int id,@RequestBody User requestUser) {
		System.out.println("id:"+id);
		System.out.println("password:"+requestUser.getPassword());
		System.out.println("email:"+requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()-> {
			return new IllegalArgumentException("수정실패!");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		return user;
	}// *더티체킹을 이용한 업데이트
	
	@GetMapping("/dummy/users") // 모든유저 출력
	public List<User> list(){
		return userRepository.findAll();
	}
	
	@GetMapping("/dummy/user") // 유저 페이징, 페이지당 2건의 데이터, DESC
	public Page<User> pageList(@PageableDefault(size=2, sort="id", direction = Sort.Direction.DESC) Pageable pageable){
		Page<User> pagingUser = userRepository.findAll(pageable);

		List<User> users = pagingUser.getContent();
		return pagingUser;
	}
	
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				return new IllegalArgumentException("해당 유저는 없습니다. id:"+id);
			}
			// 람다식 ver
//			User user = userRepository.findById(id).orElseThrow(()-> {
//				return new IllegalArgumentException("해당 유저는 없습니다. id:"+id);
//			});
			
		});
		return user; //26
	}
	
	//http의 body에 username, password, email 데이터를 가지고(요청)
	@PostMapping("/dummy/join")
	public String join(User user) { //key=value(약속된규칙)
		System.out.println("id:"+user.getId());
		System.out.println("role:"+user.getRole());
		System.out.println("createDate:"+user.getCreateDate());
		System.out.println("username:"+user.getUsername());
		System.out.println("password:"+user.getPassword());
		System.out.println("email:"+user.getEmail());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";
	}//auto_increment 문제 해결하기
}
