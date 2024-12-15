package com.pvt.enotes.repository;

import com.pvt.enotes.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo,Integer> {
    List<Todo> findByCreatedBy(Integer userId);
}
