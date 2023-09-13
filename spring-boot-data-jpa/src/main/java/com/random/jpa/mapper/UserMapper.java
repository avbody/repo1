package com.random.jpa.mapper;

import com.random.jpa.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserMapper extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {
}
