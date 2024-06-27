package models.dao;

import models.dao.implementation.UserDaoJDBC;

public class DaoFactory {
	
	public static UserDao createUserDao() {
		return new UserDaoJDBC();
	}

}
 