package client;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import entity.Yogurt;
import exception.NoSuchRowException;
import manager.YogurtManager;

public class Client {

	public static void main(String args[]) {

//		ObjectMapper mapper = new ObjectMapper();
//		String jsonString = "{\"name\":\"Mahesh\", \"age\":21}";
//		
//		// map json to student
//		try {
//			Student student = mapper.readValue(jsonString, Student.class);
//
//			System.out.println(student);
//
//			jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(student);
//
//			System.out.println(jsonString);
//		} catch (JsonParseException e) {
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		YogurtManager yogurtManager = new YogurtManager("YummyYogurt");
		ObjectMapper mapper = new ObjectMapper();
		
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

class Student {
	private String name;
	private int age;

	public Student() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String toString() {
		return "Student [ name: " + name + ", age: " + age + " ]";
	}
}
