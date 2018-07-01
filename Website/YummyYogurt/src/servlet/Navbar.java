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

import com.fasterxml.jackson.databind.ObjectMapper;

import entity.User;
import exception.NoSuchRowException;
import manager.UserManager;

@WebServlet("/navbar")
public class Navbar extends HttpServlet{

	private static final long serialVersionUID = -1746924272251977141L;

	public Navbar() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = request.getServletContext();
		EntityManagerFactory factory = (EntityManagerFactory) context.getAttribute("factory");
		
		UserManager userManager = new UserManager(factory);

		final ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		
		try {
			HttpSession session = request.getSession();
			String message = "";
			int index = -999;
			
			if(!session.isNew()) {
				index = (int) session.getAttribute("UID");
				System.out.println("id1 ="+index);
				if(index!=-999) {
					final User user = userManager.findByID(index);
					System.out.println("id2 ="+index);
					final String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
					message = "["+"[1],"+json+"]";
				}
				
				
			}else{
				message = "["+"[0],"+"[]"+"]";
				session.invalidate();
				System.out.println("default");
			}
			
			response.setContentType("application/json");
			response.getWriter().append(message);
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
