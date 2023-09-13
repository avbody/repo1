package com.random.jpa.mapper;

import com.random.jpa.pojo.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoleMapper extends JpaRepository<Role,Long>, JpaSpecificationExecutor<Role> {
}
