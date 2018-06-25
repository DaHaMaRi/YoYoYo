/**
 * 
 */
function makeOrder() {
	  //get price
	  var price = "1099";
      $.ajax({
        url: "http://localhost:8080/YummyYogurt/makeOrder.html",
        type: "POST",
        data : {
        	price : price 
        },
        //cache: true,
        success: function() {
        	window.location.href = "http://localhost:8080/YummyYogurt/Content/htdocs/thanks.html";
          $('#success').html("<div class='alert alert-success'>");
          $('#success > .alert-success').html("<button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;")
            .append("</button>");
          $('#success > .alert-success')
            .append("<strong>Your message has been sent. </strong>");
          $('#success > .alert-success')
            .append('</div>');
          //clear all fields
          $('#contactForm').trigger("reset");
        },
        error: function() {
        	alert("Bestellung fehlgeschlagen");
          $('#success').html("<div class='alert alert-danger'>");
          $('#success > .alert-danger').html("<button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;")
            .append("</button>");
          //$('#success > .alert-danger').append($("<strong>").text("Sorry " + firstName + ", it seems that my mail server is not responding. Please try again later!"));
          $('#success > .alert-danger').append('</div>');
          //clear all fields
          $('#contactForm').trigger("reset");
        }
      });
    }

