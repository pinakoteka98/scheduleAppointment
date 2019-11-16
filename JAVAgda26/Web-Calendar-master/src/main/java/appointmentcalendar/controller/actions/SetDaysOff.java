package appointmentcalendar.controller.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import appointmentcalendar.model.Data;

/**
 * SetDaysOff.
 */
public class SetDaysOff extends Action {

	/*
	 * (non-Javadoc)
	 * @see appointmentcalendar.controller.actions.Action#respond(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void respond(HttpServletRequest request, HttpServletResponse response) {
		Data data = new Data(request);
		String daysOff = data.get("daysOff");
		String workDays = data.get("workDays");

		service.scheduleDayOff(daysOff);
		service.scheduleWorkDay(workDays);
	}

}
