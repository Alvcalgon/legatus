function validateConstructorForm() {
	var errorName = validateNameConstructor();
	var errorNationality = validateNationalityConstructor();
	
	return errorName.length == 0 && errorNationality.length == 0;
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

function validateNationalityConstructor() {
	var input = document.getElementById("nationality");
	var nationality = input.value.trim();
	var language = document.documentElement.lang;
	var regExp = /^[^0-9]{0,}$/;
	var error = "";
	
	var valid = regExp.test(nationality);
	
	if (!valid || nationality.includes("<script>") || nationality.includes("</script>")) {
		error = (language == "es") ? "Nacionalidad inválido" : "Invalid nationality"; 
	
		input.style.border = "1px solid red";
	} else {
		input.style.border = "1px solid green";
	}
	
	document.getElementById("nationalityConstructorError").innerHTML = error;
	
	return error;
}

