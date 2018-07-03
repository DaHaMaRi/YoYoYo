/**
 * 
 */
$(function register() {
  $("#register input").jqBootstrapValidation({
    preventSubmit: true,
    submitError: function($form, event, errors) {
    	alert(error);
    },
    submitSuccess: function($form, event) {
      event.preventDefault(); // prevent default submit behaviour
      // get values from FORM
	  var Vorname = $("input#Vorname").val();
      var Nachname = $("input#Nachname").val();
      var Username = $("input#Username").val();
      var EMail = $("input#EMail").val();
      var birthday = $("input#birthday").val();
      var strasse = $("input#strasse").val();
      var hausnummer = $("input#hausnummer").val();
      var plz = $("input#plz").val();
      var ort = $("input#ort").val();
      var passwort = $("input#passwort").val();
      var passwortBestaetigen = $("input#passwortBestaetigen").val();
      $this = $("#registerButton");
      $this.prop("disabled", true);
      
      $.ajax({
        url: "http://localhost:8080/YummyYogurt/sign-up.html",
        type: "POST",
        data: {
        	 Vorname : Vorname,
             Nachname : Nachname,
             Username : Username,
             EMail  : EMail,
             birthday : birthday,
             strasse : strasse,
             hausnummer : hausnummer,
             plz : plz,
             ort : ort,
             passwort : passwort
        },
        cache: false,
        success: function() {
          // Success message
        	window.location.href = "http://localhost:8080/YummyYogurt/Content/htdocs/log-in.html";
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

