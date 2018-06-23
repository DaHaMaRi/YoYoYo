package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import entity.Ingredient;
import manager.IngredientManager;


@WebServlet("/Mixer/Zutaten")
public class MixerIngredients extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
    public MixerIngredients() {
        super();
    }


	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		final IngredientManager manager = new IngredientManager("YummyYogurt");
		final ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		
		final List<Ingredient> ingredients = manager.listAll();
		final String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ingredients);
		System.out.println(json);
		
		response.setContentType("application/json");
		response.getWriter().append(json);
		
		manager.close();
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
