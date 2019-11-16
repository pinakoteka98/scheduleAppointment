package appointmentcalendar.model.dto;

/**
 * BookingResponseDto.
 */
public class BookingResponseDto implements Dto {

	private int responseCode;
	private String day;
	private String time;

	public BookingResponseDto() {
	}

	/*
	 * (non-Javadoc)
	 * @see appointmentcalendar.model.dto.Dto#setResponseCode(int)
	 */
	@Override
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	/**
	 * Sets the day for this BookingResponseDto
	 * 
	 * @param day
	 *            day to set
	 */
	public void setDay(String day) {
		this.day = day;
	}

	/**
	 * Sets the time for this BookingResponseDto
	 * 
	 * @param time
	 *            time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

}
