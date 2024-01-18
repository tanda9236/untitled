package com.lsk.untitled.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;

import com.lsk.untitled.model.User;

// DAO
// 자동으로 bean등록이 된다.
// @Repository 생략가능
public interface UserRepository extends JpaRepository<User, Integer>{
	// SELECT * FROM user WHERE username =1?;
	Optional<User> findByUsername(String username);
}

// 1안 JPA Naming 쿼리
// SELECT * FROM user WHERE username = ?1 AND password = ?2; 동작
//User findByUsernameAndPassword(String username, String password);

// 2안
//@Query(value="SELECT * FROM user WHERE username = ?1 AND password = ?2",nativeQuery = true)
//User login(String username, String password);