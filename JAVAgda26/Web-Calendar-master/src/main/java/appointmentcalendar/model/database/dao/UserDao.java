package appointmentcalendar.model.database.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import appointmentcalendar.model.User;
import appointmentcalendar.model.database.DBConnectionPool;
import appointmentcalendar.model.database.DBProperties;

/**
 * UserDao.
 */
public final class UserDao extends Dao {

	private static final String TABLE_NAME = DBProperties.get("db.userServices.table");
	private static final String SETTINGS_TABLE_NAME = DBProperties.get("db.settings.table");

	UserDao() {
		super(TABLE_NAME);
	}

	/**
	 * Add an User to the User table
	 * 
	 * @param user
	 *            The user to add
	 * @throws SQLException
	 *             if a database access error occurs or the statement fails to execute
	 */
	public void add(User user) throws SQLException {
		String sql = String.format(""
				+ "INSERT INTO %s (%s, %s, %s, %s, %s) "
				+ "values('%s', '%s', '%s', '%s', 'false')",
				TABLE_NAME,
				Field.FIRST_NAME.name,
				Field.LAST_NAME.name,
				Field.EMAIL.name,
				Field.PASSWORD.name,
				Field.ADMIN.name,
				user.getFirstName(),
				user.getLastName(),
				user.getEmail(),
				user.getPassword());

		executeUpdate(sql);
	}

	/**
	 * Return a User from the database specified by email
	 * 
	 * @param email
	 *            the email of the user to retrieve
	 * @return the specified user
	 * @throws SQLException
	 *             if a database access error occurs or the statement fails to execute
	 * @throws Exception
	 *             if the result set has more than one result
	 */
	public User getUser(String email) {
		String sql = String.format(""
				+ "SELECT * FROM %s "
				+ "WHERE %s='%s'",
				TABLE_NAME,
				Field.EMAIL.name, email);

		User user = new User();

		Connection connection = null;;
		Statement statement = null;
		ResultSet rs = null;

		try {
			connection = DBConnectionPool.getConnection();
			statement = connection.createStatement();

			rs = statement.executeQuery(sql);

			while (rs.next()) {
				user.setFirstName(rs.getString(Field.FIRST_NAME.name));
				user.setLastName(rs.getString(Field.LAST_NAME.name));
				user.setEmail(rs.getString(Field.EMAIL.name));
				user.setPassword(rs.getString(Field.PASSWORD.name));
			}
		} catch (Exception e) {
			logError(e, sql, this.getClass().getEnclosingMethod().getName());
			e.printStackTrace();
		} finally {
			close(rs);
			close(statement);
			DBConnectionPool.freeConnection(connection);
		}
		return user;
	}

	/**
	 * Check user credentials and return a response code
	 * 
	 * @param email
	 * @param password
	 * @return a response code depending on the login results:
	 *         1 - the user logged in successfully
	 *         2 - the user exists but the password is incorrect
	 *         3 - the email is not registered
	 *         4 - user is an admin
	 * @throws SQLException
	 */
	@SuppressWarnings("resource")
	public int checkUserCredentials(String email, String password) throws SQLException {
		int responseCode = 0;

		String sql = String.format(""
				+ "SELECT * FROM %s "
				+ "WHERE %s='%s'",
				TABLE_NAME,
				Field.EMAIL.name, email);

		String queriedPassword = "";
		String isAdmin = "";

		Connection connection = null;;
		Statement statement = null;
		ResultSet rs = null;

		try {
			connection = DBConnectionPool.getConnection();
			statement = connection.createStatement();

			rs = statement.executeQuery(sql);

			if (!rs.isBeforeFirst()) {
				responseCode = 3;
			}

			while (rs.next()) {
				queriedPassword = rs.getString(Field.PASSWORD.name);
				isAdmin = rs.getString(Field.ADMIN.name);
			}
		} catch (Exception e) {
			logError(e, sql, this.getClass().getEnclosingMethod().getName());
			e.printStackTrace();
			close(rs);
			close(statement);
			return 0;
		} finally {
			close(rs);
			close(statement);
			DBConnectionPool.freeConnection(connection);
		}

		if (password.equals(queriedPassword) && isAdmin.equals("true"))
			responseCode = 4;
		else if (password.equals(queriedPassword))
			responseCode = 1;
		else if (!password.equals(queriedPassword))
			responseCode = 2;

		return responseCode;
	}

	public String getAccessCode() {
		String accessCode = "";
		String sql = String.format("SELECT * FROM access");

		Connection connection = null;;
		Statement statement = null;
		ResultSet rs = null;

		try {
			connection = DBConnectionPool.getConnection();
			statement = connection.createStatement();

			rs = statement.executeQuery(sql);

			while (rs.next())
				accessCode = rs.getString("access_code");

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

		return accessCode;
	}

	public void setAccessCode(String accessCode) {
		String sql = String.format(""
				+ "UPDATE %s "
				+ "SET access_code='%s' ",
				SETTINGS_TABLE_NAME,
				accessCode);
		try {
			executeUpdate(sql);
		} catch (SQLException e) {
			logError(e, sql, this.getClass().getEnclosingMethod().getName());
			e.printStackTrace();
		}
	}

	public enum Field {
		FIRST_NAME("firstName", "VARCHAR(40)"),
		LAST_NAME("lastName", "VARCHAR(40)"),
		EMAIL("email", "VARCHAR(80) PRIMARY KEY "),
		PASSWORD("password", "VARCHAR(40)"),
		ADMIN("admin", "VARCHAR(10)");

		String name;
		String type;

		Field(String name, String type) {
			this.name = name;
			this.type = type;
		}
	}

}
