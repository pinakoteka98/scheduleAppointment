package appointmentcalendar.model.database.task;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import appointmentcalendar.model.database.dao.Service;

/**
 * CalendarUpdateTask.
 */
public class CalendarUpdateTask implements DailyTask {

	private static Logger LOG = LogManager.getLogger();
	private static final int CALENDAR_SIZE = 4;

	private Service service;

	public CalendarUpdateTask() {
		service = new Service();
	}

	/**
	 * Perform the daily update on the database.
	 * The day that has past gets deleted and a new day is added at the end of the schedule
	 */
	@Override
	public boolean performDailyUpdate() {
		List<String> daysOff = null;
		List<LocalDate> currentSchedule = null;

		boolean success = false;

		try {
			daysOff = service.getDaysOffSchedule();
			currentSchedule = service.getAvailableDaysAsLocalDate();

			currentSchedule = deletePreviousDay(currentSchedule);
			LocalDate newDay = generateNextWorkDay(daysOff, currentSchedule);
			addNewDay(newDay, currentSchedule);

			success = true;

		} catch (SQLException e) {
			LOG.error(e);
			e.printStackTrace();
		}
		return success;
	}

	/**
	 * Add a new day to the schedule if the schedule is not full
	 * 
	 * @param newDay
	 * @param currentSchedule
	 */
	private void addNewDay(LocalDate newDay, List<LocalDate> currentSchedule) {
		String formattedDay = newDay.format(Service.DATE_FORMATTER);

		if (currentSchedule.size() <= CALENDAR_SIZE) {
			service.addDay(newDay);
			LOG.info("Adding new day: " + formattedDay);
		} else {
			LOG.info("Attempted to add new day: " + formattedDay + " but the calendar was full");
		}
	}

	/**
	 * Generate a day to add to the end of the schedule. If the next day at the end of the schedule is a scheduled day off
	 * then it is advanced by one day until it equals a scheduled work day.
	 * 
	 * @param daysOff
	 *            the list of scheduled days off
	 * @param currentDays
	 *            days currently on the schedule
	 * @return the new day to be added
	 * @throws SQLException
	 */
	private LocalDate generateNextWorkDay(List<String> daysOff, List<LocalDate> currentSchedule) {

		LocalDate newDay = LocalDate.now();

		for (String day : daysOff) {
			if (newDay.getDayOfWeek().toString().equalsIgnoreCase(day))
				newDay = newDay.plusDays(1);
		}

		for (LocalDate date : currentSchedule) {
			if (newDay.equals(date)) {
				newDay = newDay.plusDays(1);

				for (String day : daysOff) {
					if (newDay.getDayOfWeek().toString().equalsIgnoreCase(day))
						newDay = newDay.plusDays(1);
				}
			}
		}
		return newDay;
	}

	/**
	 * Delete the day that has passed
	 * 
	 * @param currentDays
	 *            the list of currently scheduled days
	 * @throws SQLException
	 */
	private List<LocalDate> deletePreviousDay(List<LocalDate> currentSchedule) throws SQLException {
		LocalDate yesterday = LocalDate.now().minusDays(1);
		Iterator<LocalDate> it = currentSchedule.iterator();

		while (it.hasNext()) {
			if (yesterday.equals(it.next())) {
				service.deleteDay(yesterday);
				it.remove();
			}
		}
		return currentSchedule;
	}

	/**
	 * Generate an partial SQL string listing the hours to set to 'break'
	 * 
	 * @param breaks
	 *            a list of scheduled breaks
	 * @return a string listing the times to set to 'break' in SQL syntax
	 */
	private String generateBreakSQLString(List<String> breaks) {

		StringBuilder sql = new StringBuilder();

		int size = breaks.size();

		if (size > 0) {
			sql.append(String.format("`%s`='break'", breaks.get(0)));

			if (size > 1) {
				for (int i = 1; i < size; i++)
					sql.append(String.format(",`%s`='break'", breaks.get(i)));
			}
		}
		return sql.toString();
	}

}
