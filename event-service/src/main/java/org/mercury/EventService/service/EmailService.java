package org.mercury.EventService.service;

/**
 * @ClassName EmailService
 * @Description TODO
 * @Author katefu
 * @Date 12/15/23 10:35 AM
 * @Version 1.0
 **/
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final ResourceLoader resourceLoader;

    public EmailService(JavaMailSender mailSender, ResourceLoader resourceLoader) {
        this.mailSender = mailSender;
        this.resourceLoader = resourceLoader;
    }

    public void sendEmail(String template, String to, String subject, Map<String, String> placeholders) {

        try {
            String content = loadEmailTemplate(template);
            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                content = content.replace("{{" + entry.getKey() + "}}", entry.getValue());
            }

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true); // Set to 'true' for HTML content

            mailSender.send(message);
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String loadEmailTemplate(String template) throws IOException {
        String location = "classpath:email-template-" + template + ".html";
        return StreamUtils.copyToString(
                resourceLoader.getResource(location).getInputStream(),
                StandardCharsets.UTF_8
        );
    }
}

