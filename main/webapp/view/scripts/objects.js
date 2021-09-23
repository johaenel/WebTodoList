class ItemList {
    constructor() 
	{
        this.listItems = []
    }
    addItem(task) 
	{
		// Add on top
        this.listItems.unshift(task)
    }
    getList() 
	{
        return this.listItems
    }
	deleteItem(taskText,taskCheck)
	{
		for (let index = 0; index < this.listItems.length; index++) { 
            if (this.listItems[index].getText() == taskText)
				if (this.listItems[index].isCheck() == taskCheck)
                	this.listItems.splice(index,1)
        }
	}
    getItem(taskText) 
	{
        for (let index = 0; index < this.listItems.length; index++) {
            const element = this.listItems[index]
            if (element.getText() == taskText)
                return element
        }
    }
	getItem(taskText,taskCheck)
	{
        for (let index = 0; index < this.listItems.length; index++) {
            const element = this.listItems[index]
            if (element.getText() == taskText)
				if (element.isCheck() == taskCheck)
                		return element
        }
    }
	setItem(taskText, task)
	{
		for (let index = 0; index < this.listItems.length; index++) {
            const element = this.listItems[index]
            if (element.getText() == taskText)
				element.setText(task.getText())
				element.setCheck(task.isCheck())
   				return true
        }
	}
    clearList() 
	{
        return this.listItems.splice(0, this.listItems.length)
    }
}

class Task {
    constructor() {
        this.text = ""
        this.check = false
		this.id = 0
    }

    setText(text) {
        this.text = text
    }

    setCheck(check) {
        this.check = check
    }
	setStatus(status, value)
	{
		this.status.set(status,value)
	}
    getText() {
        return this.text
    }

    isCheck() {
        return this.check
    }
	getId()
	{
		return this.id
	}
	
    fromJson(text, check,id) {
        this.text = text
        this.check = check
		this.id=id
    }
}

class HTMLRender {
	
    renderItemList(taskList) {
        let tr = ""
        let checked = ""
        let count = 0
		
        for (const task of taskList) {
            count += 1
			console.log('html render: '+task.getText())
			
            if (task.isCheck()) {
                checked = "checked"
            } else {
                checked = ""
            }
            /* 
				Ref. index(deprec).html
			*/
            let input = "<td id=\"check\"><input id=\"task-" + count +" "+"\"type=\"checkbox\" onchange=\"updateTaskCheck(this)\""+" "+checked+"></td>" +
						"<td><input type=\"text\" id=\"item\" value=\""+task.getText()+"\" onfocus=\"this.oldvalue=this.value\" onchange=\"updateTaskText(this.oldvalue,this)\"></td>" +
                        "<td id=\"delete\"><i id=\"trash\" class=\"fas fa-trash-alt\" style=\"font-size: large\" onclick=\"deleteTask(this)\"></i></td>"

                    if(task.isCheck()){
                        tr += "<tr class=\"table-success\">" + input + "</tr>"
                    } else{
                        tr += "<tr>" + input + "</tr>"
                    }
        }
                    return "<table class=\"table\">"+tr+"</table>"
        }
    }








































