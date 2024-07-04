package main;

import java.util.List;

import models.dao.DaoFactory;
import models.dao.UserDao;
import models.entities.User;

public class Application {

	public static void main(String[] args) {
		
		System.out.println("********** find by id *********");
		UserDao userDao = DaoFactory.createUserDao();
		User user = userDao.findByID(3);
		
		System.out.println(user);
		System.out.println("********** find by department *********");
		List<User> usersByDepartment = userDao.findByDepartment(1);
		for (User u: usersByDepartment) {
			System.out.println(u);
		}
		System.out.println("********** find all *********");
		List<User> usersList = userDao.findAll();
		for (User u: usersList) {
			System.out.println(u);
		}

	}

}
