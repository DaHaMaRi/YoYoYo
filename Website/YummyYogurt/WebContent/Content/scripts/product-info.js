$.ajax({
	url: "http://localhost:8080/YummyYogurt/product-info.html",
    data: {id: 1},
    type: "GET",
    dataType: "json"
})
        	
.done(function(json) {
     $("h1").html(json.name);
     $("li.breadcrumb-item.active").html(json.name);
     
     var categories = [];
     var priceOfIngredient = [];
     
     $.each(json.recipe, function(index, ingredient) {
    	 $("#recipe").append("<li>"+ingredient.name+"</li>");
    	 categories.push(ingredient.category.name);
    	 categories.push(ingredient.category.priceInCents);
     });
     
     var price = 0;
     for(var i = 0; i < priceOfIngredient.length; i++) {
    	 price = price + priceOfIngredient[i];
     }
     price = price / 100;
     $("#price").html("Preis pro Becher: " + price);
     
     categories = unique(categories);
     categories = categories.filter(function(element) {
    	 return element !== "Joghurtbasis" && element !== "Soßen";
     });
     
     $.each(categories, function(index, category) {
    	 $("#categories").append("<span>"+category+"</span>");
    	 
    	 switch(category) {
    	 	case "Früchte":			$("#categories span").eq(index).addClass("badge badge-fruits"); 	break;
    	 	case "Nüsse und Kerne":	$("#categories span").eq(index).addClass("badge badge-nuts");		break;
    	 	case "Schoko":			$("#categories span").eq(index).addClass("badge badge-chocolate"); 	break;
    	 	case "Süßigkeiten":		$("#categories span").eq(index).addClass("badge badge-sweets"); 	break;
    	 }
     });
})
        	
.fail(function(xhr, status, errorThrown) {
	alert("Sorry, there was a problem!");
    console.log("Error: " + errorThrown);
    console.log("Status: " + status );
    console.log(xhr);
});


function unique(array) {
    var resultSet = [];
    for(var i = 0; i < array.length; i++) {
    	if ((jQuery.inArray(array[i], resultSet)) == -1) {
    		resultSet.push(array[i]);
        }
    }
    return resultSet;
}
