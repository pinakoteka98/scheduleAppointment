package appointmentcalendar.controller.actions;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import appointmentcalendar.model.Data;
import appointmentcalendar.model.Responder;
import appointmentcalendar.model.User;
import appointmentcalendar.model.dto.LoginResponseDto;

/**
 * Servlet implementation class Login
 */
public class Login extends Action {

	private static final Logger LOG = LogManager.getLogger();

	private LoginResponseDto dataTransfer;

	public Login() {
		dataTransfer = new LoginResponseDto();
	}

	@Override
	public void respond(HttpServletRequest request, HttpServletResponse response) {
		Data data = new Data(request);

		String email = data.get("login-email");
		String password = data.get("login-password");

		int responseCode = service.checkUserCredentials(email, password);

		switch (responseCode) {
			case 1:
				User user = service.getUser(email);
				service.login(user);

				dataTransfer.setFirstName(user.getFirstName());
				dataTransfer.setAppointments(service.getAppointmentsForUser(email));
				request.getSession().setAttribute("user", user);

				LOG.info("User logged in - " + user);
				break;
			case 2:
				LOG.info("User attempted login but the password was incorrect");
				break;
			case 3:
				LOG.info("User attempted login but the email is not registered");
				break;
			case 4:
				User admin = service.getUser(email);
				service.login(admin);

				response.addCookie(buildAdminCookie());

				LOG.info("Admin logged in - " + admin);
				break;
		}
		dataTransfer.setResponseCode(responseCode);
		Responder.send(dataTransfer, response);
	}

	/*
	 * Build an admin login cookie
	 */
	private Cookie buildAdminCookie() {
		Cookie cookie = new Cookie("admin", "true");
		cookie.setMaxAge(30 * 60);
		return cookie;
	}

}
