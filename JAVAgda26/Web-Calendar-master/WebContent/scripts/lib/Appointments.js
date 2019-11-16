var Appointments = function(){
	
	var appointmentListSize = 0;
	
	PUBLIC_book = function(){
		Data.give('booking', ['day-sel', 'time-select'], function(data){
	    	appointmentListSize++;
	    	_addAppointmentElement(data.day + " @ " + data.time);
	    	_updateAppointmentHeading();
	    	displayConfirmMessage();
	    	Calendar.PUBLIC_refreshDayAndTime();
	    });
	};
	
	PUBLIC_cancel = function(id){
		hideInfoMessage();
	    var nodeID = $(id).closest('div').attr('id');
	    var selector = "#" + nodeID;
	    var appointment = $(selector).text().slice(0,-1);

	    var confirmation = confirm("Delete appointment for " + appointment + "?");

	    if (confirmation) {
	        Data.give('cancelAppointment',{'appointment': appointment}, function(){
	        	$(selector).remove();
	        	if (appointmentListSize > 0){
	        		appointmentListSize--;
	        	}
	        	_updateAppointmentHeading();
	        	displayCancelMessage();
	        });
	    }
	};
	
	PUBLIC_displayUserAppointments = function(appointments){
		appointmentListSize = _getObjectSize(appointments);

	    _updateAppointmentHeading();

	    if (appointmentListSize > 0) {
	        $.each(appointments, function (index, element) {
	        	_addAppointmentElement(element);
	        });
	    }
	};
	
	PUBLIC_showAppointmentsByDay = function(){
		Data.give('loadAppointmentsForDay', ['day-sel'], function(data){
			$.each(data, function(index, element) {
				$('#all-appointments').append(
						"<div class='appointment-list'>" + element + "</div>");
			})
		}, true);
	};
	
	PUBLIC_showAppointmentsToAdmin = function(){
		Data.get('nextAppointments', function(data){
			if(_getObjectSize(data) === 0){
				$('#next-appointments').append("<div class='font-16 text-center'><i>No Appointments</i></div>");
			} else {
				$.each(data, function(index, element) {
					$('#next-appointments').append("<div>" + element + "</div>");
				});
			}
		}, true);
	};
	
	
	_addAppointmentElement = function(element){
		var id = "a" + element.replace(/\s+/g, '').replace(/@/g, '').replace(/:/g, '').replace(/-/g, '');
	    $('#appointment-list').append(
	        "<div id='" + id + "'>" 
	        + element 
	        + "<a class='delete-appointment float-right' onmouseover='' onclick='Appointments.cancel(this)'><b>X</b></a>"
	        + "</div>");
	};
	
	_updateAppointmentHeading = function(){
		switch (appointmentListSize) {
        	case 0:$('#appointment-heading').html("You have no appointments");break;
        	case 1:$('#appointment-heading').html("Your next appointment is:");break;
        	default:$('#appointment-heading').html("Your appointments are:");break;
		}
	};
	
	_getObjectSize = function(object) {
	    var count = 0;
	    var i;

	    for (i in object) {
	        if (object.hasOwnProperty(i)) count++;
	    }
	    return count;
	};
	
	return {
		book: PUBLIC_book,
		cancel: PUBLIC_cancel,
		displayUserAppointments: PUBLIC_displayUserAppointments,
		showAppointmentsByDay: PUBLIC_showAppointmentsByDay,
		showAppointmentsToAdmin: PUBLIC_showAppointmentsToAdmin
	};

}();