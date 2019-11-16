package appointmentcalendar.model.database.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import appointmentcalendar.model.database.DBConnectionPool;
import appointmentcalendar.model.database.DBProperties;

/**
 * WorkScheduleDao.
 */
public final class WorkScheduleDao extends Dao {

	private static final String BREAKS_TABLE_NAME = DBProperties.get("db.dailySchedule.table");
	private static final String DAYS_TABLE_NAME = DBProperties.get("db.weeklySchedule.table");

	private static final String ID_CONDITION = "WHERE ID='1'";
	private static final String BREAK = "break";
	private static final String OPEN = "open";
	private static final String WORK_DAY = "true";
	private static final String DAY_OFF = "false";

	WorkScheduleDao() {
		super(BREAKS_TABLE_NAME);
	}

	/**
	 * Schedule a a time slot to be a break
	 * 
	 * @param time
	 * @throws SQLException
	 */
	public void scheduleBreak(String time) throws SQLException {
		changeDailyBreakSchedule(time, BREAK);
	}

	/**
	 * Schedule a a time slot to be open (not a break)
	 * 
	 * @param time
	 * @throws SQLException
	 */
	public void scheduleNonBreak(String time) throws SQLException {
		changeDailyBreakSchedule(time, OPEN);
	}

	/**
	 * Set which times of the day are auto generated to be breaks
	 * 
	 * @return the list of daily breaks
	 * @throws SQLException
	 */
	public List<String> getDailyBreaks() throws SQLException {
		return getDailyBreakSchedule(BREAK);
	}

	/**
	 * Set which times of the day are auto generated to be working hours
	 * 
	 * @return the list of daily working hours
	 * @throws SQLException
	 */
	public List<String> getWorkingHours() throws SQLException {
		return getDailyBreakSchedule(OPEN);
	}

	/**
	 * Set which days of the week are auto generated to be days off
	 * 
	 * @param day
	 * @throws SQLException
	 */
	public void scheduleDayOff(String day) throws SQLException {
		String parseDays = parseAppResonseStringForSql(day, DAY_OFF);
		changeWorkDaySchedule(parseDays);
	}

	/**
	 * Set which days of the week are auto generated to be work days
	 * 
	 * @param day
	 * @throws SQLException
	 */
	public void scheduleWorkDay(String day) throws SQLException {
		String parseDays = parseAppResonseStringForSql(day, WORK_DAY);
		changeWorkDaySchedule(parseDays);
	}

	public List<String> getDaysOffSchedule() throws SQLException {
		List<String> schedule = new ArrayList<>();

		String sql = String.format("SELECT * FROM %s %s", DAYS_TABLE_NAME, ID_CONDITION);

		Connection connection = null;;
		Statement statement = null;
		ResultSet rs = null;

		try {
			connection = DBConnectionPool.getConnection();
			statement = connection.createStatement();

			rs = statement.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();

			int index = 2;
			while (rs.next()) {
				while (index <= rsmd.getColumnCount()) {
					String result = rs.getString(index);

					if (result != null && result.equals(DAY_OFF))
						schedule.add(rsmd.getColumnLabel(index));

					index++;
				}
			}
		} catch (Exception e) {
			logError(e, sql, this.getClass().getEnclosingMethod().getName());
			e.printStackTrace();
		} finally {
			close(rs);
			close(statement);
			DBConnectionPool.freeConnection(connection);
		}

		return schedule;
	}

	/**
	 * Update the daily break schedule template.
	 * 
	 * @param time
	 * @param modifier
	 *            the status of the specified time. Either 'open' or 'break'
	 * @throws SQLException
	 */
	private void changeDailyBreakSchedule(String time, String modifier) throws SQLException {
		String sql = String.format("UPDATE %s SET `%s`='%s' %s",
				BREAKS_TABLE_NAME, time, modifier, ID_CONDITION);

		executeUpdate(sql);
	}

	/**
	 * Update the work day schedule template.
	 * 
	 * @param day
	 * @param modifier
	 *            the status for the specified day. Either 'true' for a work day or 'false' for a day off
	 * @throws SQLException
	 */
	private void changeWorkDaySchedule(String days) throws SQLException {
		String sql = String.format("UPDATE %s SET %s %s;",
				DAYS_TABLE_NAME, days, ID_CONDITION);

		executeUpdate(sql);
	}

	/**
	 * Get the daily breaks schedule
	 * 
	 * @param modifier
	 *            which status to return. Either 'open' or "break'
	 * @return a list of either all open times or all break times depending on the modifier
	 * @throws SQLException
	 */
	private List<String> getDailyBreakSchedule(String modifier) throws SQLException {
		List<String> schedule = new ArrayList<>();

		String sql = String.format("SELECT * FROM %s %s", BREAKS_TABLE_NAME, ID_CONDITION);

		Connection connection = null;;
		Statement statement = null;
		ResultSet rs = null;

		try {
			connection = DBConnectionPool.getConnection();
			statement = connection.createStatement();

			rs = statement.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();

			String result = "";
			int index = 2;
			while (rs.next()) {
				while (index <= rsmd.getColumnCount()) {
					if (rs.getString(index) != null)
						result = rs.getString(index);

					if (result != null && result.equals(modifier))
						schedule.add(rsmd.getColumnLabel(index));

					index++;
				}
			}
		} catch (Exception e) {
			logError(e, sql, this.getClass().getEnclosingMethod().getName());
			e.printStackTrace();
		} finally {
			close(rs);
			close(statement);
			DBConnectionPool.freeConnection(connection);
		}

		return schedule;
	}

	private String parseAppResonseStringForSql(String days, String modifier) {
		String[] temp = days.split("\\|");
		StringBuilder sb = new StringBuilder();

		if (temp.length > 0) {
			sb.append(temp[0] + "='" + modifier + "'");

			if (temp.length > 1) {
				for (int i = 1; i < temp.length; i++) {
					sb.append("," + temp[i] + "='" + modifier + "'");
				}
			}
		}
		return sb.toString();
	}

}
