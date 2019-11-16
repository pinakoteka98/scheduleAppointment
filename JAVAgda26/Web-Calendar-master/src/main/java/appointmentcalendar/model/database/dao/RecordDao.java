package appointmentcalendar.model.database.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import appointmentcalendar.model.database.DBProperties;

/**
 * RecordDao.
 */
public final class RecordDao extends Dao {

	private static final String TABLE_NAME = DBProperties.get("db.bookKeeping.table");

	RecordDao() {
		super(TABLE_NAME);
	}

	/**
	 * Permanently store a day from the calendar
	 * 
	 * @param date
	 * @throws SQLException
	 */
	public void add(LocalDate date) throws SQLException {
		String sql = String.format(""
				+ "INSERT INTO %s "
				+ "SELECT * "
				+ "FROM %s "
				+ "WHERE %s='%s'",
				TABLE_NAME,
				CalendarDao.TABLE_NAME,
				CalendarDao.Field.DATE.name, Date.valueOf(date));

		executeUpdate(sql);
	}

}
