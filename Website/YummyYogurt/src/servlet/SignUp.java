package servlet;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Address;
import entity.User;
import exception.NoSuchRowException;
import manager.AddressManager;
import manager.UserManager;

@WebServlet("/sign-up.html")
public class SignUp extends HttpServlet{

	private static final long serialVersionUID = 4L;
	
	public SignUp() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserManager userManager = new UserManager("YummyYogurt");
		System.out.println("doGet test");

		try {
			String vorname = request.getParameter("Vorname");
			String nachname = request.getParameter("Nachname");
			String username = request.getParameter("Username");
			LocalDate birthday = LocalDate.parse(request.getParameter("birthday"));
			String eMail = request.getParameter("EMail");
			String strasse = request.getParameter("strasse");
			String hausnummer = request.getParameter("hausnummer");
			String plz = request.getParameter("plz");
			String ort = request.getParameter("ort");
			String passwort = request.getParameter("passwort");
			System.out.println("Test3: "+ birthday.toString());
			
			LocalDate accessiondate = LocalDate.now();
		
			Address address = new Address(strasse,hausnummer," ",plz,ort);
			System.out.println("address id: "+address.getID());
			
			User user = new User(vorname,nachname,username,eMail,birthday,address,accessiondate,passwort);
			System.out.println("user id: "+user.getID());
			userManager.save(user);
			
			
			response.setContentType("text/html");
			response.getWriter().append("");
		} catch (NumberFormatException  e) {
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
