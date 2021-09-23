package model;

public class Task{
	private String text;
	private boolean check;
	private int id;
	
	public Task(String text, boolean check, int id) {
		super();
		this.text = text;
		this.check = check;
		this.id = id;
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
	
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		String json = "{"
				+ "name: "+this.getText()+" "
				+ "check: "+this.isCheck()+" "
				+ "id: "+this.getId()
				+"}";
		
		return json;
	}
}

