package com.sydney.vacbook.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sydney.vacbook.entity.Admin;
import com.sydney.vacbook.entity.Location;
import com.sydney.vacbook.entity.Vaccine;
import com.sydney.vacbook.service.IAdminService;
import com.sydney.vacbook.service.IVaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Controller
public class IndexController {

    @Autowired
    private IAdminService iAdminService;

    @Autowired
    private IVaccineService iVaccineService;

    @GetMapping("vacBook/user/index")
    public String viewHomePage() {
        return "userPages/index";
    }

    @GetMapping("vacBook/user/login")
    public String userLogin() {
        return "userPages/userLogin";
    }

    @GetMapping("vacBook/user/register")
    public String userRegister() {
        return "userPages/userRegister";
    }

    @GetMapping("vacBook/user/register/index")
    public String success() {
        return "userPages/index";
    }

    @PostMapping("vacBook/user/indexCheckboxDone")
    public ModelAndView indexCheckboxDone(){
        ModelAndView modelAndView = new ModelAndView( "userPages/index");
        return modelAndView;

    }

    @GetMapping("/vacBook/user/sendEmailSuccessful")
    public String userSentEmailSuccess() {
        return "userPages/emailConfirmation";
    }


    @GetMapping("vacBook/user/checkEligibility")
    public String userCheckEligibility() {
        return "userPages/userCheckEligibility";
    }

    @RequestMapping("/vacBook/user/forgottenPassword")
    public String userForgottenPassword() { return "userPages/forgot-psw";}

    @GetMapping("/vacBook/user/changePassword")
    public String userChangePassword() {
        return "userPages/change-psw";
    }


    @GetMapping("vacBook/user/checkEligibilityRegister")
    public String userCheckEligibilityInRegister() {
        return "userPages/CheckEligibilityInRegister";
    }


    @GetMapping("/getAllUserInfo")
    public String getAllUser(){
        return null;
    }

    @PostMapping("/sendGuestEmail")
    public void sendGuestEmail(){

    }



//
//    @RequestMapping("/admin/fetch")
//    public String adminLogin() {
//        return "index";




}
