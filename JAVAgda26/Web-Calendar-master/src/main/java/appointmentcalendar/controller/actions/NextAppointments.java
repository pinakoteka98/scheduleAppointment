package appointmentcalendar.controller.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import appointmentcalendar.model.Responder;

/**
 * NextAppointments.
 */
public class NextAppointments extends Action {

	/*
	 * (non-Javadoc)
	 * @see appointmentcalendar.controller.actions.Action#respond(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void respond(HttpServletRequest request, HttpServletResponse response) {
		Responder.send(service.getNextAppointments(5), response);
	}

}
