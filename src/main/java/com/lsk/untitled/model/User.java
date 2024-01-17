package com.lsk.untitled.model;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Getter + Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity // User클래스가 MySQL에 테이블이 생성이 된다.
//@DynamicInsert //insert시에 null필드를 제외시켜준다.
public class User {
	
	@Id //Primarykey
	@GeneratedValue(strategy = GenerationType.IDENTITY)//연결된 DB의 넘버링 전략따라가기(시퀀스, auto_increment)
	private int id;
	
	@Column(nullable = false, length = 30, unique = true)
	private String username;
	
	@Column(nullable = false, length = 100)
	private String password;
	
	@Column(nullable = false, length = 50)
	private String email;
	
//	@ColumnDefault("'user'")
	//DB는 RoleType이라는게 없음 -> RoleType 클래스 생성
	@Enumerated(EnumType.STRING)
	private RoleType role;
	
	@CreationTimestamp //시간 자동 입력됨
	private Timestamp createDate; // updateDate도 추가하기
}
