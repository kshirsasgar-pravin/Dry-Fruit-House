package com.DryFruitHouse.repository;

import java.util.List;

import com.DryFruitHouse.entity.User;
import com.DryFruitHouse.enums.UserRole;

public interface UserDAO {
	boolean saveUser(User user);

	boolean updateUser(User user);

	boolean deleteUser(Long userId);

	User getUserById(Long userId);

	User getUserByEmail(String email);

	User getUserByPhone(String phone);

	List<User> getAllUsers();

	List<User> getUsersByRole(UserRole role);

	User getUserByName(String userName);
	List<User> getActiveUser();
}
