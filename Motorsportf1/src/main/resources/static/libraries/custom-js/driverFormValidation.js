function validateDriverForm() {
	var errorFullname = validateFullnameDriver();
	var errorCountry = validateCountryDriver();
	
	return errorFullname.length == 0 && errorCountry.length == 0;
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

function validateCountryDriver() {
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
	
	document.getElementById("countryDriverError").innerHTML = error;
	
	return error;
}

