package controller;

import com.google.gson.Gson;
import model.Task;
import model.repository.TaskDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Servlet implementation class TaskServlet
 */
@WebServlet("/Tasks")
public class TaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public TaskServlet() {

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// REQUEST One Task
		String json = getJsonFromRequest(request);
		Task task = deserializeTask(json);
		TaskDAO.updateDBTasks(task);
		
		// RESPONSE All updated Tasks
		ArrayList<Task> tasks = TaskDAO.queryDBTasks();
		ArrayList<String> gsonArray = serializeTasks(tasks);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(gsonArray.toString());
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}

	private ArrayList<String> serializeTasks(ArrayList<Task> tasks) {
		ArrayList<String> gsonArray = new ArrayList<String>();

		for (Task taskItem : tasks) {
			gsonArray.add(new Gson().toJson(taskItem));
		}
		return gsonArray;
	}

	private Task deserializeTask(String json) {
		Gson gson = new Gson();
		Task task = gson.fromJson(json, Task.class);
		return task;
	}

	private String getJsonFromRequest(HttpServletRequest request) throws IOException {
		BufferedReader buffer = request.getReader();
		String json = buffer.readLine();
		buffer.close();

		return json;
	}

}
