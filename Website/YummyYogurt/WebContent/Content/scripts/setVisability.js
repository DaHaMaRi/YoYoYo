/**
 * 
 */
function setVis(id) {
	//alert(id+" "+document.getElementById(id).checked);
$.ajax({
	url: "http://localhost:8080/YummyYogurt/profil.html",
    data: {YID: id,
    	checked : document.getElementById(id).checked },
    method: "POST",
    
    success: function(result) {
    	//alert("seccuss");
    },
    
    error: function(xhr, status, errorThrown) {
		alert("konnte nicht gesetzt werden");
		location.reload(true)
	}
});
}