package appointmentcalendar.controller;

import appointmentcalendar.controller.actions.Action;

/**
 * ControllerDispatch.
 */
public class ControllerDispatch {

	private static final String PATH = "appointmentcalendar.controller.actions.";

	/**
	 * Return an action specified by a URL from the client
	 * 
	 * @param actionType
	 * @return the action specified
	 */
	public static Action toAction(String actionType) {
		Action action = null;
		try {
			action = (Action) getActionClass(format(actionType)).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return action;
	}

	private static Class<?> getActionClass(String actionType) {
		Class<?> action = null;
		try {
			action = Class.forName(PATH + actionType);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return action;
	}

	/*
	 * Capitalize first letter
	 */
	private static String format(String string) {
		return string.substring(0, 1).toUpperCase() + string.substring(1);
	}

}
