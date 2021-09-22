package model;

public class Task{
	String text;
	boolean check;
	
	public Task(String name, boolean checked) {
		super();
		this.text = name;
		this.check = checked;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean checked) {
		this.check = checked;
	}

	@Override
	public String toString() {
		String json = "{"
				+ "name: "+this.getText()+" "
				+ "check: "+this.isCheck()+" "
				+"}";
		
		return json;
	}   
}

