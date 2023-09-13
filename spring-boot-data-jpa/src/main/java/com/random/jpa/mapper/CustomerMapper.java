package com.random.jpa.mapper;

import com.random.jpa.pojo.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerMapper extends JpaRepository<Customer,Long>, JpaSpecificationExecutor<Customer>, QuerydslPredicateExecutor<Customer> {
    List<Customer> findAllByCustSourceAndCustIndustryLike(String custSource, String custIndustry);

    @Query(value = "from Customer where custName = :custname")
    List<Customer> find2(@Param("custname") String custName);

    @Modifying
    @Query("update Customer c set c.custName = :custName where c.custId = :custId")
    int updateCustomer(@Param("custName") String custName,@Param("custId") Long custId);
}
