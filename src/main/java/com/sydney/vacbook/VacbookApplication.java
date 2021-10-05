package com.sydney.vacbook;

import com.sydney.vacbook.service.SendEmailService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@MapperScan("com.sydney.vacbook.mapper")
public class VacbookApplication {

    public static void main(String[] args) {
        SpringApplication.run(VacbookApplication.class, args);
    }

}
