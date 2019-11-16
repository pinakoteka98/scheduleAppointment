package appointmentcalendar.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Email.
 */
public class Email {

	private static Logger LOG = LogManager.getLogger();

	private static final String FROM = "evan@best-ev.club";
	private static final String HOST = "xjdz4.dailyrazor.com";
	private static final String USERNAME = "evan@best-ev.club";
	private static final String PASSWORD = "GSxDb%s[NND2";
	private static final String PORT = "465";
	private static final String AUTH = "true";

	public Email() {
	}

	/**
	 * Send an email
	 * 
	 * @param recipient
	 * @param subject
	 * @param message
	 */
	public static void send(String recipient, String subject, String message) {
		Properties properties = System.getProperties();

		properties.setProperty("mail.smtp.host", HOST);
		properties.setProperty("mail.smtp.port", PORT);
		properties.setProperty("mail.smtp.auth", AUTH);
		properties.setProperty("mail.smtp.socketFactory.port", PORT);
		properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Session session = Session.getDefaultInstance(properties, new Authenticator() {
			private PasswordAuthentication pa = new PasswordAuthentication(USERNAME, PASSWORD);

			@Override
			public PasswordAuthentication getPasswordAuthentication() {
				return pa;
			}
		});

		try {
			MimeMessage email = new MimeMessage(session);

			email.setFrom(new InternetAddress(FROM));
			email.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

			email.setSubject(subject);
			email.setContent(message, "text/html");

			Transport.send(email);

			LOG.info("Email sent to " + recipient);
			System.out.println("email sent successfully");

		} catch (MessagingException e) {
			LOG.error(e);
		}
	}

}
