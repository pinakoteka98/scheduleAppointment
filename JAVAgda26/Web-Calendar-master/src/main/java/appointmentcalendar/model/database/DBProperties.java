package appointmentcalendar.model.database;

import java.util.Properties;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * DBProperties.
 */


public class DBProperties extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static Properties properties = new Properties();
	private static String PROPERTIES_FILE_NAME;

	@Override
	public void init() {
		PROPERTIES_FILE_NAME = getInitParameter("fileName");
		try {
			properties.load(getServletContext().getResourceAsStream(PROPERTIES_FILE_NAME));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String get(String property) {
		return properties.getProperty(property);
	}

}
