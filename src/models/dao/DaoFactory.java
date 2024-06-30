package models.dao;

import db.DB;
import models.dao.implementation.UserDaoJDBC;

public class DaoFactory {
	
	public static UserDao createUserDao() {
		return new UserDaoJDBC(DB.getConnection());
	}

}
 