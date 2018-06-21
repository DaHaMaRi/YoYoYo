package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Address;
import entity.User;
import exception.NoSuchRowException;
import manager.AddressManager;
import manager.UserManager;

@WebServlet("/changeAddress.html")
public class ChangeAddress extends HttpServlet{

	private static final long serialVersionUID = 3L;
	
	public ChangeAddress(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AddressManager addressManager = new AddressManager("YummyYogurt");
		UserManager userManager = new UserManager("YummyYogurt");
		System.out.println("doGet test");

		try {
			int UID = Integer.parseInt(request.getParameter("id"));
			String street_name = request.getParameter("street_name");
			String housenumber = request.getParameter("housenumber");
			String city = request.getParameter("city");
			String postalcode = request.getParameter("postalcode");
			User user = userManager.findByID(UID);
			int AID = user.getAddress().getID();
			Address address = new Address(AID,street_name,housenumber," ",postalcode,city);
			System.out.println("test: "+address.toString());
			addressManager.save(address);
			
			
			
			response.setContentType("application/json");
			//response.getWriter().append(json);
		} catch (NumberFormatException | NoSuchRowException e) {
			System.out.println(e.getMessage());
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		
		addressManager.close();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}
