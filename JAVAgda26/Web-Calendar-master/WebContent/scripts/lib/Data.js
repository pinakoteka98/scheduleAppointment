/*
 * Perform an Ajax call. Send and/or receive Json from the server.
 * 
 * 	action - the specified action. Must be same name as the corresponding server side action
 * 	data - string array containing element id names or a json object.
 * 	callback - the function to run after a successful ajax call 
 * 
 *  Give accepts either a JSON object or a string array.
 */
var Data = function() {

	const URL = Global.url + "/frontController";

	/*
	 * Send data to the server and optionally accept a data response.
	 * Callback is optional. 
	 * If the response is expected as a list, set asList as true on function call
	 */
	PUBLIC_give = function(action, data, callback, asList) {
		$.ajax({
			    type: 'POST',
			     url: URL,
		    	data: _package(action, data),
			datatype: 'json',
			 success: function(response){
				if(callback !== undefined){
					if(response !== "")
						if(asList === true)
							callback(response);
						else 
							callback(JSON.parse(response));
					else 
						callback();
				}
			}
		})
	};

	/*
	 * Get data from the server.
	 * If the response is expected as a list, set asList as true on function call.
	 */
	PUBLIC_get = function(action, callback, asList) {
		$.getJSON(URL, {
			'action' : action
		}, function(data) {
			if(asList === true)
				callback(data);
			else
				callback(JSON.parse(data));
		});
	};

	_package = function(action, data){
        if(data.constructor === Array)
            return _toJson(action, data);
        else
            return {'action' : action, 'data' : data};
	};

	_toJson = function(action, params){
		var json = '{"action":"' + action + '", "data": {"' + params[0] + '":"' + _value(params[0]) + '"';
		
		if(params.length > 1)
			for(var i = 1; i < params.length; i++)
				json += ', "' + params[i] + '": "' + _value(params[i]) + '"';
		return JSON.parse(json + '}}');
	};

	_value = function(id){
		var selector = "#" + id;
		switch($(selector)[0].nodeName){
			case 'INPUT': return $(selector).val();
			case 'SELECT': return $(selector).find(':selected').text();
		}
	};

	return {
		give: PUBLIC_give,
		get: PUBLIC_get
	};

}();