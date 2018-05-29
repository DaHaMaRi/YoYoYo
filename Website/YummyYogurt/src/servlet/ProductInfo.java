package servlet;

import java.io.IOException;
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

	private static final long serialVersionUID = 1L;

	
	public ProductInfo() {
		super();
	}

	
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		final YogurtManager manager = new YogurtManager("YummyYogurt");
		final ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();

		try {
			final int index = Integer.parseInt(request.getParameter("id"));
			final Yogurt yogurt = manager.findByID(index);
			final String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(yogurt);
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
