package appointmentcalendar.utils;

/**
 * EmailTemplate.
 */
public class EmailTemplate {

	private static final String MARGIN_TOP = "<br><br>";

	/**
	 * Generate a password recovery email body
	 * 
	 * @param email
	 * @param password
	 * @return generated HTML password recovery email
	 */
	public static String passwordRecovery(String email, String password) {
		StringBuilder sb = new StringBuilder();

		sb.append(MARGIN_TOP);
		sb.append("<h2><b>Password Recovery</b></h2>");
		sb.append("<br>");
		sb.append(
				"<blockquote>" +
						"<b>Email:</b> " + email +
						"<br>" +
						"<b>Password:</b> " + password +
						"</blockquote>");

		return sb.toString();
	}
}
