package com.sydney.vacbook.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sydney.vacbook.entity.*;
import com.sydney.vacbook.mapper.AdminMapper;
import com.sydney.vacbook.mapper.UserMapper;
import com.sydney.vacbook.service.*;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.*;

/**
 * @author shuonan wang
 * @since 2021-09-15
 */

@RestController
@RequestMapping("/vacbook/admin")
@Controller
public class AdminController {

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

    @Autowired
    VaccineController vaccineController;
    //一个adminList来判断登录合法性
    List<Admin> list = new ArrayList<>();

    @GetMapping("{admin_id}/dashboard")
    public Map<String, Object> fetchDashboard(@PathVariable("admin_id") int admin_id) {
        System.out.print(admin_id);
        Admin admin = iAdminService.getById(admin_id);

        QueryWrapper<Vaccine> findVaccineByAdminId = new QueryWrapper<>();
        findVaccineByAdminId.lambda().eq(Vaccine::getAdminId, admin_id);
        List<Vaccine> vaccineList = iVaccineService.list(findVaccineByAdminId);
        List<String> vaccineNames = new ArrayList<>();
        List<Integer> vaccineIds = new ArrayList<>();
        for (Vaccine vaccine : vaccineList) {
            vaccineNames.add(vaccine.getVaccineName());
            vaccineIds.add(vaccine.getVaccineId());
        }

        int bookingNum = 0;
        for (Integer vaccineId : vaccineIds) {
            QueryWrapper<Booking> findBookingByVaccineId = new QueryWrapper<>();
            findBookingByVaccineId.lambda().eq(Booking::getVaccineId, vaccineId);
            bookingNum += iBookingService.count(findBookingByVaccineId);
        }

        Location location = iLocationService.getById(admin.getLocationId());

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("account", admin.getAdminAccount());
        result.put("name", admin.getAdminName());
        result.put("location", location.getLocation());
        result.put("vaccines", vaccineNames);
        result.put("booking num", bookingNum);
        System.out.print(result);
        return result;
    }

    /**
     * @param admin_id
     * @param body     body can used to get reject booking request
     * @return
     */
    @PostMapping("/{admin_id}/bookings")
    public List<Booking> fetchBookings(@PathVariable("admin_id") int admin_id, @RequestBody Map<String, Object> body) {
        //TODO JAMES
        return null;
    }

    @GetMapping("/{admin_id}/booking/user/{user_id}")
    public User fetchBookingUser(@PathVariable("user_id") int user_id) {
        User user = iUserService.getById(user_id);
        return user;
    }

    /**
     * @param admin_id
     * @param body     body can used to get add, delete, update requests based on the design of figma
     * @return
     */
    @GetMapping("/{admin_id}/vaccines")
    public List<Vaccine> fetchVaccines(@PathVariable("admin_id") int admin_id, @RequestBody Map<String, Object> body) {
        //TODO ZHENGCHENG

        List<Vaccine> resultSet = vaccineController.getVaccineListByAdminId(admin_id);
        return resultSet;
    }

    @PutMapping("/{admin_id}/setting")
    public Map<String, Object> fetchAndUpdateSetting(@PathVariable("admin_id") int admin_id, @RequestBody Map<String, Object> body) {
        Admin admin = iAdminService.getById(admin_id);
        // if body has content, update admin information
        if (!body.isEmpty()) {
            System.out.println(body);
            admin.updateByMap(body);
            iAdminService.saveOrUpdate(admin);
        }
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("account", admin.getAdminAccount());
        result.put("name", admin.getAdminName());
        Location location = iLocationService.getById(admin.getLocationId());
        result.put("location", location.getLocation());
        List<Location> locationList = iLocationService.list();
        result.put("location_options", locationList);

        return result;
    }

    @PostMapping("/login")
    public String login(Admin admin, @RequestBody Map<String, Object> map) {
        //TODO WORDE
        QueryWrapper<Admin> sectionQueryWrapper = new QueryWrapper<>();
        sectionQueryWrapper.eq("admin_account", admin.getAdminAccount());
        sectionQueryWrapper.eq("admin_password", admin.getAdminPassword());
        list = iAdminService.list(sectionQueryWrapper);

        String str = list.toString();

        if (!str.equals("[]")) {

            map.put("adminList", list.get(0));
//下面写登录后想要获得的更多东西例如获取疫苗
            map.put("vaccineList", vaccineController.getVaccineListByAdminId(admin.getAdminId()));

            return "redirect:vacbook/admin/index.html";//重定向
        } else {

            return "redirect:vacbook/admin/index.html";//重定向
        }

    }

    @PostMapping("/register")
    public String register(Admin admin, Map<Object, Object> body) {
        //TODO WORDE
        boolean newAdmin = iAdminService.save(admin);
        if (newAdmin == false) {
            System.err.println("This account has been registered");
            return "redirect:/admin/index.jsp";//重定向
        } else {
            System.out.println("Thanks for join our system");

            QueryWrapper<Admin> sectionQueryWrapper = new QueryWrapper<>();
            sectionQueryWrapper.eq("admin_account", admin.getAdminAccount());
            sectionQueryWrapper.eq("admin_password", admin.getAdminPassword());
            list = iAdminService.list(sectionQueryWrapper);

            body.put("adminList", list.get(0));


            return "admin";
        }


    }

    @PostMapping("/logout")
    public String  logout(Map<Object, Object> map) {
        //TODO WORDE
        map.put("adminList","");
        return "redirect:vacbook/admin/index.html";// 重定向


    }


// exercises
//    @GetMapping("{id}/getUserByAge")
//    public List<User> getUserByAge(@PathVariable("id") int id, @RequestParam("age") int age) {
//        System.out.println("hellp");
//
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        //lambda写法:
//        queryWrapper.lambda().eq(User::getAge, age);
//
//
//        List<User> userList = iUserService.list(queryWrapper);
//        return userList;
//    }
//
//    @GetMapping("/getUserListByAgeLimit")
//    public List<User> getUserListByAgeLimit() {
//
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.lambda().gt(User::getAge, 1);
//        queryWrapper.lambda().lt(User::getAge, 25);
//
//        List<User> userList = iUserService.list(queryWrapper);
//        return userList;
//    }

//    @GetMapping("{id}/update")
//    public void updateAdmin(@PathVariable("id") int id) {
//        Admin admin = iAdminService.getById(id);
//        admin.setAdminName("super_kevin");
//        iAdminService.saveOrUpdate(admin);
//    }


}
