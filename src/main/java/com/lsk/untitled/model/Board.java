package com.lsk.untitled.model;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false,length = 100)
	private String title;
	
	@Lob //대용량데이터
	@Column(columnDefinition = "LONGTEXT")
	private String content;
	
	@ColumnDefault("0")
	private int count; //조회수
	
	// Many = Board, User = One
	@ManyToOne(fetch = FetchType.EAGER) 
	@JoinColumn(name="userId")
	private User user; //DB는 오브젝트를 저장할 수 없다. FK, 자바는 오브젝트를 저장할 수 있다.

	// One = Board, Reply = Many
	// @JoinColumn(name="userId") 필요없이 자동으로 조인해줌
	@OneToMany(mappedBy = "board",fetch = FetchType.EAGER) //한 화면에 다 띄울 필요가 있으면 EAGER, 펼치기 같은거 쓰려면 LAZY 가능
	private List<Reply> reply;
	
	@CreationTimestamp
	private Timestamp createDate;
}
