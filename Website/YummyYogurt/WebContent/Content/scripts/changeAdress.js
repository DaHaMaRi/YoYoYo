/**
 * 
 */
$(function() {

  $("#contactForm input").jqBootstrapValidation({
    preventSubmit: true,
    submitError: function($form, event, errors) {
    	alert(error);
    },
    submitSuccess: function($form, event) {
      event.preventDefault(); // prevent default submit behaviour
      // get values from FORM
	var id = 1;
      var street_name = $("input#streetnameM").val();
      var housenumber = $("input#housenumberM").val();
      var city = $("input#cityM").val();
      var postalcode = $("input#postalcodeM").val();
      alert(city);
      $this = $("#changeAdressButton");
      $this.prop("disabled", true);
      
      $.ajax({
        url: "http://localhost:8080/YummyYogurt/changeAddress.html",
        type: "POST",
        data: {
          id: id,
          street_name: street_name,
          housenumber: housenumber,
          city: city,
          postalcode: postalcode
        },
        cache: false,
        success: function() {
          // Success message
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
          // Fail message
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

