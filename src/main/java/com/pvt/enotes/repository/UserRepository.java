package com.pvt.enotes.repository;

import com.pvt.enotes.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    Boolean existsByEmail(String email) throws Exception;

    User findByEmail(String username);
}
