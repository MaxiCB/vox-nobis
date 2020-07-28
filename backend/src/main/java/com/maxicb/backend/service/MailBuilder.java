package com.maxicb.backend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class MailBuilder {

    TemplateEngine templateEngine;

    String build(String message) {
        Context context = new Context();
        context.setVariable("body", message);
        return templateEngine.process("mailTemplate", context);
    }
}
