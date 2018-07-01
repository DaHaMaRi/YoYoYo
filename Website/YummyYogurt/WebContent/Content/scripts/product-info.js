var url = window.location.href;
var querystring = url.split("?")[1];


$.ajax({
	url: "http://localhost:8080/YummyYogurt/product-info.html?" + querystring,
    method: "GET",
    dataType: "json",
    
    success: function(result) {
    	editPage(result);
    },
    
    error: function(xhr, status, errorThrown) {
		handleError(xhr, status, errorThrown);
	}
});

$("#AddToCart").on("click", function() {
	var amount = $("#amount").val();
	
	querystring = querystring + "&m=" + amount; 
	
	
	 window.location.href = "http://localhost:8080/YummyYogurt/Content/htdocs/shopping-cart.html?"+querystring;
	    
	
});


function editPage(productInfo) {
	$("title").html(productInfo.name);
    $("h1").html(productInfo.name);
    $("li.breadcrumb-item.active").html(productInfo.name);
    
    createIngredientList(productInfo.recipe);
    createCategorySection(productInfo.recipe);
    calculatePrice(productInfo.recipe);
}

function createIngredientList(recipe) {
	$.each(recipe, function(index, ingredient) {
		$("#recipe").append("<li>" + ingredient.name + "</li>");
	});
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

function calculatePrice(recipe) {
	var price = 0;
	$.each(recipe, function(index, ingredient) {
		price += ingredient.category.priceInCents;
	});
	price /= 100;
	$("#price").html("Preis pro Becher: " + price.toFixed(2) + "€");
}


function handleError(xhr, status, errorThrown) {
	alert("Sorry, there was a problem!");
    console.log("Error: " + errorThrown);
    console.log("Status: " + status );
    console.log(xhr);
}
