package com.tdtu.phonecommerce.service;

import org.thymeleaf.context.Context;

public interface EmailService {

    void sendEmailWithHtmlTemplate(String to, String subject, String templateName, Context context);


}
