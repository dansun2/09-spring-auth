package com.ohgiraffers.security.user.dao;

import com.ohgiraffers.security.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository implements JpaRepository<User, Integer> {

}
