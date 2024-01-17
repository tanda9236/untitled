package com.lsk.untitled.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lsk.untitled.model.User;

//DAO
//자동으로 bean등록이 된다.
//@Repository 생략가능
public interface UserRepository extends JpaRepository<User, Integer>{
	
}
