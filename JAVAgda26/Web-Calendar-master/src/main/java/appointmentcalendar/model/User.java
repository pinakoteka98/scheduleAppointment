package appointmentcalendar.model;

/**
 * User.
 */
public class User {

	private String firstName;
	private String lastName;
	private String email;
	private String password;

	private int loginTotal;
	private int bookingTotal;

	public User() {}

	public User(String firstName, String lastName, String email, String password) {
		setFirstName(firstName);
		setLastName(lastName);
		setEmail(email);
		setPassword(password);
	}

	/**
	 * Returns the firstName for this User
	 * 
	 * @return The firstName for this User
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns the lastName for this User
	 * 
	 * @return The lastName for this User
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns the email for this User
	 * 
	 * @return The email for this User
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Returns the password for this User
	 * 
	 * @return password for this user
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Return the total number of logins for this User
	 * 
	 * @return total number of logins for this User
	 */
	public int getLoginTotal() {
		return loginTotal;
	}

	/**
	 * Return the total number of bookings for this User
	 * 
	 * @return total number of bookings for this User
	 */
	public int getBookingTotal() {
		return bookingTotal;
	}

	/**
	 * Sets the firstName for this User
	 * 
	 * @param firstName
	 *            firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = initializeNullString(firstName);
	}

	/**
	 * Sets the lastName for this User
	 * 
	 * @param lastName
	 *            lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = initializeNullString(lastName);;
	}

	/**
	 * Sets the password for this User
	 * 
	 * @param password
	 *            password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Sets the email for this User
	 * 
	 * @param email
	 *            email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Set the total number of logins for this User
	 * 
	 * @param loginTotal
	 */
	public void setLoginTotal(int loginTotal) {
		this.loginTotal = loginTotal;
	}

	/**
	 * Set the total number of bookings for this User
	 * 
	 * @param bookingTotal
	 */
	public void setBookingTotal(int bookingTotal) {
		this.bookingTotal = bookingTotal;
	}

	/*
	 * If the string is null, return an empty string. Else return the parameter string
	 */
	private String initializeNullString(String string) {
		String setString = "";

		if (string != null)
			setString = string;

		return setString;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
	}

}
