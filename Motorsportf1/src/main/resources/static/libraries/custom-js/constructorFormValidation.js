function validateConstructorForm() {
	var errorName = validateNameConstructor();
	var errorCountry = validateCountryConstructor();
	
	return errorName.length == 0 && errorCountry.length == 0;
}


function validateNameConstructor() {
	var input = document.getElementById("name");
	var name = input.value.trim();
	var language = document.documentElement.lang;
	var regExp = /^[^0-9]{0,}$/;
	var error = "";
	
	var valid = regExp.test(name);
	
	if (!valid || name.includes("<script>") || name.includes("</script>")) {
		error = (language == "es") ? "Introduzca un nombre válido." : "You must enter a valid name.";
		
		input.style.border = "1px solid red";
	} else {
		input.style.border = "1px solid green";
	}
	
	document.getElementById("nameConstructorError").innerHTML = error;
	
	return error;
}

function validateCountryConstructor() {
	var input = document.getElementById("country");
	var country = input.value.trim();
	var language = document.documentElement.lang;
	var regExp = /^[^0-9]{0,}$/;
	var error = "";
	
	var valid = regExp.test(country);
	
	if (!valid || country.includes("<script>") || country.includes("</script>")) {
		error = (language == "es") ? "País inválido" : "Invalid country"; 
	
		input.style.border = "1px solid red";
	} else {
		input.style.border = "1px solid green";
	}
	
	document.getElementById("countryConstructorError").innerHTML = error;
	
	return error;
}

