package com.sydney.vacbook;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sydney.vacbook.controller.AdminController;
import com.sydney.vacbook.controller.UserController;
import com.sydney.vacbook.entity.*;
import com.sydney.vacbook.mapper.*;
import com.sydney.vacbook.service.IAdminService;
import com.sydney.vacbook.service.ILocationService;
import com.sydney.vacbook.service.IUserService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class AdminUnitTest {

    @Autowired
    private ILocationService iLocationService;

    @Autowired
    private IAdminService iAdminService;

    @Autowired
    private IUserService iUserService;

    @Autowired
    AdminController adminController;

    void login(){
        Admin admin = iAdminService.list().get(0);
        String account = admin.getAdminAccount();
        Map<String, Object> map = new HashMap<String, Object>();
        adminController.login(account, "1234",map);
    }

    @Test
    void loginTest(){
        Admin admin = iAdminService.list().get(0);
        String account = admin.getAdminAccount();
        Map<String, Object> map = new HashMap<String, Object>();

        //test wrong account, password
        assertFalse(adminController.login(account, "123",map));
        //test correct account, password
        assertTrue(adminController.login(account, "1234",map));
    }

    @Test
    void fetchDashboardTest(){
        login();
        Admin admin = iAdminService.list().get(0);
        System.out.println(admin);
        ModelAndView modelAndView = adminController.fetchDashboard();
        Map<String, Object> map = modelAndView.getModel();
        LinkedHashMap result =  (LinkedHashMap) map.get("result");
        System.out.println(result);
        assertTrue((result.get("account").equals(admin.getAdminAccount())));
        assertTrue((result.get("name").equals(admin.getAdminName())));
        String LocationName = iLocationService.getById(admin.getLocationId()).getLocation();
        assertTrue((result.get("location").equals(LocationName)));
    }

    @Test
    void fetchSettingTest(){
        login();
        Admin admin = iAdminService.list().get(0);
        System.out.println(admin);
        ModelAndView modelAndView = adminController.fetchSetting();
        Map<String, Object> map = modelAndView.getModel();
        LinkedHashMap result =  (LinkedHashMap) map.get("result");
        System.out.println(result);
        assertTrue((result.get("account").equals(admin.getAdminAccount())));
        assertTrue((result.get("name").equals(admin.getAdminName())));
        String LocationName = iLocationService.getById(admin.getLocationId()).getLocation();
        assertTrue((result.get("location_name").equals(LocationName)));
    }

    @Test
    void updateSettingTest(){
        login();
        Admin admin = iAdminService.list().get(0);
        System.out.println(admin);

        String name = "change";
        String password = "change";
        Integer locationId = iAdminService.list().get(1).getLocationId();
        String LocationName = iLocationService.getById( locationId).getLocation();

        ModelAndView modelAndView = adminController.updateSetting(name, password, locationId);
        Map<String, Object> map = modelAndView.getModel();
        LinkedHashMap result =  (LinkedHashMap) map.get("result");
        System.out.println(result);
        assertTrue((result.get("account").equals(admin.getAdminAccount())));
        assertTrue((result.get("name").equals(name)));
        assertTrue((result.get("location_name").equals(LocationName)));
    }

    @Test
    void fetchBookingUserTest(){
        login();
        User user = iUserService.list().get(0);
        ModelAndView modelAndView = adminController.fetchBookingUser(user.getUserId());
        Map<String, Object> map = modelAndView.getModel();
        User result = (User) map.get("result");
        System.out.println(result);
        assertTrue(result.equals(user));
    }

    @Test
    void registerTest(){
        Admin admin = new Admin(null,"testAccount","123","test1",7);
        Map<Object, Object> map = new HashMap<Object, Object>();
        adminController.register(admin, map);
    }
}