package com.example.parking.reservation;

import com.example.parking.reservation.dto.ReservationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class EmailService {

    public void sendConfirmation(String toEmail, String subject, ReservationDTO reservationDTO) throws Exception{
        final String mail = "soosroland3@gmail.com";
        final String pass = "Milka_choco**378";

        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mail, pass);
            }
        };

        Session session = Session.getInstance(props, auth);

        createEmail(session, toEmail,subject, createMessage(reservationDTO));
    }
    public void sendUpdate(String toEmail, String subject, ReservationDTO reservationDTO)throws Exception{
        final String mail = "soosroland3@gmail.com";
        final String pass = "Milka_choco**378";

        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mail, pass);
            }
        };

        Session session = Session.getInstance(props, auth);
        createEmail(session, toEmail,subject, createMessageOfUpdate(reservationDTO));
    }

    private String createMessage(ReservationDTO reservationDTO){
        StringBuilder message=new StringBuilder();
        message.append("Thank you for choosing our parking lot!\n")
                .append("This email is a confirmation for your reservation. \n")
                .append("Spot: ").append(reservationDTO.getSpotNumber()).append("\n")
                .append("Date: ").append(reservationDTO.getDate()).append("\n")
                .append("Time: ").append(reservationDTO.getTime()).append("\n")
                .append("Fee: ").append(reservationDTO.getFee()).append("\n")
                .append("Have a nice day!");
        return message.toString();
    }

    private String createMessageOfUpdate(ReservationDTO reservationDTO){
        StringBuilder message=new StringBuilder();
        message.append("Thank you for choosing our parking lot!\n")
                .append("This email is an update of your reservation. Only the spot number has been changed. \n")
                .append("Spot: ").append(reservationDTO.getSpotNumber()).append("\n")
                .append("Date: ").append(reservationDTO.getDate()).append("\n")
                .append("Time: ").append(reservationDTO.getTime()).append("\n")
                .append("Fee: ").append(reservationDTO.getFee()).append("\n")
                .append("Have a nice day!");
        return message.toString();
    }

    private void createEmail(Session session, String toEmail, String subject, String message) throws Exception{
        MimeMessage msg = new MimeMessage(session);
        //set message headers
        msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
        msg.addHeader("format", "flowed");
        msg.addHeader("Content-Transfer-Encoding", "8bit");

        msg.setFrom(new InternetAddress("soosroland3@gmail.com", "Soos Roland"));
        msg.setReplyTo(InternetAddress.parse(toEmail, false));
        msg.setSubject(subject, "UTF-8");
        msg.setText(message, "UTF-8");
        msg.setSentDate(new Date());
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

        Transport.send(msg);

    }
}
