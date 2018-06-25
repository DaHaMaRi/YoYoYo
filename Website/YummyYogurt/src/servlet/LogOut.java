package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.Address;
import entity.User;
import exception.NoSuchRowException;
import manager.AddressManager;
import manager.UserManager;

@WebServlet("/logOut")
public class LogOut extends HttpServlet{

	private static final long serialVersionUID = 6L;
	
	public LogOut() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			HttpSession session = request.getSession();
			session.invalidate();
			response.setContentType("text/html");
			response.getWriter().append("");
			System.out.println("Ausgeloggt");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}
