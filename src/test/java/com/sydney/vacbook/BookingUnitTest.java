package com.sydney.vacbook;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sydney.vacbook.controller.BookingController;
import com.sydney.vacbook.controller.VaccineController;
import com.sydney.vacbook.entity.*;
import com.sydney.vacbook.mapper.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BookingUnitTest {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private BookingMapper bookingMapper;
    @Autowired
    private VaccineMapper vaccineMapper;
    @Autowired
    private LocationMapper locationMapper;

    @Autowired
    private BookingController bookingController;
    @Autowired
    private VaccineController vaccineController;

    @Test
    void bookingRejectTest() {
        Booking booking = new Booking();
        booking.setBookingId(5); //数据库里的booking id
        bookingController.reject(booking);
    }

    @Test
    void bookingFetchTest() {
        Booking booking = new Booking();
        Vaccine vaccine = new Vaccine();
        booking.setUserId(48);
        booking.setVaccine_name("pfizer");
        booking.setVaccine("pfizer");
        vaccine.setVaccineName("pfizer");
        booking.setBookingId(2);
        booking.setVaccineId(3);
        booking.setBookingTimezone("08:00");
        booking.setDate("2021-10-27");
        bookingController.fetchBooking(booking);
    }
}