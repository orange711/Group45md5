package com.sydney.vacbook.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sydney.vacbook.entity.*;
import com.sydney.vacbook.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shuonan wang
 * @since 2021-09-15
 */

@RestController
@Controller
@RequestMapping("/vacbook/admin")
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
    public ModelAndView fetchDashboard(@PathVariable("admin_id") int admin_id) {
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
        result.put("booking_num", bookingNum);
        System.out.print(result);
        ModelAndView modelAndView = new ModelAndView( "adminPages/dashboard","result", result);
        return modelAndView;
    }

    /**
     * @param admin_id
     * @param body     body can used to get reject booking request
     * @return
     */
    @GetMapping("/{admin_id}/bookings")
    public List<Booking> fetchBookings(@PathVariable("admin_id") int admin_id) {
        Admin admin = iAdminService.getById(admin_id);
        if(admin!=null){
            QueryWrapper<Vaccine> findVaccineByAdminId = new QueryWrapper<>();
            List<Vaccine> vaccineList = iVaccineService.list(findVaccineByAdminId);
            List<String> vaccineNames = new ArrayList<>();
            List<Integer> vaccineIds = new ArrayList<>();
            for (Vaccine vaccine : vaccineList) {
                vaccineNames.add(vaccine.getVaccineName());
                vaccineIds.add(vaccine.getVaccineId());
            }
            QueryWrapper<Booking> queryWrapper = new QueryWrapper<>();
            queryWrapper.in("vaccine_id",vaccineIds);
            List<Booking> bookingList = iBookingService.list(queryWrapper);
            return bookingList;
        }
        return null;
    }

    @GetMapping("/{admin_id}/booking/user/{user_id}")
    public ModelAndView fetchBookingUser(@PathVariable("user_id") int user_id) {
        User user = iUserService.getById(user_id);
        ModelAndView modelAndView = new ModelAndView( "adminPages/booking_user","result", user);
        return modelAndView;
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

    @GetMapping("/{admin_id}/setting")
    public ModelAndView fetchSetting(@PathVariable("admin_id") int admin_id){
        Admin admin = iAdminService.getById(admin_id);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("admin_id", admin.getAdminId());
        result.put("account", admin.getAdminAccount());
        result.put("name", admin.getAdminName());
        Location location = iLocationService.getById(admin.getLocationId());
        result.put("location", location.getLocation());
        List<Location> locationList = iLocationService.list();
        result.put("location_options", locationList);

        ModelAndView modelAndView = new ModelAndView( "adminPages/setting","result", result);
        return modelAndView;
    }

    @PutMapping("/{admin_id}/setting")
    public boolean udateSetting( @PathVariable("admin_id") int admin_id, @RequestBody Map<String, Object> body) {
        //ModelAndView modelAndView = new ModelAndView( "adminPages/setting", new );
        //if body has content, update admin information
        System.out.println(body);
        Admin admin = iAdminService.getById(admin_id);
        if (!body.isEmpty()) {
            System.out.println(body);
            admin.updateByMap(body);
            iAdminService.saveOrUpdate(admin);
            return  true;
        }
        return false;
    }

    @PostMapping("/login")
    public String login(Admin admin, @RequestBody Map<String, Object> map) {
        //TODO WORDE
        System.out.println("==============");
        QueryWrapper<Admin> sectionQueryWrapper = new QueryWrapper<>();
        sectionQueryWrapper.eq("admin_account", admin.getAdminAccount());
        sectionQueryWrapper.eq("admin_password", admin.getAdminPassword());
        list = iAdminService.list(sectionQueryWrapper);

        String str = list.toString();

        if (!str.equals("[]")) {

            map.put("adminList", list.get(0));
//下面写登录后想要获得的更多东西例如获取疫苗
            map.put("vaccineList", vaccineController.getVaccineListByAdminId(admin.getAdminId()));

            return "index";//重定向
        } else {

            return "redirect:vacbook/admin/index.html";//重定向
        }

    }

    @GetMapping("/login")
    public ModelAndView getAdminLoginPage(){
        ModelAndView modelAndView = new ModelAndView( "adminPages/adminLogin");
        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView getAdminRegisterPage(){
        ModelAndView modelAndView = new ModelAndView( "adminPages/adminRegister");
        return modelAndView;
    }

    @PostMapping("/register")
    public String register(Admin admin, Map<Object, Object> body) {
        //TODO WORDE
        boolean newAdmin = iAdminService.save(admin);
        if (newAdmin == false) {
            System.err.println("This account has been registered");
            return "redirect:/admin/index.html";//重定向
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
