package appointmentcalendar.model.analytics.dao;

/**
 * BusinessAnalyticsValues.
 */
public class BusinessAnalyticsValues {

	private int totalAppointments;
	private int totalClients;
	private int aptsyesterday;
	private int aptsThisWeek;
	private int aptsLastWeek;
	private int aptsThisMonth;
	private int aptsLastMonth;

	public BusinessAnalyticsValues() {}

	public BusinessAnalyticsValues(
			int totalAppointments,
			int totalClients,
			int aptsyesterday,
			int aptsThisWeek,
			int aptsLastWeek,
			int aptsThisMonth,
			int aptsLastMonth) {

		this.totalAppointments = totalAppointments;
		this.totalClients = totalClients;
		this.aptsyesterday = aptsyesterday;
		this.aptsThisWeek = aptsThisWeek;
		this.aptsLastWeek = aptsLastWeek;
		this.aptsThisMonth = aptsThisMonth;
		this.aptsLastMonth = aptsLastMonth;
	}

	/**
	 * Returns the totalAppointments for this BusinessAnalyticsValues
	 * 
	 * @return totalAppointments The totalAppointments for this BusinessAnalyticsValues
	 */
	public int getTotalAppointments() {
		return totalAppointments;
	}

	/**
	 * Returns the totalClients for this BusinessAnalyticsValues
	 * 
	 * @return totalClients The totalClients for this BusinessAnalyticsValues
	 */
	public int getTotalClients() {
		return totalClients;
	}

	/**
	 * Returns the aptsyesterday for this BusinessAnalyticsValues
	 * 
	 * @return aptsyesterday The aptsyesterday for this BusinessAnalyticsValues
	 */
	public int getAptsyesterday() {
		return aptsyesterday;
	}

	/**
	 * Returns the aptsThisWeek for this BusinessAnalyticsValues
	 * 
	 * @return aptsThisWeek The aptsThisWeek for this BusinessAnalyticsValues
	 */
	public int getAptsThisWeek() {
		return aptsThisWeek;
	}

	/**
	 * Returns the aptsLastWeek for this BusinessAnalyticsValues
	 * 
	 * @return aptsLastWeek The aptsLastWeek for this BusinessAnalyticsValues
	 */
	public int getAptsLastWeek() {
		return aptsLastWeek;
	}

	/**
	 * Returns the aptsThisMonth for this BusinessAnalyticsValues
	 * 
	 * @return aptsThisMonth The aptsThisMonth for this BusinessAnalyticsValues
	 */
	public int getAptsThisMonth() {
		return aptsThisMonth;
	}

	/**
	 * Returns the aptsLastMonth for this BusinessAnalyticsValues
	 * 
	 * @return aptsLastMonth The aptsLastMonth for this BusinessAnalyticsValues
	 */
	public int getAptsLastMonth() {
		return aptsLastMonth;
	}

	/**
	 * Sets the totalAppointments for this BusinessAnalyticsValues
	 * 
	 * @param totalAppointments
	 *            totalAppointments to set
	 */
	public void setTotalAppointments(int totalAppointments) {
		this.totalAppointments = totalAppointments;
	}

	/**
	 * Sets the totalClients for this BusinessAnalyticsValues
	 * 
	 * @param totalClients
	 *            totalClients to set
	 */
	public void setTotalClients(int totalClients) {
		this.totalClients = totalClients;
	}

	/**
	 * Sets the aptsyesterday for this BusinessAnalyticsValues
	 * 
	 * @param aptsyesterday
	 *            aptsyesterday to set
	 */
	public void setAptsyesterday(int aptsyesterday) {
		this.aptsyesterday = aptsyesterday;
	}

	/**
	 * Sets the aptsThisWeek for this BusinessAnalyticsValues
	 * 
	 * @param aptsThisWeek
	 *            aptsThisWeek to set
	 */
	public void setAptsThisWeek(int aptsThisWeek) {
		this.aptsThisWeek = aptsThisWeek;
	}

	/**
	 * Sets the aptsLastWeek for this BusinessAnalyticsValues
	 * 
	 * @param aptsLastWeek
	 *            aptsLastWeek to set
	 */
	public void setAptsLastWeek(int aptsLastWeek) {
		this.aptsLastWeek = aptsLastWeek;
	}

	/**
	 * Sets the aptsThisMonth for this BusinessAnalyticsValues
	 * 
	 * @param aptsThisMonth
	 *            aptsThisMonth to set
	 */
	public void setAptsThisMonth(int aptsThisMonth) {
		this.aptsThisMonth = aptsThisMonth;
	}

	/**
	 * Sets the aptsLastMonth for this BusinessAnalyticsValues
	 * 
	 * @param aptsLastMonth
	 *            aptsLastMonth to set
	 */
	public void setAptsLastMonth(int aptsLastMonth) {
		this.aptsLastMonth = aptsLastMonth;
	}

}
