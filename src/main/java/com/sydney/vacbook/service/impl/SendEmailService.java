package com.sydney.vacbook.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Service
public class SendEmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String body, String topic){
        System.out.println("sending email!");
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject(topic);
        msg.setText(body);

        javaMailSender.send(msg);
        System.out.println("sent email...");
    }
}
