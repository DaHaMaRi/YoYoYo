/**
 * 
 */
/**
 * 
 */
function changeAmount(yid, m){
	
	$.ajax({
	url: "http://localhost:8080/YummyYogurt/shopping-cart.html",
    method: "GET",
    dataType: "json",
    data: {
    	yid : yid,
    	m : m
    },
    
    success: function(result) {
    	//alert(JSON.stringify(result));
    	editPage(result);
    	//check();
    	//alert(JSON.stringify(result));
    },
    
    error: function(xhr, status, errorThrown) {
    	//alert(JSON.stringify(result));
    	alert("no");
		handleError(xhr, status, errorThrown);
	}
});



function editPage(profil) {
	$.each(profil[0], function(index, y) {
		if(y.id == yid){
			calculatePrice(y,m);
		}
	});
	
	calculateFinal(profil);
}

function calculateFinal(profil){
	var sum = 0;
	$.each(profil[0], function(index, Yogurt) {
		var price = 0;
		$.each(Yogurt.recipe, function(index, ingredient) {
			price += ingredient.category.priceInCents;
		});
    	sum += price*profil[1][index];
	});
	sum /= 100;
	
	$("#Gesamt").html(sum.toFixed(2) + "€         ");
	$("#FinalMwst").html((sum*0.19).toFixed(2) + "€");
	$("#Endsumme").html(sum.toFixed(2) + "€         ");
}

function loadYogurt(yogurt, menge) {
	
	//alert("vor insert");
	
	//alert("nach insert");
}

function createIngredientList(Yogurt) {
	$.each(Yogurt.recipe, function(index, ingredient) {
		$("#Zutaten"+Yogurt.id).append("<span>" + ingredient.name + ",  <span>");
	});
}

function calculatePrice(yogurt, menge) {
	var price = 0;
	$.each(yogurt.recipe, function(index, ingredient) {
		price += ingredient.category.priceInCents;
	});
	price /= 100;
	$("#Einzelpreis"+yogurt.id).html(price.toFixed(2) + "€         ");
	$("#Summe"+yogurt.id).html((price*menge).toFixed(2) + "€");
	$("#Mwst"+yogurt.id).html((price*menge*0.19).toFixed(2) + "€");
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
		$("#Categorien"+Yogurt.id).append("<span>"+category+"</span>");
   	 
		switch(category) {
   	 		case "Früchte":			
   	 			$("#Categorien"+Yogurt.id+" span").eq(index).addClass("badge badge-fruits"); 	
   	 			break;
   	 		case "Nüsse und Kerne":	
   	 			$("#Categorien"+Yogurt.id+" span").eq(index).addClass("badge badge-nuts");		
   	 			break;
   	 		case "Schoko":			
   	 			$("#Categorien"+Yogurt.id+" span").eq(index).addClass("badge badge-chocolate"); 	
   	 			break;
   	 		case "Süßigkeiten":		
   	 			$("#Categorien"+Yogurt.id+" span").eq(index).addClass("badge badge-sweets"); 	
   	 			break;
   	 	}
	});
}

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
