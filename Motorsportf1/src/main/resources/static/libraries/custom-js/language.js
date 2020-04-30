function changeLanguageAction() {
	$("#selectLanguage-custom").change(function(){
		var value = $(this).val();
								
		var currentHref = window.location.href;
		var url = new URL(currentHref);
		
		if (window.location.search.includes("language")) {
			
			url.searchParams.set("language", value);	
			
		} else {
			
			url.searchParams.append("language", value);
		
		}
		
		window.location.href = url;
	});
}