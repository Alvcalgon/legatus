function validateDriverStandingForm() {
	var errorSeason = validateSeasonDS();
	var errorPosition = validatePositionDS();
	var errorDriver = validateDriverDS();
	
	return errorSeason.length == 0 && errorPosition.length == 0 && errorDriver.length == 0;
}


function disableSeasonDS() {
	var inputSeason = document.getElementById("season");
	var inputPosition = document.getElementById("position");
	var inputDriver = document.getElementById("driver");
	
	var season = inputSeason.value.trim();
	
	var actionDisable = season.length > 0;
	
	inputPosition.disabled = actionDisable;
	inputDriver.disabled = actionDisable;

	inputDriver.style.border = "1px solid green";
	inputPosition.style.border = "1px solid green";
}

function disablePositionDS() {
	var inputSeason = document.getElementById("season");
	var inputPosition = document.getElementById("position");
	var inputDriver = document.getElementById("driver");
	
	var position = inputPosition.value.trim();
	
	var actionDisable = position.length > 0;
	
	inputSeason.disabled = actionDisable;
	inputDriver.disabled = actionDisable;
	
	inputSeason.style.border = "1px solid green";
	inputDriver.style.border = "1px solid green";
}

function disableDriverDS() {
	var inputSeason = document.getElementById("season");
	var inputPosition = document.getElementById("position");
	var inputDriver = document.getElementById("driver");
	
	var driver = inputDriver.value.trim();
	
	var actionDisable = driver.length > 0;
	
	inputSeason.disabled = actionDisable;
	inputPosition.disabled = actionDisable;
	
	inputSeason.style.border = "1px solid green";
	inputPosition.style.border = "1px solid green";
}


function validateSeasonDS() {
	var input = document.getElementById("season");
	var season = input.value.trim();
	var language = document.documentElement.lang;
	var regExp = /^[0-9]{4}$/;
	var error = "";
	
	var valid = regExp.test(season);
	
	if (season != '' && !valid) {
		error = (language == "es") ? "Temporada inválida. Introduza un valor comprendido entre 1955 y 2018"
				: "Invalid season: Fill a value between 1955 and 2018";
	
		input.style.border = "1px solid red";
	} else {
		input.style.border = "1px solid green";
	}
	
	document.getElementById("seasonErrorDS").innerHTML = error;
	
	return error;
}

function validatePositionDS() {
	var input = document.getElementById("position");
	var position = input.value.trim();
	var language = document.documentElement.lang;
	var regExp = /^[0-9]{1,2}$/;
	var error = "";
	
	var valid = regExp.test(position);
	
	if (position != '' && !valid) {
		error = (language == "es") ? "Posición inválido" : "Invalid position"; 
	
		input.style.border = "1px solid red";
	} else {
		input.style.border = "1px solid green";
	}
	
	document.getElementById("positionErrorDS").innerHTML = error;
	
	return error;
}

function validateDriverDS() {
	var input = document.getElementById("driver");
	var driver = input.value.trim();
	var language = document.documentElement.lang;
	var regExp = /^[^0-9]{0,}$/;
	var error = "";
	
	var valid = regExp.test(driver);
	
	if (!valid || driver.includes("<script>") || driver.includes("</script>")) {
		error = (language == "es") ? "Piloto inválida" : "Invalid driver"; 
	
		input.style.border = "1px solid red";
	} else {
		input.style.border = "1px solid green";
	}
	
	document.getElementById("driverErrorDS").innerHTML = error;
	
	return error;
}


