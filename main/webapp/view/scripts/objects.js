class ItemList {
    constructor() {
        this.listItems = []
    }
    
    addItem(item) {
		// Add on top
        this.listItems.unshift(item)
    }

    fillList(array) {
        try {
            this.listItems = array
        } catch (error) {
            console.log("Input is not array")
            console.log(error)
        }

    }
	
    getList() {
        return this.listItems
    }

    clearList() {
        return this.listItems.splice(0, this.listItems.length)
    }
	
	deleteItem(itemText){
		for (let index = 0; index < this.listItems.length; index++) { 
            if (this.listItems[index].getText() == itemText)
                this.listItems.splice(index,1)
        }
	}
	
    getItem(itemText) {
        for (let index = 0; index < this.listItems.length; index++) {
            const element = this.listItems[index]
            if (element.getText() == itemText)
                return element
        }
    }
}

class Task {
    constructor() {
        this.text = ""
        this.check = false
    }

    setText(text) {
        this.text = text
    }

    setCheck(check) {
        this.check = check
    }

    getText() {
        return this.text
    }

    isCheck() {
        return this.check
    }
    fromJson(text, check) {
        this.text = text
        this.check = check
    }
}

class HTMLRender {
	
    renderItemList(taskList) {
        let tr = ""
        let checked = ""
        let count = 0

        for (const task of taskList) {
            count += 1

            if (task.isCheck()) {
                checked = "checked"
            } else {
                checked = ""
            }
            
            let input = "<td id=\"check\"><input id=\"task-" + count +" "+"\"type=\"checkbox\" onchange=\"updateTaskCheck(this)\""+" "+checked+"></td>" +
						"<td><input type=\"text\" id=\"item\" value=\""+task.getText()+"\" onfocus=\"this.oldvalue=this.value\" onchange=\"updateTaskText(this.value,this.oldvalue)\"></td>" +
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








































