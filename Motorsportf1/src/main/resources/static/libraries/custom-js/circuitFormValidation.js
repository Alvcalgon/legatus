function validateCircuitForm() {
	var errorName = validateName();
	var errorLocation = validateLocation();
	var errorSeason = validateSeason();
	var errorLimit = validateLimit();
	
	return errorName.length == 0 && errorLocation.length == 0 && errorLimit.length == 0 && errorSeason.length == 0;
}

function searchBySeason() {
	var errorSeason = validateSeason();
	
	return errorSeason.length == 0;
}

function validateLimit() {
	var input = document.getElementById("limit");
	var limit = input.value;
	var language = document.documentElement.lang;
	var error = "";
	
	var valid = !isNaN(limit) && limit >= 1;
	
	if (!valid) {
		error = (language == "es") ? "El tamaño de página debe ser entre 1 y el total de registros"
				: "The limit must be a value between 1 and register total numbers";
	}
	
	document.getElementById("limitError").innerHTML = error;
	
	return error;
}

function validateSeason() {
	var input = document.getElementById("seasonSearch");
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
	document.getElementById("seasonErrorCS").innerHTML = error;
	
	return error;
}

function validateLocation() {
	var input = document.getElementById("location");
	var location = input.value.trim();
	var language = document.documentElement.lang;
	var regExp = /^[^0-9]{0,}$/;
	var error = "";
	
	var valid = regExp.test(location);
	
	if (!valid) {
		error = (language == "es") ? "Introduzca una localización válido." : "You must enter a valid location.";
		
		input.style.border = "1px solid red";
	} else {
		input.style.border = "1px solid green";
	}
	
	var avoidXSSAttack = htmlScape(input);
	document.getElementById("locationError").innerHTML = error;
	
	return error;
}

function validateName() {
	var input = document.getElementById("name");
	var name = input.value.trim();
	var language = document.documentElement.lang;
	var regExp = /^[^0-9]{0,}$/;
	var error = "";
	
	var valid = regExp.test(name);
	
	if (!valid) {
		error = (language == "es") ? "Introduzca un nombre válido." : "You must enter a valid name.";
		
		input.style.border = "1px solid red";
	} else {
		input.style.border = "1px solid green";
	}
	
	var avoidXSSAttack = htmlScape(input);
	document.getElementById("nameError").innerHTML = error;
	
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

