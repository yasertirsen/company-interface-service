package com.fyp.companyinterfaceservice.service;

import com.fyp.companyinterfaceservice.model.NotificationEmail;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class MailContentBuilder {

    private final TemplateEngine templateEngine;

    public String build(NotificationEmail email) {
        Context context = new Context();
        context.setVariable("message", email.getBody());
        context.setVariable("link", email.getLink());
        return templateEngine.process("emailTemplate", context);
    }
}
