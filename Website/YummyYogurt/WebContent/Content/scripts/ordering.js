/**
 * 
 */

$.ajax({
	url: "http://localhost:8080/YummyYogurt/ordering.html",
    data: {id: 1},
    method: "GET",
    dataType: "json",
    
    success: function(result) {
    	editAddress(result[0]);
    	editCart(result[1]);
    },
    
    error: function(xhr, status, errorThrown) {
		handleError(xhr, status, errorThrown);
	}
});



function editAddress(ordering) {
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


function editCart(profil) {
	
    $.each(profil[0], function(index, Yogurt) {
    	//alert(JSON.stringify(yogurt));
    	loadYogurt(Yogurt,profil[1][index]);
	});
    
    $("#warenkorb").append('<table class="border rounded bg-light mb-3"><tbody><tr><td class="col-2 w-25 px-0 align-top"></td><td class="col-6 align-text-top"></td><td class="col-3"><ul style="list-style-type: none"><li><strong class="mr-5">Gesamtsumme: </strong></li><li><strong class="mr-5">inkl. MwSt (19 %):</strong></li><li><strong class="mr-5">Endsumme:</strong></li></ul></td><td class="col-1 text-right"><ul style="list-style-type: none"><li><strong id="Gesamt" class="mr-5"></strong></li><li><strong id="FinalMwst" class="mr-5"></strong></li><li><strong id="Endsumme" class="mr-5"></strong></li></ul></td></tr></tbody></table>');
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

function loadYogurt(Yogurt, menge) {
	
	$("#warenkorb").append('<table class="border rounded bg-light mb-3"><tbody><tr><td class="col-2 w-25 px-0 align-top"><a href="product-info.html"><img class="img-fluid" src="../images/Yogurt1.jpg" height="200px" alt=""></a></td><td class="col-6 align-text-top"><h3><a href="product-info.html">'+Yogurt.name+'</a></h3><p id="Zutaten'+Yogurt.id+'"></p><div id="Categorien'+Yogurt.id+'"></div></td><td class="col-3"><ul style="list-style-type: none"><li><strong class="mr-5" >Einzelpreis:</strong></li><li><strong class="mr-5" >Menge: </strong></li><li><strong class="mr-5">Summe: </strong></li><li><strong class="mr-5">inkl. 19% MwSt:</strong></li></ul></td><td class="col-1"><ul style="list-style-type: none"><li><strong class="mr-5" id="Einzelpreis'+Yogurt.id+'"></strong></li><li><strong class="mr-5" id="Menge'+Yogurt.id+'">'+menge+'</strong></li><li><strong id="Summe'+Yogurt.id+'" class="mr-5"></strong></li><li><strong id="Mwst'+Yogurt.id+'" class="mr-5"></strong></li></ul></td></tr></tbody></table>');
	//alert("vor insert");
	createIngredientList(Yogurt);
	createCategorySection(Yogurt);
	calculatePrice(Yogurt, menge);
	//alert("nach insert");
}

function createIngredientList(Yogurt) {
	$.each(Yogurt.recipe, function(index, ingredient) {
		$("#Zutaten"+Yogurt.id).append("<span>" + ingredient.name + ",  <span>");
	});
}

function calculatePrice(Yogurt, menge) {
	var price = 0;
	$.each(Yogurt.recipe, function(index, ingredient) {
		price += ingredient.category.priceInCents;
	});
	price /= 100;
	$("#Einzelpreis"+Yogurt.id).html(price.toFixed(2) + "€         ");
	$("#Summe"+Yogurt.id).html((price*menge).toFixed(2) + "€");
	$("#Mwst"+Yogurt.id).html((price*menge*0.19).toFixed(2) + "€");
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


