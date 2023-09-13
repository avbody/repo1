package com.random.jpa;

import com.random.jpa.mapper.RoleMapper;
import com.random.jpa.mapper.UserMapper;
import com.random.jpa.pojo.Role;
import com.random.jpa.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JpaApplication.class)
public class test2 {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Test
    @Transactional
    @Rollback(false)
    public void testcreate(){
        User user = new User();
        user.setUserName("用户4");

        Role role = new Role();
        role.setRoleName("角色2");

        role = roleMapper.findOne(Example.of(role)).get();


        user.getRoles().add(role);
        role.getUsers().add(user);

        //roleMapper.save(role);
        userMapper.save(user);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void testdelete(){
        userMapper.deleteById(2L);
    }

    @Test
    @Transactional
    public void testFind(){
        Role role = roleMapper.findById(4L).get();
        System.out.println(role);
        System.out.println(role.getUsers());
    }
}
