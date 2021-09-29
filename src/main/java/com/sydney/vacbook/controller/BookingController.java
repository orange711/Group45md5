package com.sydney.vacbook.controller;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.sydney.vacbook.entity.*;
import com.sydney.vacbook.mapper.BookingMapper;
import com.sydney.vacbook.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Group45
 * @since 2021-09-11
 */
@RestController
@RequestMapping("/vacbook/booking")
@Controller
public class BookingController {
    @Autowired
    private BookingMapper bookingMapper;
    @Autowired
    private IBookingService ibookingService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IVaccineService vaccineService;
    @Autowired
    private IAdminService adminService;
    @Autowired
    private ILocationService locationService;

//    理论上insert不需要的ID的 数据库里已经设置的ID自增 test里测试似乎也不需要ID
//    框架里写的是动态sql
//    尝试用service层的接口
    @GetMapping("/add")
    public boolean bookingAdd(Booking booking){
        boolean bookingAdd = ibookingService.save(booking);
        return bookingAdd;
    }

    //删除booking by id
    @GetMapping("/delete")
    public boolean bookingDelete(Booking booking){
        boolean bookingDelete = ibookingService.removeById(booking);
        return bookingDelete;
    }
    //更新booking by id
    @GetMapping("/update")
    public boolean bookingUpdate(Booking booking){
        boolean bookingUpdate = ibookingService.updateById(booking);
        return bookingUpdate;
    }
    //取到用户所选择的数据
    @PostMapping("/fetch")
    public void fetchBooking(@RequestBody Booking booking){
        if(booking.getVaccineId()!=null){
            Vaccine vaccine = vaccineService.getById(booking.getVaccineId());
            System.out.println("Vaccine:"+vaccine.getVaccineName());
            if(vaccine!=null){
                Admin admin = adminService.getById(vaccine.getAdminId());
                if(admin!=null){
                    Location location = locationService.getById(admin.getLocationId());
                    System.out.println("Location:"+location.getLocation());
                }
            }
        }
        if(booking.getUserId()!=null){
            User user = userService.getById(booking.getUserId());
            System.out.println("Name:"+user.getUserFirstname()+" "+user.getUserLastname());
        }
        if(booking.getBookingId()!=null){
            Booking booking1 = ibookingService.getById(booking.getBookingId());
            System.out.println("BookingTimezone:"+booking1.getBookingTimezone());
        }
        System.out.println("Date & Time:"+booking.getDate()+" "+booking.getTime());

    }

    @GetMapping("/confirm")
    public boolean confirmBooking(Booking booking){
        boolean confirmBooking = ibookingService.updateById(booking);
        return confirmBooking;
    }


    @GetMapping("/edit/{id}")
    public boolean editBooking(Booking booking){
        boolean editBooking = ibookingService.updateById(booking);
        return editBooking;
    }









}
