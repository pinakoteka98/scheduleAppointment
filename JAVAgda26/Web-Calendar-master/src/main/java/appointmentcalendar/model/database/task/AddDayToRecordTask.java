package appointmentcalendar.model.database.task;

import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import appointmentcalendar.model.database.dao.Service;

/**
 * AddDayToRecordTask.
 */
public class AddDayToRecordTask implements DailyTask {

	private static Logger LOG = LogManager.getLogger();

	private Service service;

	public AddDayToRecordTask() {
		service = new Service();
	}

	@Override
	public boolean performDailyUpdate() {
		LocalDate yesterday = LocalDate.now().minusDays(1);
		return service.storeDayFromCalendar(yesterday);
	}

}
