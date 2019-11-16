<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!---- Fonts ---->
<link href="https://fonts.googleapis.com/css?family=Permanent+Marker" rel="stylesheet">
<!---- CSS ---->
<link rel="stylesheet" href="styles/styles.css">

<!---- JQuery ---->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>

<!---- Bootstrap ---->
<script src="https://npmcdn.com/tether@1.2.4/dist/js/tether.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
	integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0-alpha.6/js/bootstrap.min.js"></script>

<title>Best-ev.club</title>

</head>
<body>
	<div class="logo">
		<img id='logo' src="images/Evan.logo.png">
	</div>
	
	<div class="container-fluid ui">

		<!-- login form-->
		<div id="login-form">
			<form id="signin-form" onsubmit='User.login("login-email", "login-password"); return false'>
		
				<div id="signin-label" class="form-header font random-text-color">Sign in</div>
			
				<div class="form-group">
					<label class="random-text-color" for="email-input">Email:</label> 
					<input class="form-control background-color" type="email" id="login-email" placeholder="Enter your email" required>
				</div>
			
				<div id="no-email-found" class="error-message hide-element">Email address is not registered</div>
			
				<div class="form-group">
					<label class="random-text-color" for="password-input">Password:</label> 
					<input class="form-control background-color" type="password" id="login-password" placeholder="Enter your password" required>
				</div>
			
				<div id="invalid-password-or-email" class="error-message hide-element">Email or password is incorrect</div>
			
				<div id="no-account-reg-container">
					<div class="float-left font-12">
						<i>Don't have an account yet?</i>
					</div>
					
					<div class="float-right">
						<a id="reg-link" href="#">Register</a>
					</div>
				</div>
				
				<div id='forgot' class="">
					<a id="forgot-password-link" class="font-12"><i>Forgot password</i></a>
				</div>
			
				<div class="button-container">
					<input id="signin-button" class="button float-left" type="submit" value="Login">
					<div id="reg-info-message" class="info-message hide-element random-text-color"></div>
				</div>
				
				
			</form>
		</div>
		<!-- login form -->

		<!-- register form -->
		<div id="register-form" class="hide-element">
			<form onsubmit='User.register("reg-firstName", "reg-lastName", "reg-email", "reg-password", "reg-password-2", "reg-access"); return false'> 
			
				<div id="reg-label" class="form-header font random-text-color">Register</div>
				
				<div class="form-group">
					<label class="random-text-color" for="reg-firstName">First name:</label> 
					<input class="form-control background-color" type="text" id="reg-firstName" placeholder="Enter your first name" required>
				</div>
				
				<div class="form-group">
					<label class="random-text-color" for="reg-lastName">Last name:</label> 
					<input class="form-control background-color" type="text" id="reg-lastName" placeholder="Enter your last name" required>
				</div>
				
				<div class="form-group">
					<label class="random-text-color" for="reg-email">Email:</label> 
					<input class="form-control background-color" type="email" id="reg-email" placeholder="Enter your email" required>
					<div id="email-already-registered" class="error-message hide-element">Email is already registered</div>
				</div>
				
				<div class="form-group">
					<label class="random-text-color" for="reg-password">Password:</label> 
					<input class="form-control background-color" type="password" id="reg-password" placeholder="Enter your password" required>
				</div>
				
				<div class="form-group">
					<label class="random-text-color" for="reg-password-2">Re-enter password:</label>
					<input class="form-control background-color" type="password" id="reg-password-2" placeholder="Re-enter your password" required>
					<div id="passwords-dont-match" class="error-message hide-element">Passwords do not match</div>
				</div>
				
				
				<div class="form-group">
					<label class="random-text-color" for="reg-access">Access code:</label> 
					<input class="form-control background-color" type="text" id="reg-access" placeholder="Enter your access code" required>
					<div id="wrong-access-code" class="error-message hide-element">Access code is incorrect</div>
				</div>
				
				<div class="button-container">
					<div id="reg-button-div" class="float-left">
						<input id="reg-button" class="button" type="submit" value="Register">
					</div>
					
					<div id="signin-div" class="float-right">
						<a id="signin-link" href="#">Sign in</a>
					</div>
				</div>
			</form>
		</div>
		<!-- register form -->

		<!-- booking form -->
		<div id="booking-form" class="hide-element">

			<div class="form-header font random-text-color">Book time</div>
			
			<div id="welcome" class="random-text-color">
				<div class="user-context">Welcome back <span id='name-label'></span></div>
				<div id="appointment-heading" class="user-context heading"></div>
				<div id="appointment-list" class="font-12"></div>
			</div>
			
			<div id="booking-form-inner">
			
				<div class="form-group">
					<label class="random-text-color" for="day-select">Select the day:</label> 
					<select id="day-sel" class="day-select-list" name="day-select" onchange="Calendar.initTime('day-sel')"></select>
				</div>
				
				<div class="form-group">
					<label class="random-text-color" for="time-select">Select the time:</label> 
					<select id="time-select" onchange="enableBookButton()"></select>
				</div>
				
				<div class="button-container">
					<input id="book-button" class="button float-left" type="submit" value="Book" disabled>
					<div id="booking-info-message" class="info-message hide-element random-text-color"></div>
				</div>
			</div>
			<!-- booking-form-inner -->
			
		</div>
		<!-- booking form -->
		
		
		<div id="forgot-password-form" class="hide-element">
			<div class="form-header font random-text-color">Password Recovery</div>
			<form id="recover-email-form" onsubmit="User.recoverPassword('recovery-email'); return false">
				<div class="form-group">
					<label class="random-text-color">Enter the email you registered with:</label>
					<input id="recovery-email" class="form-control background-color" type="email" placeholder="Enter your email" required>
				</div>
				<input class="button" type="submit" value="Submit">
				<div class="float-right">
					<a id="go-back">back</a>
				</div>
			</form>
		</div>
		
	</div>
	<!-- ui container -->

	<!---- JS ---->
	<script src="scripts/lib/Global.js"></script>
	<script src="scripts/lib/RandomColors.js"></script>
	<script src="scripts/welcome.js"></script>
	<script src="scripts/lib/Data.js"></script>
	<script src="scripts/lib/User.js"></script>
	<script src="scripts/lib/Appointments.js"></script>
	<script src="scripts/lib/Calendar.js"></script>

</body>
</html>