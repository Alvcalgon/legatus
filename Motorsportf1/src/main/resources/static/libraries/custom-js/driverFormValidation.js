function validateDriverForm() {
	var errorFullname = validateFullname();
	var errorCountry = validateCountry();
	var errorLimit = validateLimit();
	
	return errorFullname.length == 0 && errorCountry.length == 0 && errorLimit.length == 0;
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

function validateFullname() {
	var input = document.getElementById("fullname");
	//var input = document.querySelectorAll("form.search input#fullname")[0];
	var fullname = input.value.trim();
	var language = document.documentElement.lang;
	var regExp = /^[^0-9]{0,}$/;
	var error = "";
	
	var valid = regExp.test(fullname);
	
	if (!valid) {
		error = (language == "es") ? "Introduzca un nombre completo. En el caso de que lo haya hecho, fíjese que no haya tecleado un número"
				: "You must enter a full name. In case you type something, pay attention to a number hasn't been written";
		
		input.style.border = "1px solid red";
	} else {
		input.style.border = "1px solid green";
	}
	
	var avoidXSSAttack = htmlScape(input);
	document.getElementById("fullnameError").innerHTML = error;
	
	return error;
}

function validateCountry() {
	var input = document.getElementById("country");
	//var input = document.querySelectorAll("form.search input#country")[0];
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

