package appointmentcalendar.controller.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import appointmentcalendar.model.Data;

/**
 * SetTimeSlots.
 */
public class SetTimeSlots extends Action {

	public SetTimeSlots() {
	}

	/*
	 * (non-Javadoc)
	 * @see appointmentcalendar.controller.actions.Action#respond(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void respond(HttpServletRequest request, HttpServletResponse response) {
		Data data = new Data(request);

		String timeSlots = data.get("timeSlots");
		String day = data.get("day");

		service.setTimeSlots(timeSlots, day);
	}

}
