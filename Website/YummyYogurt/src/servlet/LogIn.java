package servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.User;
import exception.NoSuchRowException;
import manager.UserManager;

@WebServlet("/log-in.html")
public class LogIn  extends HttpServlet{

private static final long serialVersionUID = 5L;
	
	public LogIn() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = request.getServletContext();
		EntityManagerFactory factory = (EntityManagerFactory) context.getAttribute("factory");
		
		UserManager userManager = new UserManager(factory);
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
				@SuppressWarnings("unchecked")
				HashMap<Integer,Integer> einkauf = (HashMap<Integer, Integer>) session.getAttribute("Einkaufswagen");
				einkauf.put(1,1);
				einkauf.put(2,2);
				einkauf.put(3,1);
				session.setAttribute("Einkaufswagen", einkauf);
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
