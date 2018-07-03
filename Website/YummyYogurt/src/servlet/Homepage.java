package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import entity.Rating;
import entity.Yogurt;
import manager.RatingManager;
import manager.YogurtManager;


@WebServlet("/Homepage")
public final class Homepage extends HttpServlet {

	private static final long serialVersionUID = 2168314929320779824L;

	
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) 
			throws ServletException, IOException 
	{
		final ServletContext context = request.getServletContext();
		final EntityManagerFactory factory = (EntityManagerFactory) context.getAttribute("factory");
		
		final YogurtManager yogurtManager = new YogurtManager(factory);
		final RatingManager ratingManager = new RatingManager(factory);
		
		final ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		
		List<Yogurt> temp = yogurtManager.listBestYogurts();
		
		List<Yogurt> bestYogurts = new ArrayList<>();
		List<Double> averageRating = new ArrayList<>();
		
		for(int i = 0; i < 20; i++) {
			Yogurt yogurt = temp.get(i);
			bestYogurts.add(yogurt);
			
			double average = 0;
			
			List<Rating> ratings = ratingManager.findByYogurt(yogurt.getName());
			for(Rating rating : ratings) {
				average += rating.getRating();
			}
			average /= ratings.size();
			averageRating.add(average);
		}
		
		String yogurts = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(bestYogurts);
		String ratings = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(averageRating);
		
		String message = "[" + yogurts + ", " + ratings + "]";
		
		response.setContentType("application/json");
		response.getWriter().append(message);

		yogurtManager.close();
		ratingManager.close();
	}

	
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) 
			throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
