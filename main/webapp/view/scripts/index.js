const itemList = new ItemList()
const render = new HTMLRender()

function loadAllTask() {
	//get()
	console.log("M-am încărcat")
}

function addTask() {
	let task = new Task()
	let taskHTML = document.getElementById('task')

	if (taskHTML.value != "") {
		task.setText(taskHTML.value)	
		taskHTML.value = ""
		itemList.addItem(task)
		
		let myContainer = document.getElementById("container-list")
		myContainer.innerHTML = render.renderItemList(itemList.getList())
	} else {
		window.alert("Please enter a Task! Empty tasks are not allowed!")
	}
}

function deleteTask(e){
	let currentTask = e.parentElement.previousSibling.firstChild
	itemList.deleteItem(currentTask.value)

	currentTask = e.parentNode.parentNode
	currentTask.className = "to-remove"
	
	deleteAnime()
	
	currentTask.addEventListener ("animationend",function(){
		let myContainer = document.getElementById("container-list")
		myContainer.innerHTML = render.renderItemList(itemList.getList())
	})
}

function updateTaskText(newText, oldText){
	let task = itemList.getItem(oldText)
	task.setText(newText)	
}

function updateTaskCheck(e) {
	let tr = e.parentElement.parentElement
	let taskHTML = tr.childNodes[1].firstChild
	
	let task = itemList.getItem(taskHTML.value)
		if (e.checked) {
			tr.className += "table-success"
			task.setCheck(true)
		} else {
			tr.className = ""
			task.setCheck(false)
		}

		// Send Update to Server
		//postTask(JSON.stringify(myItem))
	
}

function addTaskEnter(event) {
	if (event.keyCode === 13) {
		event.preventDefault()
		document.getElementById('enter').click()
	}
}

function post(data) {
	if (typeof (data) === 'string') {
		let xhttp = new XMLHttpRequest()
		let myContainer = document.getElementById("container-list")
		itemList.clearList()

		xhttp.onload = function () {
			console.log("Received:")
			console.log(this.response)
			let itemStringList = this.responseText
			if (this.readyState == 4) {
				let itemArray = JSON.parse(itemStringList)
				itemArray.forEach(json => {
					task = new Task()
					task.fromJson(json.name, json.check)
					itemList.addItem(task)
				});
				myContainer.innerHTML = ""
				myContainer.innerHTML = render.renderItemList(itemList.getList())
			}
		}
		/** Send */
		console.log("Send:")
		console.log(data)
		/**		 */
		xhttp.open("POST", "http://localhost:8080/WebAppTodoList/Task", true)
		xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded")
		xhttp.send(data)

	} else {
		console.log("Data is not of type string! No data sent to server!")
	}
}

function get() {
	let xhttp = new XMLHttpRequest()
	let myContainer = document.getElementById("container-list")
	itemList.clearList()

	xhttp.onload = function () {
		/** Receive */
		console.log("Received:")
		console.log(this.response)
		/**			*/
		let itemStringList = this.responseText
		if (this.readyState == 4) {
			let itemArray = JSON.parse(itemStringList)
			itemArray.forEach(json => {
				task = new Task()
				task.fromJson(json.name, json.check)
				itemList.addItem(task)
			});
			myContainer.innerHTML = ""
			myContainer.innerHTML = render.printList(itemList.getList())
		}
	}
	xhttp.open("GET", "http://localhost:8080/WebAppTodoList/Task", false)
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded")
	xhttp.send()
}