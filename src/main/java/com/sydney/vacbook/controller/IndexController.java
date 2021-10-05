package com.sydney.vacbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class IndexController {


    @GetMapping("vacBook/user/index")
    public String viewHomePage() {
        return "userPages/index";
    }

    @GetMapping("vacBook/user/login")
    public String userLogin() {
        return "userPages/userLogin";
    }

    @GetMapping("/user/register")
    public String userRegister() {
        return "userPages/userRegister";
    }

    @GetMapping("/vacBook/user/sendEmailSuccessful")
    public String userSentEmailSuccess() {
        return "userPages/emailConfirmation";
    }


    @GetMapping("vacBook/user/checkEligibility")
    public String userCheckEligibility() {
        return "userPages/userCheckEligibility";
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
