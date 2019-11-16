package appointmentcalendar.controller.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import appointmentcalendar.model.Data;
import appointmentcalendar.model.Responder;
import appointmentcalendar.model.dto.ResponseCodeDto;

/**
 * Servlet implementation class Register
 */
public class Register extends Action {

	private static final Logger LOG = LogManager.getLogger();

	private ResponseCodeDto responseCodeDto;

	public Register() {
		responseCodeDto = new ResponseCodeDto();
	}

	@Override
	public void respond(HttpServletRequest request, HttpServletResponse response) {
		Data data = new Data(request);

		String firstName = data.get("reg-firstName");
		String lastName = data.get("reg-lastName");
		String email = data.get("reg-email");
		String password = data.get("reg-password");
		String access = data.get("reg-access");

		int responseCode = service.createUser(firstName, lastName, email, password, access);
		responseCodeDto.setResponseCode(responseCode);

		Responder.send(responseCodeDto, response);
	}

}
