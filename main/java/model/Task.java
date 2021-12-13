package model;

public class Task{
	String name;
	boolean check;
	
	public Task(String name, boolean checked) {
		super();
		this.name = name;
		this.check = checked;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCheck() {
		return check;
	}

	public void setChecked(boolean checked) {
		this.check = checked;
	}

	@Override
	public String toString() {
		String json = "{"
				+ "name: "+this.getName()+" "
				+ "check: "+this.isCheck()+" "
				+"}";
		
		return json;
	}   
}

