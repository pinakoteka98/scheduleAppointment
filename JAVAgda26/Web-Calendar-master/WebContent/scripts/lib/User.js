var User = new function(){
	
	PUBLIC_login = function(email, password){
		Data.give('login', [email, password], function(data){
			switch (data.responseCode) {
				case 1: _displayInfo(data.firstName);
						Appointments.displayUserAppointments(data.appointments); 
						displayBookingForm(); 
						break;
				case 2: showWrongPasswordOrEmailMessage(); break;
				case 3: showNoEmailRegisteredMessage(); break;
				case 4: window.location.replace(Global.url + "/admin"); break;
			}
		})
	};
	
	PUBLIC_register = function(firstName, lastName, email, pass1, pass2, access){
		hideWarnings();
	    hideInfoMessage();
	    
	    var password1 = $(pass1).val();
	    var password2 = $(pass2).val();
	    
		if(password1 !== password2){
			showPasswordsDontMatchMessage();
		} else {
			Data.give('register', [firstName, lastName, email, pass1, access], function(data){
				switch(data.responseCode){
					case 1: toggleLoginRegisterForms(); 
						    displayRegConfirmMessage(); 
						    break;
					case 2: showEmailAlreadyRegisteredMessage(); break;
					case 3: showDatabaseErrorDialog; break;
					case 4: showWrongAccessCodeMessage(); break;
				}
			})
		}
	};
	
	PUBLIC_recoverPassword = function(email){
		Data.give('recoverPassword', [email], function(data){
	    	switch (data.responseCode) {
	    		case 1: alert("Password recovery has been sent you your email"); break;
	    		case 1: alert("That email is not registered"); break;
	    	}
	    });
	};
	
	_displayInfo = function(firstName){
		$('#name-label').html("<b>" + firstName + "</b>");
	};
	
	return {
		login: PUBLIC_login,
		register: PUBLIC_register,
		recoverPassword: PUBLIC_recoverPassword
	};

}();