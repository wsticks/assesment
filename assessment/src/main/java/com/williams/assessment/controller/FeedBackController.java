package com.williams.assessment.controller;

import com.williams.assessment.Configuration.EmailConfiguration;
import com.williams.assessment.FeedBack;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;

@RestController
@RequestMapping("/feedback")
public class FeedBackController {

    private EmailConfiguration emailConfiguration;

    public FeedBackController(EmailConfiguration emailConfiguration) {
        this.emailConfiguration = emailConfiguration;
    }

    @PostMapping
    public void sendFeedback(@RequestBody FeedBack feedBack,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new javax.validation.ValidationException("Feedback is not Valid");
        }

        // create mail sender
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.emailConfiguration.getHost());
        mailSender.setUsername(this.emailConfiguration.getUsername());
        mailSender.setPassword(this.emailConfiguration.getPassword());
        mailSender.setPort(this.emailConfiguration.getPort());

        //Create am email Instance
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(feedBack.getEmail());
        mailMessage.setTo("akintoyekolawole@gmail.com");
        mailMessage.setSubject("News from " + feedBack.getName());
        mailMessage.setText(feedBack.getFeedback());

        //send mail
        mailSender.send(mailMessage);

    }
}
