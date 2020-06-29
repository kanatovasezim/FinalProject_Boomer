package it.academy.FinalProject.Service;

import java.util.List;

public interface MailSenderService {
    void send(List<String> to, String subject, String msg);
}
