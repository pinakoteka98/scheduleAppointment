package appointmentcalendar.model.database.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import appointmentcalendar.model.database.DBConnectionPool;

public abstract class Dao {

	protected final String tableName;

	private static final Logger LOG = LogManager.getLogger();

	protected Dao(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * Create a table
	 * 
	 * @param sql
	 *            SQL query
	 * @throws SQLException
	 *             if a database access error occurs or the statement fails to execute
	 */
	protected void create(String sql) throws SQLException {
		Statement statement = null;
		Connection connection = null;

		try {
			connection = DBConnectionPool.getConnection();
			statement = connection.createStatement();
			LOG.debug(sql);
			statement.executeUpdate(sql);
			LOG.info("Table '" + tableName + "' created");
		} finally {
			close(statement);
			DBConnectionPool.freeConnection(connection);
		}
	}

	/**
	 * INSERT, UPDATE or DELETE from database
	 * 
	 * @param sql
	 *            SQL query
	 * @throws SQLException
	 *             if a database access error occurs or the statement fails to execute
	 */
	protected void executeUpdate(String sql) throws SQLException {
		Statement statement = null;
		Connection connection = null;
		try {
			connection = DBConnectionPool.getConnection();
			statement = connection.createStatement();
			statement.executeUpdate(sql);
		} finally {
			close(statement);
			DBConnectionPool.freeConnection(connection);
		}
	}

	/**
	 * Close a statement
	 * 
	 * @param statement
	 *            the statement to close
	 */
	protected void close(Statement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
			LOG.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * Close a ResultSet
	 * 
	 * @param rs
	 *            ResultSet to close
	 */
	protected void close(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			LOG.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * Log an error and its corresponding sql call
	 * 
	 * @param e
	 * @param sql
	 */
	protected void logError(Throwable e, String sql, String method) {
		LOG.error("Query failed");
		LOG.error("From: " + method);
		LOG.error(sql);
		LOG.error(e);
	}
}
