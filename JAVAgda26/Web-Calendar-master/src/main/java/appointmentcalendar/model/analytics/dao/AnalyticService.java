package appointmentcalendar.model.analytics.dao;

import java.sql.SQLException;
import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import appointmentcalendar.model.User;

/**
 * AnalyticService.
 */
public class AnalyticService {

	private static final Logger LOG = LogManager.getLogger();

	private ClientAnalyticsDao clientsDao;
	private BusinessAnalyticsDao businessDao;

	public AnalyticService() {
		clientsDao = new ClientAnalyticsDao();
	}

	/**
	 * Add a user for analytics tracking
	 * 
	 * @param user
	 */
	public void addUser(User user) {
		try {
			clientsDao.add(user);
		} catch (SQLException e) {
			LOG.error(e);
			e.printStackTrace();
		}
	}

	public void updateLoginAnalytics(User user) {
		clientsDao.incrementLoginTotalAndUpdateLastLogin(user);
	}

	public void incrementBookingsTotal(String email) {
		clientsDao.incrementBookingsTotal(email);
	}

	public void decrementBookingsTotal(String email) {
		clientsDao.decrementBookingsTotal(email);
	}

	public void setLastAppointment(String email, LocalDate date) {
		clientsDao.setLastAppointment(email, date);
	}

	public BusinessAnalyticsValues getBusinessAnaltyicsValues() {
		return businessDao.getBusinessAnalyticsValues();
	}

}
