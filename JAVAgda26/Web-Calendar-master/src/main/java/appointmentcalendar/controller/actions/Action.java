package appointmentcalendar.controller.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import appointmentcalendar.model.database.dao.Service;

/**
 * Action. Base class for all request actions.
 */
public abstract class Action {

	protected Service service;

	public Action() {
		service = new Service();
	}

	/**
	 * Respond to an HttpServletRequest
	 * 
	 * @param request
	 * @param response
	 */
	public abstract void respond(HttpServletRequest request, HttpServletResponse response);

}
