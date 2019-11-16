package appointmentcalendar.model.dto;

import java.io.Serializable;
import java.util.List;

/**
 * LoginResponseDto.
 */
@SuppressWarnings("serial")
public class LoginResponseDto implements Dto, Serializable {

	private int responseCode;
	private String firstName;
	private List<String> appointments;

	public LoginResponseDto() {
	}

	public LoginResponseDto(int responseCode, String firstName, List<String> appointments) {
		setResponseCode(responseCode);
		setFirstName(firstName);
		setAppointments(appointments);
	}

	@Override
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setAppointments(List<String> appointments) {
		this.appointments = appointments;
	}

}
