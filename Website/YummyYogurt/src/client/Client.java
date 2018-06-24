package client;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import entity.Category;
import entity.Yogurt;
import exception.NoSuchRowException;
import manager.CategoryManager;
import manager.YogurtManager;

public class Client {

	public static void main(String args[]) {
		CategoryManager categoryManager = new CategoryManager("YummyYogurt");
		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		
		try {
			Category category = categoryManager.findByID(1);
			System.out.println(category);
			
			String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(category);
			System.out.println(json);
		} catch (NoSuchRowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		categoryManager.close();
	}

}
