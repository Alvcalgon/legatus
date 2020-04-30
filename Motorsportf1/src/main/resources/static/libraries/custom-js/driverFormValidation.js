function validateDriverForm() {
	var errorFullname = validateFullnameDriver();
	var errorNationality = validateNationalityDriver();
	
	return errorFullname.length == 0 && errorNationality.length == 0;
}


function validateFullnameDriver() {
	var input = document.getElementById("fullname");
	var fullname = input.value.trim();
	var language = document.documentElement.lang;
	var regExp = /^[^0-9]{0,}$/;
	var error = "";
	
	var valid = regExp.test(fullname);
	
	if (!valid || fullname.includes("<script>") || fullname.includes("</script>")) {
		error = (language == "es") ? "Nombre completo inválido"
				: "Invalid full name";
		
		input.style.border = "1px solid red";
	} else {
		input.style.border = "1px solid green";
	}
	
	document.getElementById("fullnameDriverError").innerHTML = error;
	
	return error;
}

function validateNationalityDriver() {
	var input = document.getElementById("nationality");
	var nationality = input.value.trim();
	var language = document.documentElement.lang;
	var regExp = /^[^0-9]{0,}$/;
	var error = "";
	
	var valid = regExp.test(nationality);
	
	if (!valid || country.includes("<script>") || country.includes("</script>")) {
		error = (language == "es") ? "Nacionalidad inválido" : "Invalid nationality"; 
	
		input.style.border = "1px solid red";
	} else {
		input.style.border = "1px solid green";
	}
	
	document.getElementById("nationalityDriverError").innerHTML = error;
	
	return error;
}

