package com.gcs.controller;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {
	public static void send(String emailId,String content,String pwd,String userName) {
		System.out.println("emailId ="+emailId);
		System.out.println("content ="+content);
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("vardhamansurvey@gmail.com","vardhaman123");
				}
			});

		try {
			
			//Vendor1@gamail.com&campId=1
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("geminipocteam@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(emailId));			
			message.setSubject(" Vardhaman Survey ");
			message.setText("Dear "+userName+", \n\n Vardhman created a survey for you. Please open the below URL to opt for the survey : \n\n"+content+"\n\n "
					+ "Please use this OTP "+pwd+" to open the survey. \n\n Please participate in the survey and make this survey a success one.");

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}