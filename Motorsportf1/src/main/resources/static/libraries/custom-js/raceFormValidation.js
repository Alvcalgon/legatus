function validateCircuitForm() {
	var errorSeason = validateSeason();
	var errorEvent = validateEvent();
	
	return errorEvent.length == 0 && errorSeason.length == 0;
}

function validateSeason() {
	var input = document.getElementById("season");
	var season = input.value.trim();
	var language = document.documentElement.lang;
	var regExp = /^[0-9]{4}$/;
	var error = "";
	
	var valid = regExp.test(season);
	
	if (season != '' && !valid) {
		error = (language == "es") ? "Temporada inválida. Introduza un valor comprendido entre 1958 y 2018"
				: "Invalid season: Fill a value between 1958 and 2018";
		
		input.style.border = "1px solid red";
	} else {
		input.style.border = "1px solid green";
	}
	
	var avoidXSSAttack = htmlScape(input);
	document.getElementById("seasonRaceError").innerHTML = error;
	
	return error;
}

function validateEvent() {
	var input = document.getElementById("event");
	var event = input.value.trim();
	var language = document.documentElement.lang;
	var regExp = /^[^0-9]{0,}$/;
	var error = "";
	
	var valid = regExp.test(event);
	
	if (!valid) {
		error = (language == "es") ? "Introduzca un evento válido" : "You must enter a valid event";
		
		input.style.border = "1px solid red";
	} else {
		input.style.border = "1px solid green";
	}
	
	var avoidXSSAttack = htmlScape(input);
	document.getElementById("eventError").innerHTML = error;
	
	return error;
}


function htmlScape(input) {
	var tagsToReplace = {'&': '&amp;', '<': '&lt;', '>': 'gt;'};
	var inputValue = input.value;
	
	var validValue = inputValue.replace(/[&<>]/g, function(tag) {
		return tagsToReplace[tag] || tag;
	});
	
	input.value = validValue;
	
	return true;
}

