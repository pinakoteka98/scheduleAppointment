var TimeSlots = function(){
	
	/*
	 * Show all time slots
	 */
	PUBLIC_show = function(){
		$( ".timeslot" ).remove();
		var day = $('#day-sel2 option:selected').text();
		
		Data.give('getTimeSlots', {'day':day}, function(data){
			$.each(data, function(index, element) {
				_addButton(element);
			});
		}, true);
	};
	
	/*
	 * Save all time slot status'
	 */
	PUBLIC_save = function(){
		var timeslots = "";
		
		$('.time-button').each(function(){
			if(!$(this).hasClass("disabled")){
				var id = $(this).attr("id").replace("-", " ");
				timeslots += id + "|" + ($(this).hasClass('active') ? 'null':'break') + ".";
				
			}
		});
		
		Data.give('setTimeSlots', {'timeSlots' : timeslots, 'day' : $('#day-sel2 option:selected').text()}, function(){
			$('#saved-message').show();
			$('#saved-message').fadeOut(4000);
		});
		
	};
	
	PUBLIC_hide = function(){
		$( ".timeslot" ).remove();
		$('#day-sel2').find('option').remove().end();
	};
	
	/*
	 * Add a time slot button to the button panel
	 */
	_addButton = function(time){
		var split = time.split("|");
		var time = split[0];
		var status = split[1];
		
		var classes;
		var disable = false;
		
		if(status === 'null'){
			classes = "btn-danger active";
		} else if(status === 'break'){
			classes = "btn-danger";
		} else {
			classes = "btn-warning disabled";
			disable = true;
		}
		
		var id = time.replace(" ","-");
		
		$('#time-slots').append(
		  "<div class='col-xs-3 col-centered timeslot'>"
				+ "<button id='" + id + "'class='btn time-button " + classes + "'" + (disable ? "disabled='disabled'":'') + ">" + time + "</button></div>");
	};
	
	return{
		show: PUBLIC_show,
		hide: PUBLIC_hide,
		save: PUBLIC_save
	};
	
}();
