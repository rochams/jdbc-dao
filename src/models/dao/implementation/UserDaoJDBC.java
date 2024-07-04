package models.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import exception.DBException;
import models.dao.UserDao;
import models.entities.Role;
import models.entities.User;

public class UserDaoJDBC implements UserDao {
	
	private Connection conn;
	public UserDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void create(User user) {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			
			ps = conn.prepareStatement("""
				INSERT INTO user (name, roleId)
				VALUES (?, ?)
			""", Statement.RETURN_GENERATED_KEYS); // Statement.RETURN_GENERATED_KEYS returns the id of created object
			ps.setString(1, user.getName());
			ps.setInt(2, user.getRole().getId());
			rs = ps.executeQuery();
			
			int rowsGen = ps.executeUpdate();
			if (rowsGen > 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					user.setId(id);
				}
			}
			
		} catch(SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closePreparedStatement(ps);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public void update(User user) {
		
	}

	@Override
	public void deleteById(Integer id) {
		
	}

	@Override
	public User findByID(Integer id) {
		// creating query-instruction object and result object
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// constructing query
			ps = conn.prepareStatement("""
				select user.*, role.Name as roleName
				from user inner join role 
				on user.roleId=role.Id
				where user.id = ? ;
			""");
			// setting the parameter
			ps.setInt(1, id);
			rs = ps.executeQuery();
			
			if (rs.next()) {	// the zero position on object res have nothing, this is why we call rs.next() to get position 1
				Role role = new Role(
						rs.getInt("roleId"),
						rs.getString("roleName")
				);
				User user = new User(
						rs.getInt("id"),
						rs.getString("name"),
						role
				);
				return user;
			}
			return null;
			
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closePreparedStatement(ps);
			DB.closeResultSet(rs);
		}
		
	}

	@Override
	public List<User> findAll() {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<User> usersList = new ArrayList<>();
		
		try {
			ps = conn.prepareStatement("""
			select user.*, role.Name as roleName
			from user inner join role 
			on user.roleId=role.Id;		
			""");
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Role role = new Role(
						rs.getInt("roleID"), 
						rs.getString("roleName")
				);
				User user = new User(
						rs.getInt("id"), 
						rs.getString("name"), 
						role
				);
				usersList.add(user);
			}
			return usersList;
			
		} catch(SQLException e) {
			throw new DBException(e.getMessage());
		}
		
	}

	@Override // like findById - difference on the loop
	public List<User> findByDepartment(Integer roleId) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			List<User> usersByRole = new ArrayList<>();
			Map<Integer, Role> map = new HashMap<>();
			
			ps = conn.prepareStatement("""
				select user.*, role.Name as roleName
				from user inner join role
				on user.roleId=role.Id
				where roleId = ?;
			""");
			ps.setInt(1, roleId);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				
				Role role = map.get(rs.getInt("roleId"));
				
				if (role == null) { 
					// checking if the role object already exists to avoid creation of another role object and guarantee one role for several users
					role = new Role(rs.getInt("roleId"), rs.getString("roleName"));
					// save role as a default role with the purpose of set all users to an unique role object
					map.put(rs.getInt("roleId"), role);
				}
				User user = new User(rs.getInt("id"), rs.getString("name"), role);
				usersByRole.add(user);
			}
			return usersByRole;
			
			
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		
	}

}
