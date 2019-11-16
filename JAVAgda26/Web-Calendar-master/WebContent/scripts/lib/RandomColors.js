var RandomColors = function() {

	const colors = [ '#2c3e50', '#342224', '#472E32', '#000000' ];

	PUBLIC_randomize = function() {

		var color = Math.floor(Math.random() * colors.length);

		$('body, select, .button').animate({
			backgroundColor : colors[color]
		}, 1000);

		$('.random-text-color').animate({
			color : colors[color]
		}, 1000);
	};

	return {
		randomize : PUBLIC_randomize
	};

}();
