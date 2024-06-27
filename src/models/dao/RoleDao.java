package models.dao;

import java.util.List;

import model.entities.Role;

public interface RoleDao {
	
	void insert(Role role);
	void update(Role role);
	void deleteById(Integer id);
	Role findById(Integer id);
	List<Role> findAll();

}
