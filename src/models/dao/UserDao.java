package models.dao;

import java.util.List;

import model.entities.User;

public interface UserDao {
	
	void insert(User user);
	void update(User user);
	void deleteById(Integer id);
	User findByID(Integer id);
	List<User> findAll();

}
