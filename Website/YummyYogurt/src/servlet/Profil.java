package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import entity.Category;
import entity.User;
import entity.Yogurt;
import exception.NoSuchRowException;
import manager.UserManager;
import manager.YogurtManager;

@WebServlet("/profil.html")
public class Profil extends HttpServlet{
	private static final long serialVersionUID = 2L;
	public Profil() {
		super();
	}
	
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		final UserManager userManager = new UserManager("YummyYogurt");
		final YogurtManager yogurtManager = new YogurtManager("YummyYogurt");
		final ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();

		try {
			HttpSession session = request.getSession();
			if(!session.isNew()) {
				final int index = (int) session.getAttribute("UID");
				final User user = userManager.findByID(index);
				final String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
				final List<Yogurt>  yogurts = userManager.getAllYogurts(index);
				final String json2 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(yogurts);
				String message = "[ " +json+ " , "+json2 +"]";
				System.out.println(message);
				response.setContentType("application/json");
				response.getWriter().append(message);
				
				
			}else {
				response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
			}
			
		} catch (NumberFormatException | NoSuchRowException e) {
			System.out.println(e.getMessage());
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			userManager.close();
		}
		
		userManager.close();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final YogurtManager yogurtManager = new YogurtManager("YummyYogurt");
		
		try {
			final int index = Integer.parseInt(request.getParameter("YID"));
			final boolean state = Boolean.parseBoolean(request.getParameter("checked"));
			final Yogurt yogurt = yogurtManager.findByID(index);
			System.out.println(yogurt.getName()+" is "+state);
			yogurt.setVisibility(state);
			
			
			response.setContentType("text/html");
			response.getWriter().append(" ");
		} catch (NumberFormatException | NoSuchRowException e) {
			System.out.println(e.getMessage());
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			yogurtManager.close();
		}
		
		yogurtManager.close();
	}
}
