package it.aulab.progetto_finale.services;

public interface EmailService {
    void sendSimpleEmail(String to, String subject, String text);
}
