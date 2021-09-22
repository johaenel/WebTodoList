document.addEventListener('readystatechange', function() {
	if (document.readyState === "complete") {
		validateUser()
		validatePass()
		validateForm()
	}
});


function validateUser() {
	document.getElementById("userName").addEventListener("input", function() {
		user = { "username": "johy" }
		input = document.getElementById("userName").lastElementChild
		message = document.getElementById("userName").firstElementChild.lastElementChild
		if (input.value != user.username && input.value != "") {
			message.className = ""
			message.innerHTML = "valid"
			message.style.color = "green";
		} else {
			message.className = "message"
			message.innerHTML = "invalid"
			message.style.color = "red";
		}
	});
}

function validatePass() {
	let define = document.getElementById("define")
	let confirm = document.getElementById("confirm")
	let message = confirm.previousElementSibling.firstElementChild
	confirm.addEventListener("change", function() {
		if (define.value != confirm.value) {
			message.className = "message"
			message.innerHTML = "invalid"
			message.style.color = "red";
		} else {
			message.className = ""
			message.innerHTML = "valid"
			message.style.color = "green";
		}
	})
}

function validateForm() {
	document.getElementsByTagName("form")[0].addEventListener("submit", function() {
		let message = document.getElementsByClassName("message")
		if (message.length > 0) {
			alert("Formular nu este îndeplinit corect!")
			return false
		} else {
			return true
		}
	})
}

function post() {

}
function get() {

}