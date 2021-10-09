package com.sydney.vacbook.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sydney.vacbook.entity.*;
import com.sydney.vacbook.service.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

import static com.sydney.vacbook.tool.MD5.code;

/**
 * @author shuonan wang
 * @since 2021-09-15
 */

//@RestController   如果加了这行注释 return 只会返回return里的实际内容、而不会跳转网页
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
    //一个adminList来判断登录合法性 并且存储相关信息
    List<Admin> listAdmin = new ArrayList<>();

    @GetMapping("/dashboard")
    public ModelAndView fetchDashboard() {
        int admin_id = listAdmin.get(0).getAdminId();
        System.out.print(admin_id);

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

        Location location = iLocationService.getById(listAdmin.get(0).getLocationId());

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("account", listAdmin.get(0).getAdminAccount());
        result.put("name", listAdmin.get(0).getAdminName());
        result.put("location", location.getLocation());
        result.put("vaccines", vaccineNames);
        result.put("booking_num", bookingNum);
        System.out.print(result);
        ModelAndView modelAndView = new ModelAndView("adminPages/dashboard", "result", result);
        return modelAndView;
    }

    @GetMapping("/bookings")
    public ModelAndView fetchBookings() {
        //System.out.println("22222222");
        Integer admin_id = listAdmin.get(0).getAdminId();
        if (admin_id != null) {
            QueryWrapper<Vaccine> findVaccineByAdminId = new QueryWrapper<>();
            findVaccineByAdminId.eq("admin_id", listAdmin.get(0).getAdminId());

            List<Vaccine> vaccineList = iVaccineService.list(findVaccineByAdminId);
            if(vaccineList.isEmpty()){
                System.out.println("There is no booking Here");
                ModelAndView modelAndView = new ModelAndView("adminPages/adminBooking");
                return modelAndView;
            }
            List<String> vaccineNames = new ArrayList<>();
            List<Integer> vaccineIds = new ArrayList<>();
            Map<Integer, String> vaccineMap = new HashMap<>();
            for (Vaccine vaccine : vaccineList) {
                vaccineNames.add(vaccine.getVaccineName());
                vaccineIds.add(vaccine.getVaccineId());
                vaccineMap.put(vaccine.getVaccineId(), vaccine.getVaccineName());
            }
            QueryWrapper<Booking> queryWrapper = new QueryWrapper<>();
            queryWrapper.in("vaccine_id", vaccineIds);
            List<Booking> bookingList = iBookingService.list(queryWrapper);
            List<Booking> bookingList1 = new ArrayList<>();
            for (Booking booking : bookingList) {
                Booking b = new Booking();
                b.setBookingId(booking.getBookingId());
                b.setDate(booking.getDate());
                b.setPeriod(booking.getBookingTimezone());
                b.setVaccine(vaccineMap.get(booking.getVaccineId()));
                b.setUser(iUserService.getById(booking.getUserId()).getUserFirstname());
                b.setUserId(iUserService.getById(booking.getUserId()).getUserId());
                bookingList1.add(b);
            }
            ModelAndView modelAndView = new ModelAndView("adminPages/adminBooking", "bookingList1", bookingList1);
            return modelAndView;
        }
        return null;
    }

    @PostMapping("/bookings")
    public List<Booking> fetchBookings(@PathVariable("admin_id") int admin_id, @RequestBody Map<String, Object> body) {
        //TODO JAMES
        return null;
    }


    @GetMapping("/bookings/user/{user_id}")
    public ModelAndView fetchBookingUser(@PathVariable("user_id") int user_id) {
        User user = iUserService.getById(user_id);
        ModelAndView modelAndView = new ModelAndView("adminPages/booking_user", "result", user);
        return modelAndView;
    }

    /**
     * Vaccine can add, delete, update requests based on the design of figma
     */
    @GetMapping("/vaccines")
    public ModelAndView fetchVaccines() {
        List<Vaccine> resultSet = vaccineController.getVaccineListByAdminId(listAdmin.get(0).getAdminId());
        ModelAndView modelAndView = new ModelAndView("adminPages/adminVaccines", "adminVaccineList", resultSet);
        return modelAndView;
    }

    @PostMapping("/vaccines/update")
    public ModelAndView updateVaccine(@RequestParam Integer stock, Integer update_id) {
        System.out.println(stock);
        System.out.println(update_id);
        if (update_id != null && stock != null) {
            System.out.println("update vaccine");
            Vaccine vaccine = iVaccineService.getById(update_id);
            vaccine.setVaccineAmount(stock);
            iVaccineService.saveOrUpdate(vaccine);
            System.out.println("change success");
        }
        return fetchVaccines();
    }

    @PostMapping("/vaccines/delete")
    public ModelAndView deleteVaccine(@RequestParam Integer delete_id) {
        System.out.println(delete_id);
        if(delete_id!=null) {
            System.out.println("delete vaccine");
            Vaccine vaccine = iVaccineService.getById(delete_id);
            vaccineController.delVaccine(vaccine);
            System.out.println("delete success");
        }
        return fetchVaccines();
    }

    @PostMapping("/vaccines/add")
    public ModelAndView addVaccine(@RequestParam String name, String type, Integer amount) {
        if (amount != null && name != null && type != null) {
            System.out.println("add vaccine");
            Vaccine vaccine = new Vaccine();
            vaccine.setVaccineName(name);
            vaccine.setVaccineType(type);
            vaccine.setVaccineAmount(amount);
            vaccine.setVaccineDescription(" ");
            vaccine.setAdminId(listAdmin.get(0).getAdminId());
            iVaccineService.save(vaccine);
            System.out.println("add success");
        }
        return fetchVaccines();
    }

    @GetMapping("/setting")
    public ModelAndView fetchSetting() {

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("admin_id", listAdmin.get(0).getAdminId());
        result.put("account", listAdmin.get(0).getAdminAccount());
        result.put("name", listAdmin.get(0).getAdminName());
        Location location = iLocationService.getById(listAdmin.get(0).getLocationId());
        result.put("location_name", location.getLocation());
        result.put("location_id", location.getLocationId());
        List<Location> locationList = iLocationService.list();
        result.put("location_options", locationList);

        ModelAndView modelAndView = new ModelAndView("adminPages/setting", "result", result);
        return modelAndView;
    }

    @PostMapping(value = "/setting")
    public ModelAndView updateSetting(@RequestParam String name, String password, Integer location) {

        System.out.println("hello");
        System.out.println(name);
        System.out.println(password);
        System.out.println(location);


        Admin admin = listAdmin.get(0);
        admin.setAdminName(name);
        admin.setLocationId(location);
        if (password != null && password != " " && !password.isEmpty()) {
            String passwordMD5 = code(password);
//            setting password protect
            System.out.println(password + ".");
            admin.setAdminPassword(passwordMD5);
        }

        //admin.updateByMap(body);
        iAdminService.saveOrUpdate(admin);
        return this.fetchSetting();
    }

    //这个地方改成index了
    @RequestMapping("/index")
    public String index() {
        return "adminPages/adminLogin";
    }

    //跳转到login  --- 但是现在登录成功的return我有点迷惑 报500错误
    @RequestMapping("/login")
    public String login(@RequestParam String account, String password, Map<String, Object> map) {
        System.out.println("1111111111111111111111111111");
        //TODO WORDE
        String MD5Password = code(password);
        QueryWrapper<Admin> sectionQueryWrapper = new QueryWrapper<>();
        sectionQueryWrapper.eq("admin_account", account);
        sectionQueryWrapper.eq("admin_password", MD5Password);
        listAdmin = iAdminService.list(sectionQueryWrapper);

        String str = listAdmin.toString();

        if (!str.equals("[]")) {

            map.put("adminList", listAdmin.get(0));
//下面写登录后想要获得的更多东西例如获取疫苗
            // map.put("vaccineList", vaccineController.getVaccineListByAdminId(admin.getAdminId()));

            return "adminPages/base";//重定向
        } else {

            return "adminPages/adminLogin";//重定向
        }

    }

    @RequestMapping("/registerPage")
    public ModelAndView registerPage() {
        Map<String, Object> result = new LinkedHashMap<>();
        List<Location> locationList = iLocationService.list();
        result.put("location_options", locationList);
        ModelAndView modelAndView = new ModelAndView("adminPages/adminRegister", "result", result);
        return modelAndView;
    }

    @RequestMapping("/register")
    public String register(Admin admin, Map<Object, Object> body) {

        String MD5Password = code(admin.getAdminPassword());
        admin.setAdminPassword(MD5Password);
        QueryWrapper<Admin> checkQueryWrapper = new QueryWrapper<>();
        checkQueryWrapper.eq("admin_account", admin.getAdminAccount());
        checkQueryWrapper.eq("admin_name", admin.getAdminName());
        if (iAdminService.getOne(checkQueryWrapper)!=null){
            System.err.println("This account has been registered");
            return "redirect:registerPage";//重定向
        }

        System.out.println("===============");
        boolean newAdmin = iAdminService.save(admin);
        if (newAdmin == false) {
            System.err.println("This account has been registered");
            return "redirect:index";//重定向
        } else {
            System.out.println("Thanks for join our system");

            QueryWrapper<Admin> sectionQueryWrapper = new QueryWrapper<>();
            sectionQueryWrapper.eq("admin_account", admin.getAdminAccount());
            sectionQueryWrapper.eq("admin_password", admin.getAdminPassword());
            listAdmin = iAdminService.list(sectionQueryWrapper);

            body.put("adminList", listAdmin.get(0));


            return "adminPages/base";
        }


    }

    @RequestMapping("/logout")
    public String logout(Map<Object, Object> map) {
        map.put("adminList", "");
        return "redirect:index";// 重定向
    }

//    取信息
//    @RequestMapping("/adminList")
//    public String  listAdmin(Model model) {
//       model.addAttribute("listAdmin",listAdmin.get(0));
//       return "admin/list";
//
//    }


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
