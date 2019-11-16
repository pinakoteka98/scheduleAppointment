package appointmentcalendar.model;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Data. Fetches request parameters
 */
public class Data {

	private Map<String, String[]> map;

	public Data(HttpServletRequest request) {
		this.map = request.getParameterMap();
	}

	/**
	 * Return data from a request by its attribute name
	 * 
	 * @param attribute
	 * @return request data string
	 */
	public String get(String attribute) {
		return map.get("data[" + attribute + "]")[0];
	}

}
