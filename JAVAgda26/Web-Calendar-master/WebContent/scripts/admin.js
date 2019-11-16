///////////////////////////////////////////////////////////////////
////////////////////////Button Events ////////////////////////////
///////////////////////////////////////////////////////////////////

$(document).ready(function() {
	RandomColors.PUBLIC_randomize();
	initPageData();
	
	$('#day-sel').on('change', function(){
		$('.appointment-list').remove();
		Appointments.showAppointmentsByDay();
		$('.appointment-list').show();
		toggleShowHideButtonText();
	})
	
	$('#show-hide-button').on('click', function(){
		$('.appointment-list').toggle();
		toggleShowHideButtonText();
	})
	
	$('#save-button').on('click', function(){
		AdminService.saveDaysOff();
	})

})

///////////////////////////////////////////////////////////////////
//////////////////////// Initialization ///////////////////////////
///////////////////////////////////////////////////////////////////

function initPageData(){
	Appointments.showAppointmentsToAdmin();
	Calendar.PUBLIC_initDay();
	AdminService.initDaysOffCheckboxes();
	AdminService.getAccessCode();
}


///////////////////////////////////////////////////////////////////
//////////////////////// View Toggling ////////////////////////////
///////////////////////////////////////////////////////////////////

function toggleShowHideButtonText(){
	if($('.appointment-list').is(':hidden'))
		$('#show-hide-button').html("Show");
	else 
		$('#show-hide-button').html("Hide");
	
}



