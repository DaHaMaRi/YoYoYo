package servlet;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.Order;
import entity.OrderItem;
import exception.NoSuchRowException;
import manager.OrderItemManager;
import manager.OrderManager;
import manager.UserManager;
import manager.YogurtManager;

@WebServlet("/makeOrder.html")
public class MakeOrder extends HttpServlet{
	private static final long serialVersionUID = 4543554575221439475L;

	public MakeOrder(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = request.getServletContext();
		EntityManagerFactory factory = (EntityManagerFactory) context.getAttribute("factory");
		
		OrderManager orderManager = new OrderManager(factory);
		YogurtManager yogurtManager = new YogurtManager(factory);
		UserManager userManager = new UserManager(factory);
		OrderItemManager orderItemManager = new OrderItemManager(factory);
		
		try {
			HttpSession session = request.getSession();
			if (!session.isNew()) {
				int uid =  (int) session.getAttribute("UID");
				
				int price = Integer.parseInt(request.getParameter("price"));
				@SuppressWarnings("unchecked")
				HashMap<Integer,Integer> einkaufSession = (HashMap<Integer, Integer>) session.getAttribute("Einkaufswagen");

				LocalDateTime orderdate = LocalDateTime.now();
				Order order = new Order(userManager.findByID(uid),price,orderdate);
				for(Integer i : einkaufSession.keySet()) {
					OrderItem test = new OrderItem(order, yogurtManager.findByID(i), einkaufSession.get(i));
					orderItemManager.save(test);
				}
				
				
				session.setAttribute("Einkaufswagen", new HashMap<Integer,Integer>());
			}else{
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			}
		} catch (NumberFormatException | NoSuchRowException  e) {
			System.out.println(e.getMessage());
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			//response.setStatus(HttpServletResponse.SC_OK);
			orderManager.close();
			yogurtManager.close();
			userManager.close();
			orderItemManager.close();
		}
		
		orderManager.close();
		yogurtManager.close();
		userManager.close();
		orderItemManager.close();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}
