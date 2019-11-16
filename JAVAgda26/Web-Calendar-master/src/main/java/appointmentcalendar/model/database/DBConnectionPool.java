package appointmentcalendar.model.database;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Servlet implementation class DBConnectionPool
 */


public class DBConnectionPool extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static Logger LOG = LogManager.getLogger();
	private static DataSource datasource = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DBConnectionPool() {
		super();
	}

	/**
	 * Initialize connection pool
	 */
	@Override
	public void init() throws ServletException {
		try {
			InitialContext initialContext = new InitialContext();

			datasource = (DataSource) initialContext.lookup("java:comp/env/jdbc/mysql");

			if (datasource == null) {
				String message = "Could not find DataSource in DBConnectionPool. ";
				LOG.error(message);
				throw new Exception(message);
			}
		} catch (Exception e) {
			throw new ServletException(e.getMessage());
		}
	}

	/**
	 * Get a connection to the database
	 */
	public static Connection getConnection() throws SQLException {
		return datasource.getConnection();
	}

	/**
	 * Return a connection to the pool
	 */
	public static void freeConnection(Connection connection) {
		try {
			connection.close();
		} catch (Exception e) {
			LOG.error("DBConnectionPool: Threw an exception closing a database connection @ " + connection.toString());
			e.printStackTrace();
		}
	}

}
