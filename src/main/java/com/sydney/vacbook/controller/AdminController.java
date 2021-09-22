package com.sydney.vacbook.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
 *
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

    @GetMapping("{id}/dashboard")
    public Map<String, Object> fetch(@PathVariable("id") int id) {
        System.out.print(id);
        Admin admin = iAdminService.getById(id);

        QueryWrapper<Vaccine> findVaccineByAdminId  = new QueryWrapper<>();
        findVaccineByAdminId .lambda().eq(Vaccine::getAdminId, id);
        List<Vaccine> vaccineList = iVaccineService.list(findVaccineByAdminId);
        List<String> vaccineNames = new ArrayList<>();
        List<Integer> vaccineIds = new ArrayList<>();
        for(Vaccine vaccine : vaccineList){
            vaccineNames.add(vaccine.getVaccineName());
            vaccineIds.add(vaccine.getVaccineId());
        }

        int bookingNum = 0;
        for(Integer vaccineId : vaccineIds){
            QueryWrapper<Booking> findBookingByVaccineId= new QueryWrapper<>();
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

    @GetMapping("{id}/update")
    public void updateAdmin(@PathVariable("id") int id){
        Admin admin = iAdminService.getById(id);
        admin.setAdminName("super_kevin");
        iAdminService.saveOrUpdate(admin);
    }

    @GetMapping("{id}/getUserByAge")
    public List<User> getUserByAge(@PathVariable("id") int id, @RequestParam("age") int age) {
        System.out.println("hellp");

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //lambda写法:
        queryWrapper.lambda().eq(User::getAge, age);


        List<User> userList = iUserService.list(queryWrapper);
        return userList;
    }

    @GetMapping("/getUserListByAgeLimit")
    public List<User> getUserListByAgeLimit() {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().gt(User::getAge, 1);
        queryWrapper.lambda().lt(User::getAge, 25);

        List<User> userList = iUserService.list(queryWrapper);
        return userList;
    }

//
//    @GetMapping("login")
//    public String getLogin(Admin admin, Map<Object, Object> map) {
//
//        //System.out.println(admin);
//
//        //调用管理员查询方法
//        list=iAdminService.list();
//        String str=list.toString();
//
//        //System.out.println(list);
//
//        if (!str.equals("[]")) {
//
//            map.put("adminList", list.get(0));
////            plateController.getPlate(map); 得到admin下面的疫苗表
////            UserController.getUser(map);得到admin下面的预定用户
//
//            return "redirect:index";//重定向
//        } else {
//
//            return "redirect:index";//重定向
//        }
//    }


}
