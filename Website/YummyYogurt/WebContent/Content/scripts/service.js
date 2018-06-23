$.ajax({
	url: "http://localhost:8080/YummyYogurt/Mixer/Zutaten",
    method: "GET",
    dataType: "json",
    
    success: function(result) {
    	editIngredients(result);
    },
    
    error: function(xhr, status, errorThrown) {
		handleError(xhr, status, errorThrown);
	}
});

function editIngredients(ingredients) {
	$.each(ingredients, function(index, ingredient) {
		switch(ingredient.category.name) {
			case "Joghurtbasis":
				$("#base").append("<label class=\"btn btn-primary mb-2 mr-1\"><input type=\"radio\" name=\"options\" id=\"option"+ index + "\" autocomplete=\"off\">" + ingredient.name + "</label>");
				break;
	 		case "Früchte":			
	 			$("#fruits").append("<label class=\"btn btn-danger mb-2 mr-1\"><input type=\"checkbox\" autocomplete=\"off\">" + ingredient.name + "</label>");	
	 			break;
	 		case "Nüsse und Kerne":	
	 			$("#nuts").append("<label class=\"btn btn-warning text-white mb-2 mr-1\"><input type=\"checkbox\" autocomplete=\"off\">" + ingredient.name + "</label>");	
	 			break;
	 		case "Schoko":			
	 			$("#chocolate").append("<label class=\"btn btn-chocolate mb-2 mr-1\"><input type=\"checkbox\" autocomplete=\"off\">" + ingredient.name + "</label>"); 	
	 			break;
	 		case "Süßigkeiten":		
	 			$("#sweets").append("<label class=\"btn btn-sweets mb-2 mr-1\"><input type=\"checkbox\" autocomplete=\"off\">" + ingredient.name + "</label>");
	 			break;
	 		case "Soßen":
	 			$("#sauce").append("<label class=\"btn btn-info mb-2 mr-1\"><input type=\"checkbox\" autocomplete=\"off\">" + ingredient.name + "</label>");
	 			break;
	 	}
	});
}


$.ajax({
	url: "http://localhost:8080/YummyYogurt/Mixer/Kategorie",
    method: "GET",
    dataType: "json",
    
    success: function(result) {
    	editCategories(result);
    },
    
    error: function(xhr, status, errorThrown) {
		handleError(xhr, status, errorThrown);
	}
});

function editCategories(categories) {
	console.log(categories.name);
	$.each(categories, function(index,category) {
		var price = category.priceInCents/100;
		var title = category.name + " - " + price.toFixed(2) + "€";
		$("h4").eq(index).html(title);
	});
}

function handleError(xhr, status, errorThrown) {
	alert("Sorry, there was a problem!");
    console.log("Error: " + errorThrown);
    console.log("Status: " + status );
    console.log(xhr);
}
