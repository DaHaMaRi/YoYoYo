package client;

import java.time.LocalDate;
import java.util.List;

import entity.Address;
import entity.User;
import exception.NoSuchRowException;
import manager.AddressManager;
import manager.UserManager;

public class Test {

	public static void main(String[] args) {
		UserManager userManager = new UserManager("YummyYogurt");
		
		try {
			User user = userManager.findByID(1);
			System.out.println(user.getAccessiondate());
		} catch (NoSuchRowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
