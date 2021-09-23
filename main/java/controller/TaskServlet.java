package controller;

import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import model.Task;
import model.User;
import model.repository.TaskDAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/Tasks")
public class TaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {		
		System.out.println("-----------------------");
		//Get the session Object
		HttpSession session = request.getSession();
		
		Object object = session.getAttribute("user");
		
		
		if(object != null) {
			if (object instanceof User) {
				
				/* REQUEST */

				// Cast User
				User user = (User) object;
				
				// Encapsulate Task
				String json = getJsonFromRequest(request);
				Task task = deserializeTask(json);
				
				
				if(task !=null) {
					System.out.println(task.getId()+task.getText()+task.isCheck());
					// INSERT || UPDATE
					TaskDAO.updateTask(task, user);
				
					// DELETE
					String content = request.getHeader("content-operation");
					if(content.equals("trash")) {
						TaskDAO.deleteTask(task,user);
					}
				}
				
				
				/* RESPONSE */
				
				// Send All Task Objects 
				ArrayList<Task> tasks = TaskDAO.queryAll(user);
				ArrayList<String> gsonArray = serializeTasks(tasks);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(gsonArray.toString());
				
			} else
				System.err.println("Object not User");
		} else
			System.err.println("No Object sent");
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}
	
	/*
	 * Serialize to JSON
	 * */
	private ArrayList<String> serializeTasks(ArrayList<Task> tasks) {
		ArrayList<String> gsonArray = new ArrayList<String>();

		for (Task taskItem : tasks) {
			gsonArray.add(new Gson().toJson(taskItem));
		}
		return gsonArray;
	}
	
	/*
	 * DeSerialize from JSON
	 * */
	private Task deserializeTask(String json) {
		Gson gson = new Gson();
		Task task = gson.fromJson(json, Task.class);
		return task;
	}
	
	/*
	 * Read RequestBuffer for JSON String
	 * */
	private String getJsonFromRequest(HttpServletRequest request) throws IOException {
		// Read Buffer
		BufferedReader buffer = request.getReader();
		
		// Save json
		String json = buffer.readLine();
		
		// Debug
		System.out.println(this.getClass()+" JSON :"+json);
		
		// Close buffer
		buffer.close();

		return json;
	}

}
