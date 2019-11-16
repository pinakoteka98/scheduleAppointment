package appointmentcalendar.controller.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import appointmentcalendar.model.Data;
import appointmentcalendar.model.Responder;
import appointmentcalendar.model.User;
import appointmentcalendar.model.dto.BookingResponseDto;

public class Booking extends Action {

	private BookingResponseDto responseDto;

	public Booking() {
		responseDto = new BookingResponseDto();
	}

	@Override
	public void respond(HttpServletRequest request, HttpServletResponse response) {
		Data data = new Data(request);
		String day = data.get("day-sel");
		String time = data.get("time-select");

		User user = (User) request.getSession().getAttribute("user");

		int responseCode = service.bookAppointment(day, time, user);

		responseDto.setResponseCode(responseCode);
		responseDto.setDay(day);
		responseDto.setTime(time);

		Responder.send(responseDto, response);
	}

}
