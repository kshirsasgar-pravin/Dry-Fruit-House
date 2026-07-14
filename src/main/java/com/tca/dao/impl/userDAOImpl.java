package com.tca.dao.impl;

import java.util.List;

import com.tca.dao.UserDAO;
import com.tca.entity.User;
import com.tca.enums.UserRole;

public class userDAOImpl implements UserDAO {

	@Override
	public boolean saveUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUser(Long userId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User getUserById(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserByPhone(String phone) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getUsersByRole(UserRole role) {
		// TODO Auto-generated method stub
		return null;
	}

}
