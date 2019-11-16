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
<link rel="stylesheet" href="styles/admin.css">

<!---- JQuery ---->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>

<!---- Bootstrap ---->
<script src="https://npmcdn.com/tether@1.2.4/dist/js/tether.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
	integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0-alpha.6/js/bootstrap.min.js"></script>
<link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css" rel="stylesheet">
<script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>

<title>Best-ev.club</title>

</head>
<body>

<%
	String isAdmin = null;
	Cookie[] cookies = request.getCookies();
	
	if(cookies != null){
		for(Cookie cookie : cookies){
			if(cookie.getName().equals("admin")){
				isAdmin = cookie.getValue();
			}
		}
	}
	if(isAdmin == null || !isAdmin.equals("true")){
		response.sendRedirect("welcome.jsp");
		return;
	}
%>

	<div class="logo">
		<img id='logo' src="images/Evan.logo.png">
	</div>
	
	<div class="container-fluid ui">

		<div id="reg-label" class="form-header font random-text-color">Admin</div>

		<div class="random-text-color">
			<Label class='heading'>Next appointments:</label>
			<div id="next-appointments" class="font-14"></div>
		</div>

		<div id="view-by-day">
			<div class="form-group">
				<label class="random-text-color heading">View appointments by day</label> 
				<select id="day-sel" class="day-select-list"></select>
			</div>
			
			<div id="all-appointments" class="random-text-color font-13 float-left"></div>
			<div class="random-text-color font-14 float-right">
				<button id="show-hide-button" class="button small-button margin-bottom"></button>
			</div>
		</div>
		
		<div class="divider">
			<hr>
		</div>
		
		<div id="workday-manager" class='random-text-color'>
		
		<!-- Add open time slot options  LOOK INTO USING CHECKBOXES INSTEAD OF BUTTONS -->
		
			<div id='open-close-timeslots'>
				<div><label>Open/Close time slots:</label></div>
				<div class="form-group">
					<select id="day-sel2" class="day-select-list" onchange="TimeSlots.show()"></select>
				</div>
				<div class='container-fluid'>
					<div id='time-slots' class="row btn-group" data-toggle='buttons'>
					</div>
				</div>
				<div class='row'>
					<div class='col-xs-4'>
						<button class=" button small-button margin-bottom margin-top float-left" 
							onclick="TimeSlots.hide(); Calendar.initDay();">Hide</button>
					</div>
					<div class='col-xs-4'>
						<div id="saved-message" class="margin-top info-message random-text-color hide-element">Saved</div>
					</div>
					<div class='col-xs-4'>
						<button class=" button small-button margin-bottom margin-top float-right" onclick="TimeSlots.save()">Save</button>
					</div>
				</div>
			</div>
			
			<div class="divider">
				<hr>
			</div>
			
			<div style="clear:both">
				<label>Days off:</label>
				<form id='schedule-checkbox' class='text-center' style='white-space: nowrap'>
					<label class="checkbox-inline check-box"><input id='SUNDAY'    type="checkbox" value="">S|</label>
					<label class="checkbox-inline check-box"><input id='MONDAY'    type="checkbox" value="">M|</label>
					<label class="checkbox-inline check-box"><input id='TUESDAY'   type="checkbox" value="">T|</label>
					<label class="checkbox-inline check-box"><input id='WEDNESDAY' type="checkbox" value="">W|</label>
					<label class="checkbox-inline check-box"><input id='THURSDAY'  type="checkbox" value="">T|</label>
					<label class="checkbox-inline check-box"><input id='FRIDAY'    type="checkbox" value="">F|</label>
					<label class="checkbox-inline check-box"><input id='SATURDAY'  type="checkbox" value="">S</label>
					<div id="schedule-save-button" class="float-right">
						<input id ="save-button" type='submit' class="button small-button" value='Save'>
					</div>
				</form>
			</div>
			
			<div class="divider">
				<hr>
			</div>
		</div>	
		
		<!-- Access Code Setting -->
		<div>
			<div><label>Access code:</label></div>
			<form onsubmit='AdminService.setAccessCode()'>
				<div><input id="access-code-setting" type="text" class="float-left"></div>
				<div><input id="access-code-save" type="submit" class="button small-button float-right" value="Save"></div>
			</form>
		</div>

	</div>

	<!---- JS ---->
	<script src="scripts/lib/Global.js"></script>
	<script src="scripts/lib/RandomColors.js"></script>
	<script src="scripts/lib/Data.js"></script>
	<script src="scripts/lib/User.js"></script>
	<script src="scripts/lib/Appointments.js"></script>
	<script src="scripts/lib/Calendar.js"></script>
	<script src="scripts/lib/TimeSlots.js"></script>
	<script src="scripts/lib/AdminService.js"></script>
	<script src="scripts/admin.js"></script>

</body>
</html>