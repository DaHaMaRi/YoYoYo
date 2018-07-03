$.ajax({
	url: "http://localhost:8080/YummyYogurt/profil.html",
    method: "GET",
    dataType: "json",
    
    success: function(result) {
    	editPage(result);
    	check();
    	//alert(JSON.stringify(result));
    },
    
    error: function(xhr, status, errorThrown) {
    	//alert(JSON.stringify(result));
		handleError(xhr, status, errorThrown);
	}
});



function editPage(profil) {
	$("title").html(profil[0].username);
    $("#username").html(profil[0].username);
    $("#namevorname").html(profil[0].firstname +" "+ profil[0].familyname);
    $("#email").html(profil[0].email);
    $.each(profil[1], function(index, yogurt) {
    	//alert(JSON.stringify(yogurt));
    	loadYogurt(yogurt, profil[2][index]);
	});
    
    
}

function loadYogurt(Yogurt, rating) {
	$("#myYogurts").append('<div class="col-lg-3 col-md-4 col-sm-6 portfolio-item"><div class="card h-100"><a href="product-info.html?id='+Yogurt.id+'"><img class="card-img-top" src="../images/Yogurt5.jpg" height="200px" alt=""></a>              <div class="card-body"><h4 class="card-title"><a href="product-info.html?id='+Yogurt.id+'">'+Yogurt.name+'</a></h4><p class="card-text"><p id="ingrediant'+Yogurt.id+'"></p></p><p><strong id=preis'+Yogurt.id+'></strong></p><div id="category'+Yogurt.id+'"></div> </div><div class="card-footer text-center"><div id="rating'+Yogurt.id+'"></div><label class="switch"><input type="checkbox" class="visebel" id="'+Yogurt.id+'"><span class="slider round"></span> </label></div></div></div>');
	//alert("vor insert");
	createIngredientList(Yogurt);
	calculatePrice(Yogurt);
	createCategorySection(Yogurt);
	createRatings(Yogurt, rating);
	//alert("nach insert");
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


