package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import entity.User;
import exception.NoSuchRowException;
import manager.UserManager;

@WebServlet("/profil.html")
public class Profil extends HttpServlet{
	private static final long serialVersionUID = 2L;
	public Profil() {
		super();
	}
	
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		final UserManager manager = new UserManager("YummyYogurt");
		final ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();

		try {
			final int index = Integer.parseInt(request.getParameter("id"));
			final User user = manager.findByID(index);
			final String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
			System.out.println(json);
			
			
			response.setContentType("application/json");
			response.getWriter().append(json);
		} catch (NumberFormatException | NoSuchRowException e) {
			System.out.println(e.getMessage());
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		
		manager.close();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
