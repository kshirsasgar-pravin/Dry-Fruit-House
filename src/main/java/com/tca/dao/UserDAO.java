package com.tca.dao;

import java.util.List;

import com.tca.entity.User;
import com.tca.enums.UserRole;

public interface UserDAO {
	boolean saveUser(User user);

	boolean updateUser(User user);

	boolean deleteUser(Long userId);

	User getUserById(Long userId);

	User getUserByEmail(String email);

	User getUserByPhone(String phone);

	List<User> getAllUsers();

	List<User> getUsersByRole(UserRole role);
}
