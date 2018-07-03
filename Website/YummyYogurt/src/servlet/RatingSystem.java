package servlet;

import java.io.IOException;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manager.RatingManager;


@WebServlet("/RatingSystem")
public final class RatingSystem extends HttpServlet {
	
	private static final long serialVersionUID = -855441989241868835L;

	
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) 
			throws ServletException, IOException 
	{
		final ServletContext context = request.getServletContext();
		final EntityManagerFactory factory = (EntityManagerFactory) context.getAttribute("factory");
		
		final RatingManager ratingManager = new RatingManager(factory);
		
		
		
		
		ratingManager.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
