package com.van.lily.framework.core.tools;


import com.van.lily.commons.config.VanLilyConfigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
public class SmsTool {

    @Autowired
    private JavaMailSender sender;

    /**
    * 发送短信，借助 twill io
    */
    public void sendMsg(String to, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(VanLilyConfigure.OFFICIAL_EMAILS[0]);
        message.setText(content);
        message.setSubject(content);
        System.out.println(content);
        sender.send(message);
    }
}