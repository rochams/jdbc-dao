package models.dao;

import java.util.List;

import models.entities.User;

public interface UserDao {
	
	void create(User user);
	void update(User user);
	void deleteById(Integer id);
	User findByID(Integer id);
	List<User> findAll();

}
