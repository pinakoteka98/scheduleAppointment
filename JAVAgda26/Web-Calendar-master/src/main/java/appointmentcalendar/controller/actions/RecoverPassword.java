package appointmentcalendar.controller.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import appointmentcalendar.model.Data;
import appointmentcalendar.model.Responder;
import appointmentcalendar.model.User;
import appointmentcalendar.model.dto.ResponseCodeDto;
import appointmentcalendar.utils.Email;
import appointmentcalendar.utils.EmailTemplate;

/**
 * Servlet implementation class RecoverPassword
 */
public class RecoverPassword extends Action {

	@Override
	public void respond(HttpServletRequest request, HttpServletResponse response) {
		Data data = new Data(request);
		String email = data.get("recovery-email");

		User user = service.getUser(email);

		int responseCode = 2;

		if (user.getEmail() != null) {
			if (user.getEmail().equals(email)) {
				String message = EmailTemplate.passwordRecovery(email, user.getPassword());
				Email.send(email, "Password Recovery", message);

				responseCode = 1;
			}
		}

		ResponseCodeDto responseDto = new ResponseCodeDto();
		responseDto.setResponseCode(responseCode);

		Responder.send(responseDto, response);

	}

}
