/**
 * 
 */
$(function logInTop() {
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
    });

    /*When clicking on Full hide fail/success boxes */
    $('#name').focus(function() {
      $('#success').html('');
    });
