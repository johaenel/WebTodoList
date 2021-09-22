package controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.repository.UserDAO;

import java.io.IOException;


@WebServlet("/Registration")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public RegistrationServlet() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean result = UserDAO.signIn(request.getParameter("firstName"), 
				request.getParameter("lastName"),
				request.getParameter("userName"),
				request.getParameter("password"));
		String message;
		String domClass;
		
		if(result) {
			 message = "Felicitări! Te-ai înregistrat cu succes!";
			 domClass = "alert alert-info";
		}else {
			 message = "Regretăm, dar a apărut o eroare!";
			 domClass = "alert alert-danger";
		}
		request.setAttribute("message", message);
		request.setAttribute("domclass", domClass);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(Dispatcher.CONFIRMATION);
        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
