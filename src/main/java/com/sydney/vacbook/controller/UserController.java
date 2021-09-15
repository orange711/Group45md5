package com.sydney.vacbook.controller;


import com.sydney.vacbook.entity.User;
import com.sydney.vacbook.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Group45
 * @since 2021-09-11
 */
@RestController
@RequestMapping("/vacbook/user")
@Controller
public class UserController {
@Autowired
    private UserMapper userMapper;
//返回userList
@GetMapping("/userList")
    public List<User> list(){
        List<User> users = userMapper.selectList(null);
        for (User user:users
             ) {
            System.out.println(user);
        }
        return users;
    }
//插入一个user
    @GetMapping("/userInsert")
    public int userInsert(User user){
        int insert = userMapper.insert(user);
        return insert;
    }
//插入测试；
    @GetMapping("/userAddTest")
    public int userAddTest(){
        int insert = userMapper.insert(new User(6,"1234567890","11@11.com","ass","sss","male","sydney",3,"new","123","ss","ff"));
        return insert;
    }

    //更新一个user
    @GetMapping("/userupdate")
    public int userUpdate(User user){
        int i = userMapper.updateById(user);
        return i;

        //hello
    }
}
