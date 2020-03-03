function validateConstructorStandingForm() {
	var errorSeason = validateSeason();
	var errorPosition = validatePosition();
	var errorDriver = validateDriver();
	var errorLimit = validateLimit();
	
	return errorFullname.length == 0 && errorPosition.length == 0 && errorDriver.length == 0 && errorLimit.length == 0;
}

function validateLimit() {
	var input = document.getElementById("limit");
	//var input = document.querySelectorAll("form.search input#fullname")[0];
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
	var input = document.querySelectorAll("form.search input#season")[0];
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
	
	var avoidXSSAttack = htmlScape(input);
	document.getElementById("seasonErrorCS").innerHTML = error;
	
	return error;
}

function validatePosition() {
	var input = document.querySelectorAll("form.search input#position")[0];
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
	
	var avoidXSSAttack = htmlScape(input);
	document.getElementById("positionErrorCS").innerHTML = error;
	
	return error;
}

function validateDriver() {
	var input = document.querySelectorAll("form.search input#driver")[0];
	var driver = document.forms["driver-standing-finder"]["driver"].value.trim();
	var language = document.documentElement.lang;
	var regExp = /^[^0-9]*$/;
	var error = "";
	
	var valid = regExp.test(driver);
	
	if (driver != '' && !valid) {
		error = (language == "es") ? "Piloto inválida" : "Invalid driver"; 
	
		input.style.border = "1px solid red";
	} else {
		input.style.border = "1px solid green";
	}
	
	var avoidXSSAttack = htmlScape(input);
	document.getElementById("pilotoErrorCS").innerHTML = error;
	
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

