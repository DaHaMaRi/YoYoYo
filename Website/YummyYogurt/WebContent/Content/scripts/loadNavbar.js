/**
 * 
 */
$.ajax({
	url: "http://localhost:8080/YummyYogurt/navbar",
    method: "GET",
    dataType: "json",
    
    success: function(result) {
    	if(result[0] == 1){
    		pageUser(result[1]);
    	}else{
    		pageDefault();
    	}
    	logInTop();
    	checkLogout();
    	
    	//check();
    	//alert(JSON.stringify(result));
    },
    
    error: function(xhr, status, errorThrown) {
    	//alert(JSON.stringify(result));
		handleError(xhr, status, errorThrown);
	}
});

function pageDefault() {
	var topLogin ="";
	topLogin += '<a class="nav-link dropdown-toggle" href="#" id="navbarDropdownLogIn" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">';
	topLogin += 'Anmelden  <span class="fa fa-user"></span>';
	topLogin += '</a>';
	topLogin += '<div class="dropdown-menu dropdown-menu-right p-2" aria-labelledby="navbarDropdownLogIn">';
	topLogin += '<form name="logInTop" id="logInTop">';
	topLogin += '<label>Benutzername</label>';
	topLogin += '<input type="text" class="form-control"name="username" id="username" value="" />';
	topLogin += '<label>Passwort</label>';
	topLogin += '<input type="password" class="form-control" name="password" id="password" value="" />';
	topLogin += '<button name="logInTopButton" id="logInTopButton"  type="submit" class="button">Anmelden</button>';
	topLogin += '</form>';
	topLogin += '<a href="sign-up.html"><small>Ich habe noch keinen Account</small></a>';
	topLogin += '</div>';
	$("#dynamicNavbar").append(topLogin);
}

function pageUser(data) {
	var topUser = "";
	topUser += '<a class="nav-link dropdown-toggle text-ice-blue" href="#" id="navbarDropdownLogIn" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">';
	topUser += data.username+'  <span class="fa fa-user-circle"></span>';
	topUser += '</a>';
	topUser += '<div class="dropdown-menu dropdown-menu-right p-2" aria-labelledby="navbarDropdownLogIn">';
	topUser += '<a class="dropdown-item" href="profile.html">Profil</a>';
	topUser += '<a class="dropdown-item" href="profile-setting.html">Einstellungen</a>';
	topUser += '<button  type="button" id="logOutButton" name="logOutButton" class="dropdown-item logOut">Abmelden</button>';
	topUser += '</div>';
	$("#dynamicNavbar").append(topUser);	
}

function handleError(xhr, status, errorThrown) {
	alert("Sorry, there was a problem!");
    console.log("Error: " + errorThrown);
    console.log("Status: " + status );
    console.log(xhr);
}

function logInTop() {
	  $("#logInTop input").jqBootstrapValidation({
	    preventSubmit: true,
	    submitError: function($form, event, errors) {
	    	alert(error);
	    },
	    submitSuccess: function($form, event) {
	      event.preventDefault(); // prevent default submit behaviour
	      // get values from FORM
		  
	      var Username = $("input#username").val();
	      var passwort = $("input#password").val();
	      $this = $("#logInTopButton");
	      $this.prop("disabled", true);
	      
	      $.ajax({
	        url: "http://localhost:8080/YummyYogurt/log-in.html",
	        type: "POST",
	        data: {
	             Username : Username,
	             passwort : passwort
	        },
	        success: function() {
	          // Success message
	        	window.location.href = "http://localhost:8080/YummyYogurt/Content/htdocs/profile.html";
	        },
	        error: function() {
	          // Fail message
	        	alert("fehlgeschagen");
	          $('#success').html("<div class='alert alert-danger'>");
	          $('#success > .alert-danger').html("<button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;")
	            .append("</button>");
	          //$('#success > .alert-danger').append($("<strong>").text("Sorry " + firstName + ", it seems that my mail server is not responding. Please try again later!"));
	          $('#success > .alert-danger').append('</div>');
	          //clear all fields
	          $('#contactForm').trigger("reset");
	        },
	        complete: function() {
	          setTimeout(function() {
	            $this.prop("disabled", false); // Re-enable submit button when AJAX call is complete
	          }, 1000);
	        }
	      });
	    },
	      filter: function() {
	          return $(this).is(":visible");
	        },
	      });

	      $("a[data-toggle=\"tab\"]").click(function(e) {
	        e.preventDefault();
	        $(this).tab("show");
	      });
	    }

	    /*When clicking on Full hide fail/success boxes */
	    $('#name').focus(function() {
	      $('#success').html('');
	    });

	    
	    

	    function logOut() {
	      
	          $.ajax({
	            url: "http://localhost:8080/YummyYogurt/logOut",
	            type: "GET",
	            
	            success: function() {
	              // Success message
	            	window.location.href = "http://localhost:8080/YummyYogurt/Content/htdocs/index.html";
	            },
	            error: function() {
	              // Fail message
	            	alert("fehlgeschagen");
	              $('#success').html("<div class='alert alert-danger'>");
	              $('#success > .alert-danger').html("<button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;")
	                .append("</button>");
	              //$('#success > .alert-danger').append($("<strong>").text("Sorry " + firstName + ", it seems that my mail server is not responding. Please try again later!"));
	              $('#success > .alert-danger').append('</div>');
	              //clear all fields
	              $('#contactForm').trigger("reset");
	            },
	            complete: function() {
	              setTimeout(function() {
	                $this.prop("disabled", false); // Re-enable submit button when AJAX call is complete
	              }, 1000);
	            }
	          });
	    }

function checkLogout(){
	    $('.logOut').click(function() {
	        			logOut();  //-->this will alert id of checked checkbox
	    });
}
	        

