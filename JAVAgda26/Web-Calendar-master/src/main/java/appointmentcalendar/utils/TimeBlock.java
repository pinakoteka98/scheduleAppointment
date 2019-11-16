package appointmentcalendar.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * TimeBlock. Represents 30 minutes of a Day.
 */
public class TimeBlock {

	private LocalTime time;

	public TimeBlock(LocalTime time) {
		this.time = time;
	}

	/**
	 * Get this time in 24 hour format
	 * 
	 * @return this time as a LocalTime object
	 */
	public LocalTime getTime() {
		return time;
	}

	/**
	 * Get this time in 'h:mm AM/PM' format
	 * 
	 * @return this time as a formatted String
	 */
	public String getFormattedTime() {
		return time.format(DateTimeFormatter.ofPattern("h:mm a"));
	}

}
