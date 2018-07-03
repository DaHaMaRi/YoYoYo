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
				$("#base").append("<label><input name=\""+ingredient.id+"\">" + ingredient.name + "</label>");
				$("#base label").addClass("btn btn-primary mb-2 mr-1");
				
				$("#base input").attr("type", "radio");
				$("#base input").attr("id", "option" + index);
				$("#base input").attr("autocomplete", "off");
				break;
	 		case "Früchte":			
	 			$("#fruits").append("<label><input name=\""+ingredient.id+"\">" + ingredient.name + "</label>");
	 			$("#fruits label").addClass("btn btn-danger mb-2 mr-1");
	 			
	 			$("#fruits input").attr("type", "checkbox");
	 			$("#fruits input").attr("autocomplete", "off");
	 			break;
	 		case "Nüsse und Kerne":	
	 			$("#nuts").append("<label><input name=\""+ingredient.id+"\">" + ingredient.name + "</label>");	
	 			$("#nuts label").addClass("btn btn-warning text-white mb-2 mr-1")
	 			
	 			$("#nuts input").attr("type", "checkbox");
	 			$("#nuts input").attr("autocomplete", "off");
	 			break;
	 		case "Schoko":			
	 			$("#chocolate").append("<label><input name=\""+ingredient.id+"\">" + ingredient.name + "</label>"); 	
	 			$("#chocolate label").addClass("btn btn-chocolate mb-2 mr-1")
	 			
	 			$("#chocolate input").attr("type", "checkbox");
	 			$("#chocolate input").attr("autocomplete", "off");
	 			break;
	 		case "Süßigkeiten":
	 			$("#sweets").append("<label><input name=\""+ingredient.id+"\">" + ingredient.name + "</label>");
	 			$("#sweets label").addClass("btn btn-sweets mb-2 mr-1")
	 			
	 			$("#sweets input").attr("type", "checkbox");
	 			$("#sweets input").attr("autocomplete", "off");
	 			break;
	 		case "Soßen":
	 			$("#sauce").append("<label><input name=\""+ingredient.id+"\">" + ingredient.name + "</label>");
	 			$("#sauce label").addClass("btn btn-info mb-2 mr-1")
	 			
	 			$("#sauce input").attr("type", "checkbox");
	 			$("#sauce input").attr("autocomplete", "off");
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
	$.each(categories, function(index,category) {
		var price = category.priceInCents/100;
		var title = category.name + " - " + price.toFixed(2) + "€";
		$("h4").eq(index).html(title);
	});
}

$("#submitForm").on("click", function() {
	$.ajax({
		url: "http://localhost:8080/YummyYogurt/Mixer",
	    data: $("#saveFrozenYogurt").serialize(),
	    method: "POST",
	    
	    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
	    dataType: "json"
	})
	.done(function(data, textStatus, xhr) {
		window.location.href = "http://localhost:8080/YummyYogurt/Content/htdocs/product-info.html" + "?id=" + data.id;
	})
	.fail(function(xhr, textStatus, errorThrown) {
		window.location.href = "http://localhost:8080/YummyYogurt/Content/htdocs/log-in.html";
	}); 
	return false;
});

function handleError(xhr, textStatus, errorThrown) {
	alert("Sorry, there was a problem!");
    console.log("Error: " + errorThrown);
    console.log("Status: " + status );
    console.log(xhr);
}
