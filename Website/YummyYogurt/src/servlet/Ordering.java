package servlet;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import entity.Yogurt;
import exception.NoSuchRowException;
import manager.UserManager;
import manager.YogurtManager;

@WebServlet("/ordering.html")
public class Ordering extends HttpServlet{


	private static final long serialVersionUID = 4L;
	
	public Ordering(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final ServletContext context = request.getServletContext();
		final EntityManagerFactory factory = (EntityManagerFactory) context.getAttribute("factory");
		
		final UserManager manager = new UserManager(factory);
		final YogurtManager yogurtManager = new YogurtManager(factory);
		
		final ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();

		try {
			final int index = Integer.parseInt(request.getParameter("id"));
			final User user = manager.findByID(index);
			final String jsonA = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
			HttpSession session = request.getSession();
			if (!session.isNew()) {
				
				@SuppressWarnings("unchecked")
				HashMap<Integer,Integer> einkaufSession = (HashMap<Integer, Integer>) session.getAttribute("Einkaufswagen");
				final List<Yogurt>  yogurts = new ArrayList<Yogurt>();
				final List<Integer> mengen = new ArrayList<Integer>();
				
				for(Integer i : einkaufSession.keySet()) {
					yogurts.add(yogurtManager.findByID(i));
					mengen.add(einkaufSession.get(i));
				}
				final String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(yogurts);
				final String json2 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mengen);
				String message = "[ " +json+ " , "+json2 +"]";
				message = "[ " +jsonA+ " , "+message+"]";
				response.setContentType("application/json");
				response.getWriter().append(message);
			}
		} catch (NumberFormatException | NoSuchRowException e) {
			System.out.println(e.getMessage());
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			manager.close();
			yogurtManager.close();
		}
		
		manager.close();
		yogurtManager.close();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}
