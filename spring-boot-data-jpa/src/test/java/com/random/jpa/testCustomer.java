package com.random.jpa;


import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.random.jpa.mapper.CustomerMapper;
import com.random.jpa.mapper.LinkManMapper;
import com.random.jpa.pojo.Customer;
import com.random.jpa.pojo.LinkMan;
import com.random.jpa.pojo.QCustomer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = JpaApplication.class)
public class testCustomer {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private LinkManMapper linkManMapper;

    @PersistenceContext
    EntityManager em;

    @Test
    public void testCreate() {
        Customer customer = new Customer();
        customer.setCustName("张三");
        customerMapper.save(customer);
    }

    @Test
    public void testFind() {
        Page<Customer> customerPage = customerMapper.findAll(PageRequest.of(0, 2, Sort.by("custId").descending()));
        for (Customer customer : customerPage.getContent()) {
            System.out.println(customer);
        }
    }

    @Test
    public void testFind1() {
        List<Customer> customers = customerMapper.findAllByCustSourceAndCustIndustryLike("internet", "jav%");
        customers.forEach(System.out::println);
    }

    @Test
    public void testFind2() {
        List<Customer> customers = customerMapper.find2("张三");
        customers.forEach(System.out::println);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void testUpdateCustomer() {
        System.out.println(customerMapper.updateCustomer("张三", 1L));
    }

    @Test
    public void testQueryByExample() {
        Customer customer = new Customer();
        customer.setCustSource("internet");
        customer.setCustIndustry("java");
        List<Customer> customers = customerMapper.findAll(Example.of(customer));
        customers.forEach(System.out::println);
    }

    @Test
    public void testQueryByExample2() {
        Customer customer = new Customer();
        customer.setCustSource("aaa");
        customer.setCustIndustry("JAV");
        customer.setCustId(1L);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("custSource")
                //.withIgnoreCase("custIndustry")
                .withMatcher("custIndustry", config -> config.startsWith().ignoreCase());
        List<Customer> customers = customerMapper.findAll(Example.of(customer, matcher));
        customers.forEach(System.out::println);
    }

    @Test
    public void testQueryDSL() {
        QCustomer customer = QCustomer.customer;
        BooleanExpression be = customer.custName.in("张三", "李四")
                .and(customer.custIndustry.containsIgnoreCase("JA"))
                .or(customer.custSource.like("%old%"));
        Iterable<Customer> customers = customerMapper.findAll(be);
        customers.forEach(System.out::println);
    }

    @Test
    public void testQueryDSL1() {
        QCustomer customer = QCustomer.customer;
        BooleanExpression expression = customer.isNull().or(customer.isNotNull());
        expression = true ? expression.and(customer.custName.in("张三","李四")):expression;
        expression = false ? expression.and(customer.custIndustry.containsIgnoreCase("JA")):expression;
        System.out.println(customerMapper.findAll(expression));
    }

    @Test
    public void testSpecification() {

        Specification<Customer> specification = (root, criteriaQuery, cb) -> {
            //return cb.and(cb.gt(root.get("custId").as(Long.class), 1L), cb.like(root.get("custIndustry").as(String.class), "%ava"));
            return cb.in(root.get("custName").as(String.class)).value("张三").value("李四");
        };
        Page<Customer> customers = customerMapper.findAll(specification, PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "custName")));
        System.out.println(customers.getContent());
    }

    @Test
    public void testOriginal(){
        JPAQueryFactory factory = new JPAQueryFactory(em);
        QCustomer customer = QCustomer.customer;
        JPAQuery<Tuple> query = factory.select(customer.custId, customer.custName)
                .from(customer)
                .where(customer.custId.gt(1L))
                .orderBy(customer.custId.desc());
        List<Tuple> tuples = query.fetch();
        tuples.forEach(System.out::println);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void testOneToMany(){
        Customer customer = new Customer();
        customer.setCustName("赵八");
        customer.setCustSource("门户2");
        customer.setCustIndustry("土木2");

        LinkMan linkMan = new LinkMan();
        linkMan.setLkmName("采购3");
        linkMan.setLkmPhone("13344445555");


        customer.getLinkMans().add(linkMan);
        linkMan.setCustomer(customer);

        customerMapper.save(customer);
        linkManMapper.save(linkMan);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void testdelete(){
        customerMapper.deleteById(11L);
    }
}
