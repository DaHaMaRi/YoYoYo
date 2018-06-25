package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import entity.Address;
import entity.User;
import entity.Yogurt;
import exception.NoSuchRowException;
import manager.AddressManager;
import manager.UserManager;
import manager.YogurtManager;

@WebServlet("/shopping-cart.html")
public class ShoppingCart extends HttpServlet{

	public ShoppingCart() {
		super();
	}
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		YogurtManager yogurtManager = new YogurtManager("YummyYogurt");
		final ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();

		try {
			System.out.println("Test1");
			HttpSession session = request.getSession();
			if (!session.isNew()) {
				System.out.println("Test2");
				Integer yid =  Integer.parseInt(request.getParameter("yid"));
				Integer m = Integer.parseInt(request.getParameter("m"));
				System.out.println("yid "+yid);
				System.out.println("m "+m);
				
				@SuppressWarnings("unchecked")
				HashMap<Integer,Integer> einkaufSession = (HashMap<Integer, Integer>) session.getAttribute("Einkaufswagen");
				if(yid!=-999 && m != -999) {
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
		} catch (NumberFormatException | NoSuchRowException  e) {
			System.out.println(e.getMessage());
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			//response.setStatus(HttpServletResponse.SC_OK);
			yogurtManager.close();
		}
		
		yogurtManager.close();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}
