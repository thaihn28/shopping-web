package com.example.shoppingweb.service;

import com.example.shoppingweb.model.NotificationEmail;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private MailContentBuilder mailContentBuilder;

    @Async //send mail take a lot of time to finish, so enable async
    public void sendMail(NotificationEmail notificationEmail) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("qque722@gmail.com");
            helper.setTo(notificationEmail.getRecipient());
            helper.setSubject(notificationEmail.getSubject());
            helper.setText(mailContentBuilder.build(notificationEmail.getBody()));

            mailSender.send(message);
            System.out.println("Activation mail send!!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }
}
