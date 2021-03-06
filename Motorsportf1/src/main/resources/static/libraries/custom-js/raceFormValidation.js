function validateRaceForm() {
	var errorSeason = validateSeasonRace();
	var errorEvent = validateEventRace();
	
	return errorEvent.length == 0 && errorSeason.length == 0;
}

function validateSeasonRace() {
	var input = document.getElementById("season");
	var season = input.value.trim();
	var language = document.documentElement.lang;
	var regExp = /^[0-9]{4}$/;
	var error = "";
	
	var valid = regExp.test(season);
	
	if (season != '' && !valid) {
		error = (language == "es") ? "Temporada inválida. Introduza un valor comprendido entre 1950 y 2019"
								   : "Invalid season: Fill a value between 1950 and 2019";
		
		input.style.border = "1px solid red";
	} else {
		input.style.border = "1px solid green";
	}
	
	document.getElementById("seasonRaceError").innerHTML = error;
	
	return error;
}

function validateEventRace() {
	var input = document.getElementById("event");
	var event = input.value.trim();
	var language = document.documentElement.lang;
	var regExp = /^[^0-9]{0,}$/;
	var error = "";
	
	var valid = regExp.test(event);
	
	if (!valid || event.includes("<script>") || event.includes("</script>")) {
		error = (language == "es") ? "Introduzca un evento válido" : "You must enter a valid event";
		
		input.style.border = "1px solid red";
	} else {
		input.style.border = "1px solid green";
	}

	document.getElementById("eventRaceError").innerHTML = error;
	
	return error;
}
