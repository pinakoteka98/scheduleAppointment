package appointmentcalendar.utils;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * ContextFinalizer.
 */
public class ContextFinalizer implements ServletContextListener {

	private static Logger LOG = LogManager.getLogger();

	@Override
	public void contextInitialized(ServletContextEvent sce) {
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		Driver d = null;
		while (drivers.hasMoreElements()) {
			try {
				d = drivers.nextElement();
				DriverManager.deregisterDriver(d);
				LOG.warn(String.format("Driver %s deregistered", d));
			} catch (SQLException ex) {
				LOG.warn(String.format("Error deregistering driver %s", d), ex);
			}
		}
		try {

			AbandonedConnectionCleanupThread.checkedShutdown();
			LOG.info("AbandonedConnectionCleanupThread.checkedShutdown successfully executed");
		} catch (Exception e) {
			LOG.warn("SEVERE problem cleaning up: " + Arrays.toString(e.getStackTrace()));
			e.printStackTrace();
		}
	}

}
