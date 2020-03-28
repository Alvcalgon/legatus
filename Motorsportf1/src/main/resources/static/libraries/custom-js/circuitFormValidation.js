function validateCircuitForm() {
	var errorName = validateNameCircuit();
	var errorLocation = validateLocation();
	var errorSeason = validateSeason();
	
	return errorName.length == 0 && errorLocation.length == 0 && errorSeason.length == 0;
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
	
	document.getElementById("locationError").innerHTML = error;
	
	return error;
}

function validateNameCircuit() {
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
	
	document.getElementById("nameError").innerHTML = error;
	
	return error;
}

