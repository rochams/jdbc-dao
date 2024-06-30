package models.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
		
	}

	@Override
	public void update(User user) {
		
	}

	@Override
	public void deleteById(Integer id) {
		
	}

	@Override
	public User findByID(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("""
				select user.*, role.Name as roleName
				from user inner join role 
				on user.roleId=role.Id
				where user.id = ? ;
			""");
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
		return null;
	}

}
