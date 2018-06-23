/**
 * 
 */

$.ajax({
	url: "http://localhost:8080/YummyYogurt/ordering.html",
    data: {id: 1},
    method: "GET",
    dataType: "json",
    
    success: function(result) {
    	editPage(result);
    },
    
    error: function(xhr, status, errorThrown) {
		handleError(xhr, status, errorThrown);
	}
});



function editPage(ordering) {
    $("#firstname").html(ordering.firstname);
    $("#name").html(" "+ ordering.familyname);
    $("#street").html(ordering.address.streetname);
    $("#housenumber").html(" "+ ordering.address.streetnumber);
    $("#postalcode").html(ordering.address.postalCode);
    $("#city").html(" "+ordering.address.city);
    
    $("#streetnameM").val(ordering.address.streetname);
    $("#housenumberM").val(ordering.address.streetnumber);
    $("#postalcodeM").val(ordering.address.postalCode);
    $("#cityM").val(ordering.address.city);
    
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


