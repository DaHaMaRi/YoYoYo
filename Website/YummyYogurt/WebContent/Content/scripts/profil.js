$.ajax({
	url: "http://localhost:8080/YummyYogurt/profil.html",
    data: {id: 2},
    method: "GET",
    dataType: "json",
    
    success: function(result) {
    	editPage(result);
    },
    
    error: function(xhr, status, errorThrown) {
		handleError(xhr, status, errorThrown);
	}
});



function editPage(profil) {
	$("title").html(profil.username);
    $("#username").html(profil.username);
    $("#namevorname").html(profil.firstname +" "+ profil.familyname);
    $("#email").html(profil.email);
    
}

function createIngredientList(recipe) {
	$.each(recipe, function(index, ingredient) {
		$("#recipe").append("<li>" + ingredient.name + "</li>");
	});
}

function calculatePrice(recipe) {
	var price = 0;
	$.each(recipe, function(index, ingredient) {
		price += ingredient.category.priceInCents;
	});
	price /= 100;
	$("#price").html("Preis pro Becher: " + price.toFixed(2) + "€");
}

function createCategorySection(recipe) {
	var categories = [];
	$.each(recipe, function(index, ingredient) {
		categories.push(ingredient.category.name);
	});
	
	categories = unique(categories);
	categories = categories.filter(function(element) {
		return element !== "Joghurtbasis" && element !== "Soßen";
	});
	
	$.each(categories, function(index, category) {
		$("#categories").append("<span>"+category+"</span>");
   	 
		switch(category) {
   	 		case "Früchte":			
   	 			$("#categories span").eq(index).addClass("badge badge-fruits"); 	
   	 			break;
   	 		case "Nüsse und Kerne":	
   	 			$("#categories span").eq(index).addClass("badge badge-nuts");		
   	 			break;
   	 		case "Schoko":			
   	 			$("#categories span").eq(index).addClass("badge badge-chocolate"); 	
   	 			break;
   	 		case "Süßigkeiten":		
   	 			$("#categories span").eq(index).addClass("badge badge-sweets"); 	
   	 			break;
   	 	}
	});
}

function unique(array) {
    var resultSet = [];
    for(var i = 0; i < array.length; i++) {
    	if ((jQuery.inArray(array[i], resultSet)) == -1) {
    		resultSet.push(array[i]);
        }
    }
    return resultSet;
}

function handleError(xhr, status, errorThrown) {
	alert("Sorry, there was a problem!");
    console.log("Error: " + errorThrown);
    console.log("Status: " + status );
    console.log(xhr);
}

