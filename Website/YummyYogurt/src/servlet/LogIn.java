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

@WebServlet("/log-in.html")
public class LogIn  extends HttpServlet{

private static final long serialVersionUID = 5L;
	
	public LogIn() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserManager userManager = new UserManager("YummyYogurt");
		//System.out.println("doGet test");

		try {
			
			String username = request.getParameter("Username");
			String passwort = request.getParameter("passwort");
			
			User user = userManager.findByUsername(username);
			System.out.println("user id: "+user.getID());
			
			if (passwort.equals(user.getPassword())) {
				HttpSession session = request.getSession();
				session.setAttribute("UID", user.getID());
				session.setAttribute("Einkaufswagen", new HashMap<Integer,Integer>());
			}else{
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			}
			
			response.setContentType("text/html");
			response.getWriter().append("");
		} catch (NumberFormatException | NoSuchRowException  e) {
			System.out.println(e.getMessage());
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			//response.setStatus(HttpServletResponse.SC_OK);
			userManager.close();
		}
		
		userManager.close();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}
