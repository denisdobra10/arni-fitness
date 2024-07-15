package com.dodera.arni_fitness.mail;

import com.dodera.arni_fitness.model.Membership;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendWelcomeMessage(String toEmail, String name) {
        String subject = "Bine ai venit!";
        String text = EmailTemplates.getWelcomeEmail(name);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("test@doderasoft.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public void sendPaymentMessage(String toEmail, String name, String paymentLink, Membership membership) {
        String subject = "Iti multumim pentru achizitie!";
        String text = EmailTemplates.getPaymentEmail(name, membership.getTitle(), membership.getPrice(), paymentLink);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("test@doderasoft.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public void sendTextMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("test@doderasoft.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}
