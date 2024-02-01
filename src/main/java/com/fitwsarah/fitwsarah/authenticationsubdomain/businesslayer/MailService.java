package com.fitwsarah.fitwsarah.authenticationsubdomain.businesslayer;


import com.fitwsarah.fitwsarah.authenticationsubdomain.datalayer.MailStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendMail(String email, MailStructure mailStructure){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromEmail);
        simpleMailMessage.setSubject(mailStructure.getSubject());
        simpleMailMessage.setText(mailStructure.getMessage());
        simpleMailMessage.setTo(email);

        javaMailSender.send(simpleMailMessage);
    }
}
