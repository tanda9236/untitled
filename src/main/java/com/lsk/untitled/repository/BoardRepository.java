package com.lsk.untitled.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lsk.untitled.model.Board;

public interface BoardRepository extends JpaRepository<Board, Integer>{

}