package appointmentcalendar.controller.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import appointmentcalendar.model.Data;
import appointmentcalendar.model.User;

public class CancelAppointment extends Action {

	@Override
	public void respond(HttpServletRequest request, HttpServletResponse response) {
		Data data = new Data(request);
		String appointment = data.get("appointment");

		User user = (User) request.getSession().getAttribute("user");

		service.cancelAppointment(appointment, user);
	}
}
