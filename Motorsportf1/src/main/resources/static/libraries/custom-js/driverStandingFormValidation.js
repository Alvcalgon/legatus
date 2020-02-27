function validateConstructorStandingForm() {
	var errorSeason = validateSeason();
	var errorPosition = validatePosition();
	var errorDriver = validateDriver();
	
	return errorFullname.length == 0 && errorPosition.length == 0 && errorDriver.length == 0;
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

