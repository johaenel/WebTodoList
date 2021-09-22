const itemList = new ItemList()
const render = new HTMLRender()

/* 
	Load all Tasks from Server
*/
function loadAllTask() {
	
	// Retrieve User Specific Data
	let user = document.getElementById('userdata')
		if(user != null) {
			SEND('','GET')
			
			// Update Presentation
			renderHTMLList()
	}
}

function addTask(){
	
	// Create New TASK
	let task = new Task()
	let taskHTML = document.getElementById('task')

	// Add Properties to Task
	if (taskHTML.value != "") {
		task.setText(taskHTML.value)	
		taskHTML.value = ""
		
		// Save UserTask
		if(!sendUserTask(task)){ // on Server
			itemList.addItem(task) // on Local 
		}
		
		// Update Presentation
		renderHTMLList()
	} else {
		window.alert("Please enter a Task! Empty tasks are not allowed!")
	}
}

function deleteTask(e){
	
	// Create Object to encapsulate HTML Task
	let task = new Task()
		task.setText(e.parentElement.previousSibling.firstChild.value)
		task.setCheck(e.parentElement.previousSibling.previousSibling.firstChild.checked)
		
	// Delete Task from List 
	if(!deleteUserTask(task)){ // on Server
		itemList.deleteItem(task.getText())	// on Local
	}
	
	// Update Presentation
	currentTask = e.parentNode.parentNode // Prepare HTML Task to be deleted
	currentTask.className = "to-remove"
	deleteAnime() 						// CSS Anumation delete class=to-remove
	currentTask.addEventListener ("animationend",function(){
		renderHTMLList()
	})
}

// TODO update on Server
function updateTaskText(newText, oldText){
	let task = itemList.getItem(oldText)
	task.setText(newText)	
}

// TODO update on Server
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
		
}

/* 
	DELETE a Task on server
*/
function deleteUserTask(task){
	// Check USER
		let user = document.getElementById('userdata')
		if(user != null){
			// DELETE TASK
			SEND(JSON.stringify(task),'DELETE')
			return true
		}else{
			return false
		}
}

/*
	INSERTS new Task
	UPDATES existing Task
	on server
*/
function sendUserTask(task){
	// Check USER
		let user = document.getElementById('userdata')
		if(user != null){
			// POST TASK & USER
			SEND(JSON.stringify(task),'POST')
			return true
		}else{
			return false
		}
}

function addTaskEnter(event) {
	if (event.keyCode === 13) {
		event.preventDefault()
		document.getElementById('enter').click()
	}
}

function renderHTMLList(){
	let myContainer = document.getElementById("container-list")
	myContainer.innerHTML = render.renderItemList(itemList.getList())
}
/* 	
	Request GET, POST, DELETE Task
	Receive updated list of Tasks
*/
function SEND(data, METHOD) {
	
	if (typeof (data) === 'string') {
		let xhttp = new XMLHttpRequest()
		itemList.clearList() // Reset local item list
		
		// Receive All Tasks Updated
		xhttp.onload = function () {
			
			// All statements in this function are Thread Safe!
			
			// Encapsulate Response in local Task objects
			let itemStringList = this.responseText
			
			if (this.readyState == 4) {
				let itemArray = JSON.parse(itemStringList)
				itemArray.forEach(json => {
					let task = new Task()
					task.fromJson(json.text, json.check)
					//console.log('SEND: '+ task.getText())
					itemList.addItem(task)
				});
			}
			
			// Update Presentation
			renderHTMLList()
		}
		
		// Send One Task
		let contentOperation = ''
		if(METHOD == 'DELETE'){
			METHOD = 'POST'
			contentOperation = 'trash'
			
		}
		console.log(METHOD)
		xhttp.open(METHOD, "http://localhost:8080/WebTodoList/Tasks", true)
		xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
		xhttp.setRequestHeader('Content-Operation',contentOperation)
		
		if(METHOD == 'POST'){
			// Debug Send
			console.log(data)
			xhttp.send(data)	
		}else{
			xhttp.send()	
		}
	} else {
		console.error("Data is not of type string! No data sent to server!")
	}
}