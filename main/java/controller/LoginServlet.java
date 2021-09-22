package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.User;
import model.repository.UserDAO;

/**
 * Servlet implementation class AuthenticationServlet
 */
@WebServlet("/Authentication")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public LoginServlet() {
		super();
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse 
			response)
			throws ServletException, IOException {
		
		// Get Credentials from request
		String user_name = request.getParameter("userName");
		String password = request.getParameter("password");
		
		// Encapsulate Data from Database
		User user = UserDAO.logIn(user_name, password);
		
		// Create Process Data Object
		HttpRequestResponseProccesor proccesor = 
				new HttpRequestResponseProccesor(request, response);
		
		// Save Data Process Data to Session
		// Route Result
		if (user != null) {
			proccesor.saveObjToSession("user", user);
			proccesor.routeToDispatcher(Dispatcher.DEFAULT);
		} else {
			String message = "Regretăm, însă datele de autentificare nu "
					+ "sunt valide!";
			String domclass = "alert alert-danger";
			request.setAttribute("message", message);
			request.setAttribute("domclass", domclass);
			proccesor.routeToDispatcher(Dispatcher.AUTHENTICATION);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, 
	 * HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
/*
 * fiecare metodă are o responsabilitate!!!
 * este independentă de context */

class HttpRequestResponseProccesor {
	private HttpServletRequest request;
	private HttpServletResponse response;
	HttpSession session;
	
	public HttpRequestResponseProccesor(HttpServletRequest request, 
			HttpServletResponse response) {
		super();
		this.request = request;
		this.response = response;
	}

	public void saveObjToSession(String objKey, Object obj) {
		
		//validate input
		//key not empty
		// check session not null
		
		session = request.getSession();
		session.setAttribute(objKey, obj);
	}
	
	
	
	public void routeToDispatcher(String route) throws ServletException, 
	IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(route);
		dispatcher.forward(request, response);
	}
}
