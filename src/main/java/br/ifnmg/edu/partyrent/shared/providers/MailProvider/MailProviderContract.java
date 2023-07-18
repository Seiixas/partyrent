package br.ifnmg.edu.partyrent.shared.providers.MailProvider;

import jakarta.mail.MessagingException;

public interface MailProviderContract {
    public boolean sendMail(String to, String subject, String htmlTemplate) throws MessagingException;
}
