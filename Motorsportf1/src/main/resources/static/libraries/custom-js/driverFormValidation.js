function validateDriverForm() {
	var errorFullname = validateFullname();
	var errorCountry = validateCountry();
	
	return errorFullname.length == 0 && errorCountry.length == 0;
}

function validateFullname() {
	var input = document.getElementById("fullname");
	var fullname = input.value.trim();
	var language = document.documentElement.lang;
	var regExp = /^[^0-9]+$/;
	var error = "";
	
	var valid = regExp.test(fullname);
	
	if (!valid) {
		error = (language == "es") ? "Introduzca un nombre completo. En el caso de que lo haya hecho, fíjese que no haya tecleado un número"
				: "You must enter a full name. In case you type something, pay attention to a number hasn't been written";
	
		input.style.border = "1px solid red";
	} else {
		input.style.border = "1px solid green";
	}
	
	
	document.getElementById("fullnameError").innerHTML = error;
	return error;
}

function validateCountry() {
	var input = document.getElementById("country");
	var country = input.value.trim();
	var language = document.documentElement.lang;
	var regExp = /^[^0-9]+$/;
	var error = "";
	
	var valid = regExp.test(country);
	
	if (!valid) {
		error = (language == "es") ? "País inválido" : "Invalid country"; 
	
		input.style.border = "1px solid red";
	} else {
		input.style.border = "1px solid green";
	}
	
	
	document.getElementById("countryError").innerHTML = error;
	return error;
}

