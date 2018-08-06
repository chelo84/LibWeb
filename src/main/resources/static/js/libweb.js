//Implementação da função de 'dropdown' do menu vertical
window.addEventListener('load', function() {
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
});
	
//Implementação do botão de adicionar saldo à carteira do usuário
window.addEventListener('load', function() {
	var modal = document.getElementById('addSaldoModal');

	var btn = document.getElementById("addSaldoBtn");

	var span = document.getElementsByClassName("close")[0];

	btn.onclick = function() {
	    modal.style.display = "block";
	}

	span.onclick = function() {
	    modal.style.display = "none";
	}

	window.onclick = function(event) {
	    if (event.target == modal) {
	        modal.style.display = "none";
	    }
	}
});

function transformarData(dataRecebida) {
	var data = new Date(dataRecebida);
	
	var dia = data.getDate()+1;
	dia = (dia < 10) ? "0"+ dia : dia;
	
	var mes = data.getMonth()+1;
	mes = (mes < 10) ? "0"+ mes : mes;
	var ano = data.getFullYear();
	
	return dia +"/"+ mes +"/"+ ano;
}