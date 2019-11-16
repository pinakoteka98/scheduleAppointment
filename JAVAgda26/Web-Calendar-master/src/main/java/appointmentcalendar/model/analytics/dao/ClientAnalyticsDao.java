package appointmentcalendar.model.analytics.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import appointmentcalendar.model.User;
import appointmentcalendar.model.database.DBConnectionPool;
import appointmentcalendar.model.database.DBProperties;
import appointmentcalendar.model.database.dao.Dao;

/**
 * ClientAnalyticsDao.
 */
public class ClientAnalyticsDao extends Dao {

	private static final String TABLE_NAME = DBProperties.get("db.clientAnalytics.table");

	ClientAnalyticsDao() {
		super(TABLE_NAME);
	}

	public void add(User user) throws SQLException {
		String sql = String.format(""
				+ "INSERT INTO %s "
				+ "(%s, %s) "
				+ "VALUES('%s', '%s')",
				TABLE_NAME,
				Field.EMAIL.name, Field.JOIN_DATE.name,
				user.getEmail(), Date.valueOf(LocalDate.now()));

		executeUpdate(sql);
	}

	/**
	 * Update login specific data for user
	 * 
	 * @param user
	 * @throws SQLException
	 */
	public void incrementLoginTotalAndUpdateLastLogin(User user) {
		String sql = String.format(""
				+ "UPDATE %s "
				+ "SET %s='%s', %s='%s' "
				+ "WHERE %s='%s'",
				TABLE_NAME,
				Field.LOGIN_TOTAL.name, user.getLoginTotal() + 1,
				Field.LAST_LOGIN.name, Timestamp.valueOf(LocalDateTime.now()),
				Field.EMAIL.name, user.getEmail());
		try {
			executeUpdate(sql);
		} catch (SQLException e) {
			logError(e, sql, this.getClass().getEnclosingMethod().getName());
			System.out.println(sql);
			e.printStackTrace();
		}
	}

	public void incrementBookingsTotal(String email) {
		updateBookingTotal(email, "+");
	}

	public void decrementBookingsTotal(String email) {
		updateBookingTotal(email, "-");
	}

	public int getBookingsTotal(String email) {
		int total = 0;

		String sql = String.format(""
				+ "SELECT %s "
				+ "FROM %s "
				+ "WHERE %s='%s'",
				Field.BOOKING_TOTAL.name,
				TABLE_NAME,
				Field.EMAIL.name, email);

		Connection connection = null;;
		Statement statement = null;
		ResultSet rs = null;

		try {
			connection = DBConnectionPool.getConnection();
			statement = connection.createStatement();

			rs = statement.executeQuery(sql);

			while (rs.next())
				total = rs.getInt(Field.BOOKING_TOTAL.name);

		} catch (Exception e) {
			logError(e, sql, this.getClass().getEnclosingMethod().getName());
			e.printStackTrace();
			close(rs);
			close(statement);
		} finally {
			close(rs);
			close(statement);
			DBConnectionPool.freeConnection(connection);
		}
		return total;
	}

	private void updateBookingTotal(String email, String modifier) {
		int total = getBookingsTotal(email);

		String sql = String.format(""
				+ "UPDATE %s "
				+ "SET %s='%s' "
				+ "WHERE %s='%s' ",
				TABLE_NAME,
				Field.BOOKING_TOTAL.name, modifier.equals("+") ? total + 1 : total - 1,
				Field.EMAIL.name, email);

		try {
			executeUpdate(sql);
		} catch (SQLException e) {
			logError(e, sql, this.getClass().getEnclosingMethod().getName());
			e.printStackTrace();
		}
	}

	public void setLastAppointment(String email, LocalDate date) {
		String sql = String.format(""
				+ "UPDATE %s "
				+ "SET %s='%s' "
				+ "WHERE %s='%s'",
				TABLE_NAME,
				Field.LAST_APT.name, Date.valueOf(date),
				Field.EMAIL.name, email);

		try {
			executeUpdate(sql);
		} catch (SQLException e) {
			logError(e, sql, this.getClass().getEnclosingMethod().getName());
			e.printStackTrace();
		}
	}

	public enum Field {
		EMAIL("email"),
		RANK("rank"),
		BOOKING_TOTAL("total_apt"),
		LAST_APT("last_apt"),
		VISITS_THIS_MONTH("visits_this_month"),
		VISITS_LAST_MONTH("visits_last_month"),
		JOIN_DATE("member_since"),
		LAST_LOGIN("last_login"),
		LOGIN_TOTAL("num_of_logins");

		String name;

		Field(String name) {
			this.name = name;
		}
	}

}
