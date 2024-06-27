package main;

import model.entities.Role;
import model.entities.User;

public class Application {

	public static void main(String[] args) {
		
		Role role = new Role(1, "common");
		User user = new User(1, "Mateus", role);
		System.out.println(user);

	}

}
