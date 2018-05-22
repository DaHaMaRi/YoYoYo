package client;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import entity.Yogurt;
import exception.NoSuchRowException;
import manager.YogurtManager;

public class Client {

	public static void main(String args[]) {
		YogurtManager yogurtManager = new YogurtManager("YummyYogurt");
		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		
		try {
			Yogurt yogurt = yogurtManager.findByID(1);
			String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(yogurt);
			System.out.println(json);
			
			yogurt = mapper.readValue(json, Yogurt.class);
			System.out.println(yogurt);
			
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
		
		yogurtManager.close();
	}

}
