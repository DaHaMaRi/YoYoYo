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
import javax.servlet.http.HttpSession;

import entity.Ingredient;
import entity.User;
import entity.Yogurt;
import exception.NoSuchRowException;
import manager.IngredientManager;
import manager.UserManager;
import manager.YogurtManager;


@WebServlet("/Mixer")
public final class Mixer extends HttpServlet {
	
	private static final long serialVersionUID = -1033141283317370043L;

	
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) 
			throws ServletException, IOException 
	{
		response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
	}

	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) 
			throws ServletException, IOException 
	{
		final String payload = request.getReader().readLine();
		final ServletContext context = request.getServletContext();
		final EntityManagerFactory factory = (EntityManagerFactory) context.getAttribute("factory");
		
		final UserManager userManager = new UserManager(factory);
		final YogurtManager yogurtManager = new YogurtManager(factory);
		final IngredientManager ingredientManager = new IngredientManager(factory);
		
		try {
			HttpSession session = request.getSession();
			if(!session.isNew()) {
				int index = (Integer) session.getAttribute("UID");
				final User owner = userManager.findByID(index);
				final String yogurtname = this.parseYogurtname(payload);
			
				final Yogurt yogurt = new Yogurt(yogurtname, owner, true);
				System.out.println(payload);
			
				final List<Integer> nameOfIngredients = this.parseIngredientnames(payload);
				for(Integer ingredientname : nameOfIngredients) {
					Ingredient ingredient = ingredientManager.findByID(ingredientname);
					yogurt.addToRecipe(ingredient);
				}
			
				yogurtManager.save(yogurt);
			
				response.setStatus(HttpServletResponse.SC_OK);
				response.setContentType("application/json");
				response.getWriter().append("{\"id\": \"" + yogurt.getID() + "\"}");
			}else {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				session.invalidate();
			}
			
		} catch (NoSuchRowException e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			System.out.println(e.getMessage());
		} finally {
			userManager.close();
			yogurtManager.close();
			ingredientManager.close();
		}
	}
	
	
	private String parseYogurtname(final String payload) 
	{
		final String yogurtname;
		String[] formValues = payload.split("&");
		String[] values = formValues[0].split("=");
		
		if(values[1].contains("+"))
			values[1] = values[1].replace("+", " ");
		
		yogurtname = values[1];
		return yogurtname;
	}
	
	private List<Integer> parseIngredientnames(final String payload) 
	{
		final List<Integer> nameOfIngredients = new ArrayList<>();
		
		String[] formValues = payload.split("&");
		for(int i = 1; i < formValues.length; i++) {
			String[] values = formValues[i].split("=");
			
			
			nameOfIngredients.add(Integer.parseInt(values[0]));
		}
		
		return nameOfIngredients;
	}

}
