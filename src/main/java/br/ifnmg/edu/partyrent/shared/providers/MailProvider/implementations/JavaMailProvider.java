package br.ifnmg.edu.partyrent.shared.providers.MailProvider.implementations;

import br.ifnmg.edu.partyrent.shared.providers.MailProvider.MailProviderContract;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class JavaMailProvider implements MailProviderContract {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public boolean sendMail(String to, String subject, String htmlTemplate) throws MessagingException {
        var message = javaMailSender.createMimeMessage();

        message.setFrom("no-reply@partyrent.com");
        message.setRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject(subject);
        message.setContent(htmlTemplate, "text/html; charset=utf-8");

        try {
            this.javaMailSender.send(message);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
