package appointmentcalendar.model.analytics.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import appointmentcalendar.model.database.DBConnectionPool;
import appointmentcalendar.model.database.DBProperties;
import appointmentcalendar.model.database.dao.Dao;

/**
 * BusinessAnalyticsDao.
 */
public class BusinessAnalyticsDao extends Dao {

	private static final String TABLE_NAME = DBProperties.get("db.businessAnalytics.table");

	BusinessAnalyticsDao() {
		super(TABLE_NAME);
	}

	public BusinessAnalyticsValues getBusinessAnalyticsValues() {
		String sql = select("*", TABLE_NAME);

		Connection connection = null;;
		Statement statement = null;
		ResultSet rs = null;

		BusinessAnalyticsValues bav = new BusinessAnalyticsValues();

		try {
			connection = DBConnectionPool.getConnection();
			statement = connection.createStatement();

			rs = statement.executeQuery(sql);

			while (rs.next()) {
				bav.setTotalAppointments(rs.getInt(Field.TOTAL_APTS.name));
				bav.setTotalClients(rs.getInt(Field.TOTAL_CLIENTS.name));
				bav.setAptsyesterday(rs.getInt(Field.APTS_YESTERDAY.name));
				bav.setAptsThisWeek(rs.getInt(Field.APTS_THIS_WEEK.name));
				bav.setAptsLastWeek(rs.getInt(Field.APTS_LAST_WEEK.name));
				bav.setAptsThisMonth(rs.getInt(Field.APTS_THIS_MONTH.name));
				bav.setAptsLastMonth(rs.getInt(Field.APTS_LAST_MONTH.name));
			}

		} catch (Exception e) {
			logError(e, sql, this.getClass().getEnclosingMethod().getName());
			e.printStackTrace();
		} finally {
			close(rs);
			close(statement);
			DBConnectionPool.freeConnection(connection);
		}

		return bav;
	}

	public void addToTotalAppointments(int amount) {
		String sql = String.format(""
				+ "UPDATE %s "
				+ "SET %s=%d",
				TABLE_NAME,
				Field.TOTAL_APTS, amount);

		try {
			executeUpdate(sql);
		} catch (SQLException e) {
			logError(e, sql, this.getClass().getEnclosingMethod().getName());
			e.printStackTrace();
		}
	}

	protected String select(String columns, String tableName) {
		return String.format(""
				+ "SELECT %s FROM",
				columns, tableName);
	}

	protected String select(String columns, String tableName, String condition, String expression) {
		return String.format(""
				+ "SELECT %s FROM %s WHERE %s='%s'",
				columns, tableName, condition, expression);
	}

	protected String update(String tableName, String column, String value) {
		return String.format(""
				+ "UPDATE %s SET %s='%s'",
				tableName, column, value);
	}

	enum Field {
		TOTAL_APTS("total_apt"),
		TOTAL_CLIENTS("total_clients"),
		APTS_YESTERDAY("apts_yesterday"),
		APTS_THIS_WEEK("apts_this_week"),
		APTS_LAST_WEEK("apts_last_week"),
		APTS_THIS_MONTH("apts_this_month"),
		APTS_LAST_MONTH("apts_last_month");

		String name;

		Field(String name) {
			this.name = name;
		}
	}

}
