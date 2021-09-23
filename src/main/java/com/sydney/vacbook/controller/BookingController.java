package com.sydney.vacbook.controller;


import com.sydney.vacbook.entity.Booking;
import com.sydney.vacbook.mapper.BookingMapper;
import com.sydney.vacbook.service.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
    private IBookingService bookingService;
//理论上insert不需要的ID的 数据库里已经设置的ID自增 test里测试似乎也不需要ID
//    框架里写的是动态sql
//    尝试用service层的接口
    @GetMapping("/add")
    public boolean bookingAdd(Booking booking){
        boolean save = bookingService.save(booking);

        //int i = bookingMapper.insert(booking);
        return save;
    }
    //删除booking by id
    @GetMapping("/delete")
    public int bookingDelete(Booking booking){
        int i = bookingMapper.deleteById(booking);
        return i;
    }
    //更新booking by id
    @GetMapping("/update")
    public int bookingUpdate(Booking booking){
        int i = bookingMapper.updateById(booking);
        return i;
    }

    @PostMapping("/fetch")
    public void fetchBooking(Booking booking){
        //todo
    }

    @GetMapping("/confirm")
    public int confirmBooking(Booking booking){
        int i = bookingMapper.updateById(booking);
        return i;
    }


    @GetMapping("/edit/{id}")
    public void editBooking(Booking booking){
        int i = bookingMapper.updateById(booking);
    }








}
