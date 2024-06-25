package main;

import model.entities.User;

public class Application {

	public static void main(String[] args) {
		
		User user = new User(1, "Mateus");
		System.out.println(user);

	}

}
