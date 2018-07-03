package servlet;

import java.io.IOException;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import entity.Address;
import entity.User;
import exception.NoSuchRowException;
import manager.UserManager;

@WebServlet("/ProfileSettings")
public final class ProfileSettings extends HttpServlet {
	
	private static final long serialVersionUID = 8475537074668492154L;

	
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) 
			throws ServletException, IOException 
	{
		ServletContext context = request.getServletContext();
		EntityManagerFactory factory = (EntityManagerFactory) context.getAttribute("factory");
		
		UserManager userManager = new UserManager(factory);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		
		HttpSession session = request.getSession();
		if(!session.isNew()) {
			int userID = (Integer) session.getAttribute("UID");
			try {
				User user = userManager.findByID(userID);
				String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
				
				response.setContentType("application/json");
				response.getWriter().append(json);
				
			} catch (NoSuchRowException e) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				System.out.println(e.getMessage());
			}
		} else {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
		
		userManager.close();
	}

	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) 
			throws ServletException, IOException 
	{
		ServletContext context = request.getServletContext();
		EntityManagerFactory factory = (EntityManagerFactory) context.getAttribute("factory");
		
		UserManager userManager = new UserManager(factory);
		
		HttpSession session = request.getSession();
		if(!session.isNew()) {
			int userID = (Integer) session.getAttribute("UID");
			try {
				User user = userManager.findByID(userID);
				
				String firstname = request.getParameter("firstname");
				String familyname = request.getParameter("familyname");
				String username = request.getParameter("username");
				String email = request.getParameter("email");
				
				String streetname = request.getParameter("streetname");
				String streetnumber = request.getParameter("streetnumber");
				String additional = request.getParameter("additional");
				String postalcode = request.getParameter("postalcode");
				String city = request.getParameter("city");
				
				Address oldAddress = user.getAddress();
				Address newAddress = new Address(oldAddress.getID(), streetname, streetnumber, additional, postalcode, city);
				
				user.setFirstname(firstname);
				user.setFamilyname(familyname);
				user.setUsername(username);
				user.setEmail(email);
				user.setAddress(newAddress);
				
				userManager.save(user);
				
			} catch (NoSuchRowException e) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				System.out.println(e.getMessage());
			}
		} else {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
		
		userManager.close();
	}

}
