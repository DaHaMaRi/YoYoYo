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
public class ProductInfo extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ProductInfo() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int index = Integer.parseInt(request.getParameter("id"));
		
		YogurtManager manager = new YogurtManager("YummyYogurt");
		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();

		try {
			Yogurt yogurt = manager.findByID(index);
			String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(yogurt);
			System.out.println(json);
			
			
			response.setContentType("application/json");
			response.getWriter().append(json);
		} catch (NoSuchRowException e) {
			System.out.println(e.getMessage());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
