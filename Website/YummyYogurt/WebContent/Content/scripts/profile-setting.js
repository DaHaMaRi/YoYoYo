$.ajax({
	url: "http://localhost:8080/YummyYogurt/ProfileSettings",
    method: "GET",
    dataType: "json",
    
    success: function(result) {
    	editPage(result);
    },
    
    error: function(xhr, status, errorThrown) {
		handleError(xhr, status, errorThrown);
	}
});

function editPage(user) {
	$("#firstname").attr("value", user.firstname);
	$("#familyname").attr("value", user.familyname);
	$("#username").attr("value", user.username);
	$("#email").attr("value", user.email);
	
	$("#streetname").attr("value", user.address.streetname);
	$("#streetnumber").attr("value", user.address.streetnumber);
	$("#additional").attr("value", user.address.additional);
	$("#postalcode").attr("value", user.address.postalCode);
	$("#city").attr("value", user.address.city);
}


$("#submitUserData").on("click", function() {
	$.ajax({
		url: "http://localhost:8080/YummyYogurt/ProfileSettings",
	    data: $("#userForm").serialize(),
	    method: "POST",
	    
	    contentType: "application/x-www-form-urlencoded; charset=UTF-8"
	})
	.done(function(data, textStatus, xhr) {
		
	})
	.fail(function(xhr, textStatus, errorThrown) {
		
	}); 
});


function handleError(xhr, textStatus, errorThrown) {
	alert("Sorry, there was a problem!");
    console.log("Error: " + errorThrown);
    console.log("Status: " + status );
    console.log(xhr);
}
