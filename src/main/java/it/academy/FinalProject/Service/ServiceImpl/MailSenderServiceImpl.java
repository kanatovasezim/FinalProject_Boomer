package it.academy.FinalProject.Service.ServiceImpl;

import it.academy.FinalProject.Service.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailSenderServiceImpl implements MailSenderService {
    @Autowired
    private JavaMailSender mailSender;
    @Override
    public void send( List<String> to, String subject, String msg) {
        for (String s: to) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("boomer.platform@gmail.com");
            message.setTo(s);
            message.setSubject(subject);
            message.setText(msg);
            mailSender.send(message);
        }
    }
}
