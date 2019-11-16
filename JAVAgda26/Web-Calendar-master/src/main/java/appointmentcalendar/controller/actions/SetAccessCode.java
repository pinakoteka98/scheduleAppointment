package appointmentcalendar.controller.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import appointmentcalendar.model.Data;

/**
 * SetAccessCode.
 */
public class SetAccessCode extends Action {

	public SetAccessCode() {
	}

	/*
	 * (non-Javadoc)
	 * @see appointmentcalendar.controller.actions.Action#respond(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void respond(HttpServletRequest request, HttpServletResponse response) {
		Data data = new Data(request);
		String code = data.get("accessCode");

		service.setAccessCode(code);
	}

}
