package appointmentcalendar.controller.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import appointmentcalendar.model.Data;

/**
 * RemoveBreakTime.
 */
public class RemoveBreakTime extends Action {

	/*
	 * (non-Javadoc)
	 * @see appointmentcalendar.controller.actions.Action#respond(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void respond(HttpServletRequest request, HttpServletResponse response) {
		Data data = new Data(request);
		String time = data.get("time");

		service.scheduleNonBreak(time);
	}

}
