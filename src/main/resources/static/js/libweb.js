window.onload = function() {
	var dropdown = document.getElementsByClassName("dropdown-a");
	var i;
		
	for (i = 0; i < dropdown.length; i++) {
		dropdown[i].addEventListener("click", function() {
		this.classList.toggle("active");
		var dropdownContent = document.getElementById("dropdown-container");
		if (dropdownContent.style.display === "block") {
			dropdownContent.style.display = "none";
		} else {
			dropdownContent.style.display = "block";
	    }
	});
	}
}

function addSaldo() {
	
}
