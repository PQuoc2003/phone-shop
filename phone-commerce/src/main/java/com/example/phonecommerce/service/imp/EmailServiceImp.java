package com.example.phonecommerce.service.imp;

import com.example.phonecommerce.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceImp implements EmailService {


    private final JavaMailSender emailSender;

    private final TemplateEngine templateEngine;

    @Autowired
    public EmailServiceImp(JavaMailSender emailSender, TemplateEngine templateEngine) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendEmailWithHtmlTemplate(String to, String subject, String templateName, Context context) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");

        try {
            mimeMessageHelper.setFrom("noreply@dpq.com");
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            String htmlContent = templateEngine.process(templateName, context);
            mimeMessageHelper.setText(htmlContent, true);
            emailSender.send(mimeMessage);


        } catch (MessagingException e) {
            // Exception, nothing.
        }


    }
}
