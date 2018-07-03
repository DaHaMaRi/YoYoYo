$.ajax({
	url: "http://localhost:8080/YummyYogurt/Homepage",
    method: "GET",
    dataType: "json",
    
    success: function(result) {
    	editPage(result);
    },
    
    error: function(xhr, status, errorThrown) {
		handleError(xhr, status, errorThrown);
	}
});


function editPage(result) {
	$.each(result[0], function(index, yogurt){
		loadYogurt(yogurt, result[1][index]);
	});
}

function loadYogurt(Yogurt, rating) {
//	var card = "";
//	card += '<div class="col-lg-3 col-md-4 col-sm-6 portfolio-item">';
//	card += '<div class="card h-100">';
//	card += '<img class="card-img-top" src="../images/Yogurt1.jpg" height="200px" alt="">';
//	card += '<div class="card-body">';
//	card += '<h4 class="card-title">';
//	card += '<a href="product-info.html?id='+yogurt.id+'">'+ yogurt.name +'</a></h4>';
//	card += '<p class="card-text" id="'+index+'"></p>';
//	createIngredientList(yogurt.recipe, index);
//	card += '</div></div></div>';
//	$("#content").append(card);
	
	
	$("#content").append('<div class="col-lg-3 col-md-4' 
			+' col-sm-6 portfolio-item"><div class="card h-100">'
			+'<a href="product-info.html?id='+Yogurt.id+'">'
			+'<img class="card-img-top" src="../images/Yogurt5.jpg"'
			+' height="200px" alt=""></a> '
			+'<div class="card-body"><h4 class="card-title">'
			+'<a href="product-info.html?id='+Yogurt.id+'">'
			+''+Yogurt.name+'</a></h4><p class="card-text">'
			+'<p id="ingrediant'+Yogurt.id+'"></p></p><p>'
			+'<strong id=preis'+Yogurt.id+'></strong></p>'
			+'<div id="category'+Yogurt.id+'"></div> </div>'
			+'<div class="card-footer text-center">'
			+'<div id="rating'+Yogurt.id+'"></div>' // Bewertung
			+'</div></div></div>');
	//alert("vor insert");
	createIngredientList(Yogurt);
	calculatePrice(Yogurt);
	createCategorySection(Yogurt);
	createRatings(Yogurt, rating);
}

function createRatings(Yogurt, rating) {
	var value = Math.round(rating);
	for(var i = 0; i < value; i++) {
		$("#rating"+Yogurt.id).append('<span class="fa fa-star"></span>');
	}
	var limit = 5 - value
	for(var k = 0; k < limit; k++) {
		$("#rating"+Yogurt.id).append('<span class="fa fa-star-o"></span>');
	}
}

function createIngredientList(Yogurt) {
	$.each(Yogurt.recipe, function(index, ingredient) {
		$("#ingrediant"+Yogurt.id).append("<span>" + ingredient.name + ",  <span>");
	});
}

function calculatePrice(Yogurt) {
	var price = 0;
	$.each(Yogurt.recipe, function(index, ingredient) {
		price += ingredient.category.priceInCents;
	});
	price /= 100;
	$("#preis"+Yogurt.id).html("Preis pro Becher: " + price.toFixed(2) + "€");
}

function createCategorySection(Yogurt) {
	var recipe = Yogurt.recipe;
	var categories = [];
	$.each(recipe, function(index, ingredient) {
		categories.push(ingredient.category.name);
	});
	
	categories = unique(categories);
	categories = categories.filter(function(element) {
		return element !== "Joghurtbasis" && element !== "Soßen";
	});
	
	$.each(categories, function(index, category) {
		$("#category"+Yogurt.id).append("<span>"+category+"</span>");
   	 
		switch(category) {
   	 		case "Früchte":			
   	 			$("#category"+Yogurt.id+" span").eq(index).addClass("badge badge-fruits"); 	
   	 			break;
   	 		case "Nüsse und Kerne":	
   	 			$("#category"+Yogurt.id+" span").eq(index).addClass("badge badge-nuts");		
   	 			break;
   	 		case "Schoko":			
   	 			$("#category"+Yogurt.id+" span").eq(index).addClass("badge badge-chocolate"); 	
   	 			break;
   	 		case "Süßigkeiten":		
   	 			$("#category"+Yogurt.id+" span").eq(index).addClass("badge badge-sweets"); 	
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