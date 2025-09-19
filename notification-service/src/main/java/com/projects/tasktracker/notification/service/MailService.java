package com.projects.tasktracker.notification.service;

import com.projects.tasktracker.notification.config.props.ApplicationMailProperties;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final ApplicationMailProperties applicationMailProperties;

    public void sendEmailMessage(String destinationEmail, String title, String message) {
        try {
            var mimeMessage = mailSender.createMimeMessage();
            var mimeMessageHelper = new MimeMessageHelper(
                    mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_RELATED,
                    UTF_8.name()
            );

            mimeMessageHelper.setFrom(applicationMailProperties.getFrom());
            mimeMessageHelper.setTo(destinationEmail);
            mimeMessageHelper.setSubject(title);
            mimeMessageHelper.setText(message);

            mailSender.send(mimeMessage);
        } catch (MessagingException ex) {
            log.error("Failed to send email to {}", destinationEmail, ex);
        }
    }
}
