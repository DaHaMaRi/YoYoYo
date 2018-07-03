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

import entity.Yogurt;
import exception.NoSuchRowException;
import manager.YogurtManager;


@WebServlet("/shopping-cart.html")
public final class ShoppingCart extends HttpServlet {
	
	private static final long serialVersionUID = 4151464703212835757L;


	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) 
			throws ServletException, IOException 
	{
		final ServletContext context = request.getServletContext();
		final EntityManagerFactory factory = (EntityManagerFactory) context.getAttribute("factory");
		
		final YogurtManager yogurtManager = new YogurtManager(factory);
		
		final ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();

		try {
			final HttpSession session = request.getSession();
			if (!session.isNew()) {
				String yidS =  request.getParameter("id");
				String mS = request.getParameter("m");

				HashMap<Integer,Integer> einkaufSession = (HashMap<Integer, Integer>) session.getAttribute("Einkaufswagen");
				if(yidS != null && mS != null) {
					Integer yid = Integer.parseInt(yidS);
					Integer m = Integer.parseInt(mS);
					einkaufSession.put(yid, m);
				}
				session.setAttribute("Einkaufswagen", einkaufSession);
				final List<Yogurt>  yogurts = new ArrayList<Yogurt>();
				final List<Integer> mengen = new ArrayList<Integer>();
				
				for(Integer i : einkaufSession.keySet()) {
					yogurts.add(yogurtManager.findByID(i));
					mengen.add(einkaufSession.get(i));
				}
				final String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(yogurts);
				final String json2 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mengen);
				String message = "[ " +json+ " , "+json2 +"]";
				
				response.setContentType("application/json");
				response.getWriter().append(message);
			}else{
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			}
			
		} catch (NumberFormatException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			System.out.println(e.getMessage());
		} catch (NoSuchRowException  e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			System.out.println(e.getMessage());
		} finally {
			yogurtManager.close();
		}
	}
	
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) 
			throws ServletException, IOException 
	{
		doGet(request, response);
	}
	
}
