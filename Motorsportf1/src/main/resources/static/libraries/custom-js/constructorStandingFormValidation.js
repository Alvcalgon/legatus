function validateCSForm() {
	var errorSeason = validateSeasonCS();
	var errorPosition = validatePositionCS();
	var errorConstructor = validateConstructorCS();
	
	return errorSeason.length == 0 && errorPosition.length == 0 && errorConstructor.length == 0;
}


function disableSeasonCS() {
	var inputSeason = document.getElementById("season");
	var inputPosition = document.getElementById("position");
	var inputConstructor = document.getElementById("constructor");
	
	var season = inputSeason.value.trim();
	
	var actionDisable = season.length > 0;
	
	inputPosition.disabled = actionDisable;
	inputConstructor.disabled = actionDisable;
	
	inputConstructor.style.border = "1px solid green";
	inputPosition.style.border = "1px solid green";
}

function disablePositionCS() {
	var inputSeason = document.getElementById("season");
	var inputPosition = document.getElementById("position");
	var inputConstructor = document.getElementById("constructor");
	
	var position = inputPosition.value.trim();
	
	var actionDisable = position.length > 0;
	
	inputSeason.disabled = actionDisable;
	inputConstructor.disabled = actionDisable;

	inputSeason.style.border = "1px solid green";
	inputConstructor.style.border = "1px solid green";
}

function disableConstructorCS() {
	var inputSeason = document.getElementById("season");
	var inputPosition = document.getElementById("position");
	var inputConstructor = document.getElementById("constructor");
	
	var constructor = inputConstructor.value.trim();
	
	var actionDisable = constructor.length > 0;
	
	inputSeason.disabled = actionDisable;
	inputPosition.disabled = actionDisable;
	
	inputSeason.style.border = "1px solid green";
	inputPosition.style.border = "1px solid green";
}

function validateSeasonCS() {
	var input = document.getElementById("season");
	var season = input.value.trim();
	var language = document.documentElement.lang;
	var regExp = /^[0-9]{4}$/;
	var error = "";
	
	var valid = regExp.test(season);
	
	if (season != '' && !valid) {
		error = (language == "es") ? "Temporada inválida. Introduza un valor comprendido entre 1958 y 2019"
				: "Invalid season: Fill a value between 1958 and 2019";
		
		input.style.border = "1px solid red";
	} else {
		input.style.border = "1px solid green";
	}
	
	document.getElementById("seasonErrorCS").innerHTML = error;
	
	return error;
}

function validatePositionCS() {
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
	
	document.getElementById("positionErrorCS").innerHTML = error;
	
	return error;
}

function validateConstructorCS() {
	var input = document.getElementById("constructor");
	var constructor = input.value.trim();
	var language = document.documentElement.lang;
	var regExp = /^[^0-9]{0,}$/;
	var error = "";
	
	var valid = regExp.test(constructor);
	
	if (!valid || constructor.includes("<script>") || constructor.includes("</script>")) {
		error = (language == "es") ? "Escudería inválida" : "Invalid constructor"; 
	
		input.style.border = "1px solid red";
	} else {
		input.style.border = "1px solid green";
	}
	
	document.getElementById("constructorErrorCS").innerHTML = error;
	
	return error;
}


