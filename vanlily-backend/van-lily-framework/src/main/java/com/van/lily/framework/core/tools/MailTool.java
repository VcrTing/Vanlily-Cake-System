package com.van.lily.framework.core.tools;


import com.van.lily.commons.config.VanLilyConfigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
public class MailTool {

    @Autowired
    private JavaMailSender sender;

    /**
     * 发送简单电邮
     */
    public void sendMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(VanLilyConfigure.OFFICIAL_EMAILS[0]);
        message.setText(content);
        message.setSubject(subject);
        sender.send(message);
    }

    /**
    * 发送 HTML
    */
    public void sendHtml(String to, String subject, String html) {
        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(VanLilyConfigure.OFFICIAL_EMAILS[0]);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);
            sender.send(message);
        } catch (Exception e) { }
    }
}