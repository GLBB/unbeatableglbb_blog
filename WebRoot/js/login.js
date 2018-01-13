function reg(){	
	var register = document.getElementById("register");
	var login = document.getElementById("login");
	
	var loginMode = document.getElementById("loginMode");
	var registerMode = document.getElementById("registerMode");
	
	registerMode.style.backgroundColor = "red";
	loginMode.style.backgroundColor = "white";
	
	login.style.display = "none";
	register.style.display = "block";
}

function logi(){
	var register = document.getElementById("register");
	var login = document.getElementById("login");
	
	var loginMode = document.getElementById("loginMode");
	var registerMode = document.getElementById("registerMode");
	
	registerMode.style.backgroundColor = "white";
	loginMode.style.backgroundColor = "red";
	
	login.style.display = "block";
	register.style.display = "none";
	
}