package appointmentcalendar.controller.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import appointmentcalendar.model.Responder;

/**
 * Servlet implementation class DayInit
 */
public class DayInit extends Action {

	@Override
	public void respond(HttpServletRequest request, HttpServletResponse response) {
		Responder.send(service.getAvailableDays(), response);
	}

}
