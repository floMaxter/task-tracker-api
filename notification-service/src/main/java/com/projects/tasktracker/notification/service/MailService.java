package com.projects.tasktracker.notification.service;

import com.projects.tasktracker.notification.config.props.ApplicationMailProperties;
import com.projects.tasktracker.notification.exeption.NonRetryableException;
import com.projects.tasktracker.notification.exeption.RetryableException;
import jakarta.mail.MessagingException;
import jakarta.mail.SendFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.angus.mail.util.MailConnectException;
import org.springframework.mail.MailSendException;
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
        } catch (SendFailedException ex) {
            log.error("Invalid email address: {}", destinationEmail);
            throw new NonRetryableException(ex);
        } catch (MailConnectException ex) {
            log.error("Temporary mail server issue");
            throw new RetryableException(ex);
        } catch (MailSendException ex) {
            log.error("Mail server is unreachable or misconfigured: {}", ex.getMessage());
            throw new NonRetryableException(ex);
        } catch (MessagingException ex) {
            log.error("Unexpected messaging error while sending email to {}: {}", destinationEmail, ex.getMessage());
            throw new NonRetryableException(ex);
        }
    }
}
