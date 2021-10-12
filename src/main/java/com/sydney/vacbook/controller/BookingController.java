package com.sydney.vacbook.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.sydney.vacbook.entity.*;
import com.sydney.vacbook.mapper.BookingMapper;
import com.sydney.vacbook.service.*;
import com.sydney.vacbook.service.impl.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
    @Autowired
    private SendEmailService sendEmailService;

    private JavaMailSender javaMailSender;

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
                booking.setVaccine_name(vaccine.getVaccineName());
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
        ibookingService.save(booking);
    }

    /**
     * reject method interface
     * @param booking
     * @return
     */
    @GetMapping("/reject/{bookingId}")
    public boolean reject(Booking booking){
        boolean confirmBooking = ibookingService.removeById(booking);
        return confirmBooking;
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

    @GetMapping("/user/{id}")
    public ModelAndView getUserBookingInfo(@PathVariable("id") int id){
        //find Booking info by userID
        QueryWrapper<Booking> findBookingByUserId = new QueryWrapper<>();
        findBookingByUserId.lambda().eq(Booking::getUserId, id);
        List<Booking> bookingList = ibookingService.list(findBookingByUserId);

        //get vaccine name by id
        Integer vaccineId = bookingList.get(0).getVaccineId();
        Vaccine vaccine = vaccineService.getById(vaccineId);

        //get user name by id
        Integer userId = bookingList.get(0).getUserId();
        User user = userService.getById(userId);

        //get Booking date and time
        String date = bookingList.get(0).getDate();
        String time = bookingList.get(0).getBookingTimezone();

        //get Location name
        Integer locationId = bookingList.get(0).getBookingId();
        Location location = locationService.getById(locationId);

        // new map to front end
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("Vaccine", vaccine.getVaccineName());
        result.put("firstName", user.getUserFirstname());
        result.put("lastName", user.getUserLastname());
        result.put("date", date);
        result.put("time", time);
        result.put("location", location.getLocation());

        ModelAndView modelAndView = new ModelAndView( "userPages/booking-edit","bookingInfo",result);
        return modelAndView;
    }

    @RequestMapping("/sendRejectEmail")
    public void sendRejectEmailToUser(@RequestParam Integer booking_id){
        // get reject booking list
        Booking booking = ibookingService.getById(booking_id);

        // get reject user info by booking list
        User user = userService.getById(booking.getUserId());

        //get vaccine name by booking list's vaccine ID
        Vaccine vaccine = vaccineService.getById(booking.getVaccineId());
        String vaccineName = vaccine.getVaccineName();

        //set email param
        String toEmail = user.getEmail();
        String topic = "Cancel Booking message from vacBook!";
        String msg = "Dear "+ user.getUserFirstname() +":\n"+
                "Sorry Your Booking has been reject!\n"+
                "Below are your booking details:\n\n"+
                "Booking user first name:       "+ user.getUserFirstname()+"\n"+
                "Booking user last name:        "+ user.getUserLastname()+"\n"+
                "Booking Date:                      "+ booking.getDate()+"\n"+
                "Booking period:                    "+ booking.getBookingTimezone()+"\n"+
                "Booked vaccine name:          "+ vaccineName+"\n\n"+
                "Finally, I once again apologize for canceling your vaccine appointment for some uncontrollable reasons.\n\n" +
                " You can log in to our VacBook system again to select a vaccine appointment for another time period.\n\n " +
                "If you have any questions about the above information, please don't hesitate to contact us: yanyukang29@gmail.com";

        sendEmailService.sendEmail(toEmail,msg,topic);
        System.out.println("sent reject email success!");
    }









}
