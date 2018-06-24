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
		AddressManager addressManager = new AddressManager("YummyYogurt");
		UserManager userManager = new UserManager("YummyYogurt");
		System.out.println("doGet test");

		//try {
			//int UID = Integer.parseInt(request.getParameter("id"));
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
			//Address address = new Address(AID,strasse,hausnummer," ",plz,ort);
			LocalDate accessiondate = LocalDate.now();
			//User user = new User(UID,vorname,nachname,username,eMail,birthday,address,accessiondate);
			
			
			
			
			response.setContentType("application/json");
			response.getWriter().append(" ");
		//} catch (NumberFormatException | NoSuchRowException e) {
			//System.out.println(e.getMessage());
			//response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			//response.setStatus(HttpServletResponse.SC_OK);
			//addressManager.close();
			//userManager.close();
		//}
		
		addressManager.close();
		userManager.close();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}
