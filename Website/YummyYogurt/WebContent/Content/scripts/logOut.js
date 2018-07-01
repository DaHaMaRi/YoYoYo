/**
 * 
 */

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


$('.logOut').click(function() {
    			logOut();  //-->this will alert id of checked checkbox
});
    

