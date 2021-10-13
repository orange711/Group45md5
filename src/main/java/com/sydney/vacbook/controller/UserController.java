package com.sydney.vacbook.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sydney.vacbook.entity.Admin;
import com.sydney.vacbook.entity.Location;
import com.sydney.vacbook.entity.User;
import com.sydney.vacbook.entity.Vaccine;
import com.sydney.vacbook.service.*;
import com.sydney.vacbook.service.impl.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.sydney.vacbook.tool.MD5.code;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Group45
 * @since 2021-09-11
 */
@Controller
@RequestMapping("/vacBook/user")
public class UserController {
    @Autowired
    private IUserService iUserService;

    @Autowired
    private SendEmailService sendEmailService;

    @Autowired
    private IAdminService iAdminService;

    @Autowired
    private IVaccineService iVaccineService;

    @Autowired
    private IBookingService iBookingService;

    @Autowired
    private ILocationService iLocationService;


//    新增userlist
    List<User> listUser = new ArrayList<>();

    @GetMapping("/profile")
    public ModelAndView fetchUser(){
        User user = listUser.get(0);
        ModelAndView modelAndView = new ModelAndView( "userPages/userProfile","result", user);
        System.out.println(user);
        return  modelAndView;
    }

    @PutMapping("/profile")
    public ModelAndView updateUser(@RequestParam String first, String last, Integer age, String account, String password, String email, String gender, String phone, String address, String answer) {
        User user = listUser.get(0);
        user.setUserFirstname(first);
        user.setUserLastname(last);
        user.setGender(gender);
        user.setEmail(email);
        user.setPhoneNumber(phone);
        user.setAddress(address);
        user.setUserAccount(account);
        if (password != null && password != " " && !password.isEmpty()) {
            String passwordMD5 = code(password);
            System.out.println(password + ".");
            user.setUserPassword(passwordMD5);
        }

        user.setUserSafeKey(answer);
        user.setAge(age);
        iUserService.saveOrUpdate(user);
        return fetchUser();
    }

    /**
     * send account and receive user to get question
     */
    @GetMapping("/get_user_by_account")
    public User getUserByAccount(@RequestParam("user_account") String user_account){//@RequestBody Map<String, Object> body){
        //String user_account = body.get("user_account").toString();
        System.out.println(user_account);
        QueryWrapper<User> findUserByAccount = new QueryWrapper<>();
        findUserByAccount.lambda().eq(User::getUserAccount, user_account);
        User user = iUserService.getOne(findUserByAccount);
        System.out.println(user);
        return user;
    }

    // fontend should do this validation
//    @PostMapping("/forgetPassword")
//    public boolean forgetPassword(@RequestBody Map<String, Object> body){
//        String user_account = body.get("user_account").toString();
//        String input_answer = body.get("answer").toString();
//        QueryWrapper<User> findUserByAccount = new QueryWrapper<>();
//        findUserByAccount.lambda().eq(User::getUserAccount, user_account);
//        User user = iUserService.getOne(findUserByAccount);
//        if(!user.getUserSafeKey().equalsIgnoreCase(input_answer)){
//            System.out.println(input_answer);
//            System.out.println(user.getUserSafeKey());
//            return false;
//        }
//        //return user;
//        return true;
//    }

    @PostMapping("/changePassword")
    public void changePassword(User user, @RequestBody Map<String, Object> body){
        String changePassword = body.get("changePassword").toString();
        user.setUserPassword(changePassword);
        iUserService.saveOrUpdate(user);
        //return main
    }


//    存放user list
    @RequestMapping("/userList")
    public ModelAndView userList(){
        ModelAndView modelAndView = new ModelAndView("userPages/index", "userList", listUser);
        return modelAndView;
    }

    @GetMapping("/index/booking")
    public ModelAndView userBooking() {
        User user = listUser.get(0);
        List<Admin> adminList = iAdminService.list();
        List<Vaccine> vaccineList = iVaccineService.list();
        ModelAndView modelAndView = new ModelAndView("userPages/indexBooking","providers",adminList);
        modelAndView.addObject("vaccineList",vaccineList);
        modelAndView.addObject("user_name", user.getUserLastname());
        modelAndView.addObject("user_id", user.getUserId());
        return modelAndView;
    }


    @PostMapping ("/loginForm")
    public ModelAndView login(HttpServletRequest request,String userAccount,String userPassword,  Map<String, Object> body) {
        System.out.println(userAccount+".,.."+userPassword);
        String userPasswordMD5 = code(userPassword);

		//按用户名密码查询
        QueryWrapper<User> sectionQueryWrapper = new QueryWrapper<>();
        sectionQueryWrapper.eq("user_account", userAccount);
        sectionQueryWrapper.eq("user_password", userPasswordMD5);
        listUser = iUserService.list(sectionQueryWrapper);

        if (!listUser.toString().equals("[]")) {
            System.out.println("Welcome to our system!");
            System.out.println(listUser.get(0));

//            //Session 存储
//            //第一步：获取session
//            HttpSession session = request.getSession();
//            //第二步：将想要保存到数据存入session中
//            session.setAttribute("userName",userAccount);
//            session.setAttribute("password",userPasswordMD5);
//            //这样就完成了用户名和密码保存到session的操作
//            System.out.println(session.getAttribute("userName"));

            ModelAndView success = new ModelAndView("redirect:index/booking");
            return success;
        } else {
            System.err.println("Some errors");
            ModelAndView fail = new ModelAndView("redirect:login");
            return fail;
        }
    }

        @RequestMapping("/register")
    public String register(User user, Map<String, Object> body){

        System.out.println(user);

//        user.setUserPassword(request.getParameter("password"));
//        user.setUserAccount(request.getParameter("account"));
//        user.setUserSafeKey(request.getParameter("safeKey"));
//        user.setAddress(request.getParameter("address"));
//        user.setAge(request.getIntHeader("age"));//could be some problems
//        user.setGender(request.getParameter("gender"));
//        user.setUserFirstname(request.getParameter("first"));
//        user.setUserLastname(request.getParameter("last"));
//        user.setUserQuestion(request.getParameter("question"));
//        user.setPhoneNumber(request.getParameter("phone"));
//        user.setEmail(request.getParameter("email"));

        String passwordMD5 = code(user.getUserPassword());
        user.setUserPassword(passwordMD5);
        QueryWrapper<User> checkQueryWrapper = new QueryWrapper<>();
        checkQueryWrapper.eq("user_account", user.getUserAccount());
        checkQueryWrapper.eq("phone_number", user.getPhoneNumber());
        checkQueryWrapper.eq("email", user.getEmail());
        if (iUserService.getOne(checkQueryWrapper)!=null){
            System.err.println("This account has been registered或者电话或者email被注册");
            return "redirect:registerPage";//重定向
        }


        boolean newUser = iUserService.save(user);

        if (newUser == false){
            System.err.println("This account has been ...");
            return "redirect:registerPage";
        }
        else{
            System.out.println("Hi!");


            QueryWrapper<User> sectionQueryWrapper = new QueryWrapper<>();
            sectionQueryWrapper.eq("user_account", user.getUserAccount());
            sectionQueryWrapper.eq("admin_password", user.getUserPassword());
            listUser = iUserService.list(sectionQueryWrapper);


            int userid = listUser.get(0).getUserId();
            String account = listUser.get(0).getUserAccount();
            String password = listUser.get(0).getUserPassword();
            String email = listUser.get(0).getEmail();
            String userLastName = listUser.get(0).getUserLastname();
            String userFirstName = listUser.get(0).getUserFirstname();
            String address = listUser.get(0).getAddress();
            int age =  listUser.get(0).getAge();
            String phoneNumber = listUser.get(0).getPhoneNumber();
            String question = listUser.get(0).getUserQuestion();
            String userSafeKey = listUser.get(0).getUserSafeKey();



            body.put("userid", userid);
            body.put("username", account);
            body.put("password", password);
            body.put("email", email);
            body.put("userFirstName",userFirstName);
            body.put("userLastName",userLastName);
            body.put("address",address);
            body.put("age",age);
            body.put("phoneNumber",phoneNumber);
            body.put("question",question);
            body.put("userSafeKey",userSafeKey);

            return "redirect:index";

        }


    }

    @RequestMapping("/sendEmail")
    public ModelAndView sendToUserEmail(@RequestParam String email){
        sendEmailService.sendEmail(email,"HI!","Test email from Vacbook!");
        System.out.println("sent reject email success!");
        ModelAndView modelAndView = new ModelAndView( "userPages/emailConfirmation");
        return modelAndView;

    }

    @PostMapping ("/checkboxDone")
    public ModelAndView checkboxDone(){
        ModelAndView modelAndView = new ModelAndView( "userPages/userRegister");
        return modelAndView;

    }

    @GetMapping("/logout")
    public String logout(Map<Object, Object> map){
        map.put("userid", "");
        map.put("username", "");
        return "redirect:index";// 重定向
    }


}