package appointmentcalendar.model.database.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import appointmentcalendar.model.User;
import appointmentcalendar.model.database.DBConnectionPool;
import appointmentcalendar.model.database.DBProperties;

/**
 * CalendarDao.
 */
public class CalendarDao extends Dao {

	protected static final String TABLE_NAME = DBProperties.get("db.scheduling.table");
	private static final String BREAK = "break";

	protected CalendarDao() {
		super(TABLE_NAME);
	}

	/**
	 * Add a day to the database
	 * 
	 * @param day
	 *            the day to add
	 * @throws SQLException
	 */
	public void addDay(LocalDate day) throws SQLException {
		String sql = String.format(""
				+ "INSERT INTO %s (%s) "
				+ "VALUES ('%s')",
				TABLE_NAME, Field.DATE.name,
				Date.valueOf(day));

		executeUpdate(sql);
	}

	/**
	 * Book an appointment
	 * 
	 * @param day
	 *            The day of the appointment (Day.getDateAndDay();)
	 * @param time
	 *            The formatted time of the appointment (Timeblock.getFormattedTime();)
	 * @param user
	 *            The user who is booking
	 * @throws SQLException
	 */
	public void bookAppointment(LocalDate day, String time, User user) throws SQLException {
		String sql = String.format(""
				+ "UPDATE %s "
				+ "SET `%s`='%s' "
				+ "WHERE %s='%s';",
				TABLE_NAME,
				time, user.getEmail(),
				Field.DATE.name, Date.valueOf(day));

		executeUpdate(sql);
	}

	public void cancelAppointment(LocalDate day, String time) throws SQLException {
		String sql = String.format(""
				+ "UPDATE %s "
				+ "SET `%s`=NULL "
				+ "WHERE %s='%s';",
				TABLE_NAME,
				time,
				Field.DATE.name, Date.valueOf(day));

		executeUpdate(sql);
	}

	public void scheduleBreaks(LocalDate day, String breakList) throws SQLException {
		String sql = String.format(""
				+ "UPDATE %s "
				+ "SET %s "
				+ "WHERE %s='%s';",
				TABLE_NAME,
				breakList,
				Field.DATE.name, Date.valueOf(day));

		executeUpdate(sql);
	}

	public List<String> getAvailableTimesFromSpecificDay(LocalDate day) throws SQLException {
		List<String> times = new ArrayList<>();

		String sql = String.format(""
				+ "SELECT * "
				+ "FROM %s "
				+ "WHERE %s='%s'",
				TABLE_NAME,
				Field.DATE.name, Date.valueOf(day));

		Connection connection = null;;
		Statement statement = null;
		ResultSet rs = null;

		try {
			connection = DBConnectionPool.getConnection();
			statement = connection.createStatement();

			rs = statement.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();

			rs.first();

			int count = 1;
			while (count <= rsmd.getColumnCount()) {
				String label = rsmd.getColumnLabel(count++);
				String value = rs.getString(label);

				if (value == null)
					times.add(label);
			}
		} catch (Exception e) {
			logError(e, sql, this.getClass().getEnclosingMethod().getName());
			e.printStackTrace();
		} finally {
			close(rs);
			close(statement);
			DBConnectionPool.freeConnection(connection);
		}
		return times;
	}

	public List<LocalDate> getAvailableDays() throws SQLException {
		List<LocalDate> days = new ArrayList<>();

		String sql = String.format(""
				+ "SELECT %s "
				+ "FROM %s",
				Field.DATE.name,
				TABLE_NAME);

		Connection connection = null;;
		Statement statement = null;
		ResultSet rs = null;

		try {
			connection = DBConnectionPool.getConnection();
			statement = connection.createStatement();

			rs = statement.executeQuery(sql);

			while (rs.next())
				days.add(rs.getDate(Field.DATE.name).toLocalDate());

		} catch (Exception e) {
			logError(e, sql, this.getClass().getEnclosingMethod().getName());
			e.printStackTrace();
		} finally {
			close(rs);
			close(statement);
			DBConnectionPool.freeConnection(connection);
		}

		// days.sort(DateSorter.sort());
		return days;
	}

	public List<String> getAppointmentsForUser(String email) throws SQLException {
		List<String> appointments = new ArrayList<>();

		String sql = String.format(""
				+ "SELECT * "
				+ "FROM %s ", TABLE_NAME);

		Connection connection = null;;
		Statement statement = null;
		ResultSet rs = null;

		try {
			connection = DBConnectionPool.getConnection();
			statement = connection.createStatement();

			rs = statement.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();

			while (rs.next()) {
				int count = 1;
				while (count <= rsmd.getColumnCount()) {
					String label = rsmd.getColumnLabel(count++);
					String value = rs.getString(label);

					if (value != null && value.equals(email)) {
						LocalDate day = rs.getDate(1).toLocalDate();
						appointments.add(
								day.format(Service.DATE_FORMATTER)
										+ " @ " + rsmd.getColumnLabel(count - 1));
					}
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
		return appointments;
	}

	public List<String> getNextAppointments(int totalListSize, LocalDate day, String time) throws SQLException {
		List<String> appointments = new ArrayList<>();

		String sql = String.format(""
				+ "SELECT * "
				+ "FROM %s "
				+ "WHERE %s='%s'",
				TABLE_NAME,
				Field.DATE.name, Date.valueOf(day));

		Connection connection = null;;
		Statement statement = null;
		ResultSet rs = null;

		try {
			connection = DBConnectionPool.getConnection();
			statement = connection.createStatement();

			rs = statement.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();

			rs.first();

			int index = 1;
			int columnIndex = 2;
			while (index <= rsmd.getColumnCount()) {
				String column = rsmd.getColumnLabel(index);

				if (column.equals(time)) {
					columnIndex = index;
					break;
				}
				index++;
			}

			int listSize = 0;
			while (columnIndex <= rsmd.getColumnCount()) {
				String email = rs.getString(columnIndex);

				if (email != null && !email.equals(BREAK) && listSize < totalListSize) {
					appointments.add(email + "|" + rsmd.getColumnLabel(columnIndex));
					listSize++;
				}
				columnIndex++;
			}
		} catch (Exception e) {
			logError(e, sql, this.getClass().getEnclosingMethod().getName());
			e.printStackTrace();
		} finally {
			close(rs);
			close(statement);
			DBConnectionPool.freeConnection(connection);
		}
		return appointments;
	}

	public List<String> getAppointmentsForSpecificDay(LocalDate day) throws SQLException {
		List<String> appointments = new ArrayList<>();
		String sql = String.format(""
				+ "SELECT * "
				+ "FROM %s "
				+ "WHERE %s='%s'",
				TABLE_NAME,
				Field.DATE.name, Date.valueOf(day));

		Connection connection = null;;
		Statement statement = null;
		ResultSet rs = null;

		try {
			connection = DBConnectionPool.getConnection();
			statement = connection.createStatement();

			rs = statement.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();

			while (rs.next()) {
				int index = 2;
				while (index <= rsmd.getColumnCount()) {
					String result = rs.getString(index);

					if (result != null && !result.equals(BREAK)) {
						appointments.add(result + "|" + rsmd.getColumnLabel(index));
					}
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
		return appointments;
	}

	/**
	 * @param date
	 * @return list of all users who had an appointment on a specified day
	 */
	public List<String> getUsersBookedOnDay(LocalDate date) {
		List<String> users = new ArrayList<>();

		String sql = String.format(""
				+ "SELECT * "
				+ "FROM %s "
				+ "WHERE %s='%s'",
				TABLE_NAME,
				Field.DATE.name, Date.valueOf(date));

		Connection connection = null;;
		Statement statement = null;
		ResultSet rs = null;

		try {
			connection = DBConnectionPool.getConnection();
			statement = connection.createStatement();

			rs = statement.executeQuery(sql);

			while (rs.next()) {
				int index = 2;
				while (index <= rs.getMetaData().getColumnCount()) {
					users.add(rs.getString(index++));
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

		return users;
	}

	/**
	 * Delete a day from the database
	 * 
	 * @param day
	 * @throws SQLException
	 */
	public void deleteDay(LocalDate day) throws SQLException {
		String sql = String.format(""
				+ "DELETE FROM %s "
				+ "WHERE %s='%s'",
				TABLE_NAME,
				Field.DATE.name, Date.valueOf(day));

		executeUpdate(sql);
	}

	public List<String> getAllTimeSlots(LocalDate day) {
		List<String> timeSlots = new ArrayList<>();
		String sql = String.format(""
				+ "SELECT * "
				+ "FROM %s "
				+ "WHERE %s='%s'",
				TABLE_NAME,
				Field.DATE.name, Date.valueOf(day));

		Connection connection = null;;
		Statement statement = null;
		ResultSet rs = null;

		try {
			connection = DBConnectionPool.getConnection();
			statement = connection.createStatement();

			rs = statement.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();

			while (rs.next()) {
				int index = 2;
				while (index <= rsmd.getColumnCount()) {
					String column = rsmd.getColumnLabel(index++);
					timeSlots.add(column + "|" + rs.getString(column));
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

		return timeSlots;
	}

	public void setTimeSlots(String timeSlots, LocalDate day) throws SQLException {
		String[] arr = timeSlots.split("\\.");
		String modifier = "";

		for (int i = 0; i < arr.length; i++) {
			String[] temp = arr[i].split("\\|");
			String time = temp[0];
			String status = temp[1];

			modifier += "`" + time + "`=" + (status.equals("null") ? "NULL" : "'break'") + ", ";
		}

		modifier = modifier.substring(0, modifier.length() - 2); // remove trailing comma

		String sql = String.format(""
				+ "UPDATE %s "
				+ "SET %s "
				+ "WHERE %s='%s'",
				TABLE_NAME,
				modifier,
				Field.DATE.name, Date.valueOf(day));

		executeUpdate(sql);
	}

	public enum Field {
		DATE("date", "DATE");

		String name;
		String type;

		Field(String name, String type) {
			this.name = name;
			this.type = type;
		}
	}

}
