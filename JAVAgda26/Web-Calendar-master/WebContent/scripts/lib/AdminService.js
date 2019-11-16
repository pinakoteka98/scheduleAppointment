var AdminService = function(){

	const days = [
		"SUNDAY",
		"MONDAY",
		"TUESDAY",
		"WEDNESDAY",
		"THURSDAY",
		"FRIDAY",
		"SATURDAY"
	];
	
	PUBLIC_initDaysOffCheckBoxes = function(){
		Data.get('getDaysOff', function(data){
			$.each(data, function(index, element){
				var id = "#" + element;
				$(id).prop("checked", true);
			});
		}, true);
	};
	
	PUBLIC_saveDaysOff = function(){
		var daysOff = "";
		var workDays = "";
		for (var i = 0; i < days.length; i++){
			var id = "#" + days[i];
			if($(id).is(':checked')){
				daysOff += days[i] + "|";
			}else{
				workDays += days[i] + "|";
			}
		}
		Data.give('setDaysOff', {'daysOff' : daysOff, 'workDays' : workDays});
	};
	
	PUBLIC_setAccessCode = function(){
		Data.give('setAccessCode', {'accessCode':$("#access-code-setting").val()});
	};
	
	PUBLIC_getAccessCode = function(){
		Data.get('getAccessCode', function(data){
			$("#access-code-setting").val(data);
		}, true);
	};
	
	return{
		initDaysOffCheckboxes: PUBLIC_initDaysOffCheckBoxes,
		saveDaysOff: PUBLIC_saveDaysOff,
		getAccessCode: PUBLIC_getAccessCode,
		setAccessCode: PUBLIC_setAccessCode
	};

}();