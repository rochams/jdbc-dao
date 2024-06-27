package main;

import models.dao.DaoFactory;
import models.dao.UserDao;
import models.entities.Role;
import models.entities.User;

public class Application {

	public static void main(String[] args) {
		
		UserDao userDao = DaoFactory.createUserDao();

	}

}
