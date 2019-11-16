package appointmentcalendar.controller.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import appointmentcalendar.model.Data;
import appointmentcalendar.model.Responder;

/**
 * GetTimeSlots.
 */
public class GetTimeSlots extends Action {

	public GetTimeSlots() {
	}

	/*
	 * (non-Javadoc)
	 * @see appointmentcalendar.controller.actions.Action#respond(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void respond(HttpServletRequest request, HttpServletResponse response) {
		Data data = new Data(request);
		String day = data.get("day");

		Responder.send(service.getTimeSlots(day), response);
	}

}
