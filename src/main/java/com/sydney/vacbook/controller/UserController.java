package com.sydney.vacbook.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sydney.vacbook.entity.Admin;
import com.sydney.vacbook.entity.Location;
import com.sydney.vacbook.entity.User;
import com.sydney.vacbook.mapper.UserMapper;
import com.sydney.vacbook.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    private IUserService iUserService;

    @Autowired
    private IAdminService iAdminService;

    @Autowired
    private IVaccineService iVaccineService;

    @Autowired
    private IBookingService iBookingService;

    @Autowired
    private ILocationService iLocationService;

    @GetMapping("/{user_id}")
    public ModelAndView fetchUser(@PathVariable("user_id") int user_id){
        User user = iUserService.getById(user_id);
        ModelAndView modelAndView = new ModelAndView( "userPages/user_profile","result", user);
        System.out.println(user);
        return  modelAndView;
    }

    @PutMapping("/{user_id}")
    public boolean updateUser(@PathVariable("user_id") int user_id, @RequestBody Map<String, Object> body) {
        User user = iUserService.getById(user_id);
        // if body has content, update user information
        if (!body.isEmpty()) {
            System.out.println(body);
            user.updateByMap(body);
            iUserService.saveOrUpdate(user);
            return true;
        }
        return false;
    }

    @PostMapping("/getUserQuestion")
    public String getUserQuestion(@RequestBody Map<String, Object> body){
        String user_account = body.get("user_account").toString();
        QueryWrapper<User> findUserByAccount = new QueryWrapper<>();
        findUserByAccount.lambda().eq(User::getUserAccount, user_account);
        User user = iUserService.getOne(findUserByAccount);
        if(user == null) return null;
        //AJAX render ??
        String question = user.getUserQuestion();
        return question;
    }

    @PostMapping("/forgetPassword")
    public boolean forgetPassword(@RequestBody Map<String, Object> body){
        String user_account = body.get("user_account").toString();
        String input_answer = body.get("answer").toString();
        QueryWrapper<User> findUserByAccount = new QueryWrapper<>();
        findUserByAccount.lambda().eq(User::getUserAccount, user_account);
        User user = iUserService.getOne(findUserByAccount);
        if(!user.getUserSafeKey().equalsIgnoreCase(input_answer)){
            System.out.println(input_answer);
            System.out.println(user.getUserSafeKey());
            return false;
        }
        //return user;
        return true;
    }

    @PostMapping("/changePassword")
    public void changePassword(User user, @RequestBody Map<String, Object> body){
        String changePassword = body.get("changePassword").toString();
        user.setUserPassword(changePassword);
        iUserService.saveOrUpdate(user);
    }

    /**
     * Auth part
     * TODO WORDE
     * @param body
     * @return
     */
    @PostMapping("/login")
    public void login( @RequestBody Map<String, Object> body) {

    }

    @GetMapping("/register")
    public void register(@RequestBody Map<String, Object> body){

    }

    @GetMapping("/logout")
    public void logout(User user){

    }



}