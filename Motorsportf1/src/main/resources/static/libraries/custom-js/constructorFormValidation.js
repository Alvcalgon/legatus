function validateDriverForm() {
	var errorFullname = validateFullname();
	var errorCountry = validateCountry();
	var errorLimit = validateLimit();
	
	return errorFullname.length == 0 && errorCountry.length == 0 && errorLimit.length == 0;
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

function validateCountry() {
	var input = document.getElementById("country");
	var country = input.value.trim();
	var language = document.documentElement.lang;
	var regExp = /^[^0-9]{0,}$/;
	var error = "";
	
	var valid = regExp.test(country);
	
	if (!valid) {
		error = (language == "es") ? "País inválido" : "Invalid country"; 
	
		input.style.border = "1px solid red";
	} else {
		input.style.border = "1px solid green";
	}
	
	var avoidXSSAttack = htmlScape(input);
	document.getElementById("countryError").innerHTML = error;
	
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

