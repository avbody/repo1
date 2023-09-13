package com.random.jpa.mapper;

import com.random.jpa.pojo.LinkMan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LinkManMapper extends JpaRepository<LinkMan,Long>, JpaSpecificationExecutor<LinkMan> {
}
