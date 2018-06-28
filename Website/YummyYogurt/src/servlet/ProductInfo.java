package servlet;

import java.io.IOException;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import entity.Yogurt;
import exception.NoSuchRowException;
import manager.YogurtManager;

@WebServlet("/product-info.html")
public final class ProductInfo extends HttpServlet {

	private static final long serialVersionUID = 311248899817719950L;

	
	public ProductInfo() {
		super();
	}

	
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		final ServletContext context = request.getServletContext();
		final EntityManagerFactory factory = (EntityManagerFactory) context.getAttribute("factory");
		
		final YogurtManager manager = new YogurtManager(factory);
		
		final ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();

		try {
			final int index = Integer.parseInt(request.getParameter("id"));
			final Yogurt yogurt = manager.findByID(index);
			final String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(yogurt);
			
			response.setContentType("application/json");
			response.getWriter().append(json);
			
		} catch (NumberFormatException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			System.out.println(e.getMessage());
		} catch (NoSuchRowException e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			System.out.println(e.getMessage());
		} finally {
			manager.close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
