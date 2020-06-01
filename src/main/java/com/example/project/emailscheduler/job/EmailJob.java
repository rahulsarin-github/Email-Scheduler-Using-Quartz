package com.example.project.emailscheduler.job;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Component
public class EmailJob extends QuartzJobBean {
    private static final Logger logger = LoggerFactory.getLogger(EmailJob.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailProperties mailProperties;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("Executing Job with key {}", jobExecutionContext.getJobDetail().getKey());

        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        String subject = jobDataMap.getString("subject");
        String body = jobDataMap.getString("body");
        String recipientEmail = jobDataMap.getString("email");

        sendMail(mailProperties.getUsername(), recipientEmail, subject, body);
    }

    private void sendMail(String fromEmail, String toEmail, String subject, String body) {
        try {
            logger.info("Sending Email to {}", toEmail);
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper messageHelper = new MimeMessageHelper(message, StandardCharsets.UTF_8.toString());
            //MimeBodyPart part = new MimeBodyPart();
            //part.attachFile(file);
//            Message message = new MimeMessage(session);
//            Multipart multipart = new MimeMultipart();
//             
//            // creates body part for the message
//            MimeBodyPart messageBodyPart = new MimeBodyPart();
//            messageBodyPart.setContent(message, "text/html");
//             
//            // creates body part for the attachment
//            MimeBodyPart attachPart = new MimeBodyPart();
//             
//            // code to add attachment...will be revealed later
//             
//            // adds parts to the multipart
//            multipart.addBodyPart(messageBodyPart);
//            multipart.addBodyPart(attachPart);
//             
//            // sets the multipart as message's content
//            message.setContent(multipart);
            
            
            messageHelper.setSubject(subject);
            messageHelper.setText(body, true);
            messageHelper.setFrom(fromEmail);
            messageHelper.setTo(toEmail);

            mailSender.send(message);
        } catch (MessagingException ex) {
            logger.error("Failed to send email to {}", toEmail);
        }
    }
}
