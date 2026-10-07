package com.DryFruitHouse.test;

import java.util.List;

import com.DryFruitHouse.repository.UserDAO;
import com.DryFruitHouse.repository.impl.UserDAOImpl;
import com.DryFruitHouse.entity.User;
import com.DryFruitHouse.enums.AuthProvider;
import com.DryFruitHouse.enums.UserRole;

public class UserDAOTest {

	private UserDAO userDAO = new UserDAOImpl();

	// Used to store the latest inserted user id
	private Long userId;

	// ========================= SAVE =========================

	public void saveUserTest() {



		User user1 = new User();
		user1.setEmail("test@gmail.com");
		user1.setPassword("test@123");
		user1.setPhone("9404331578");
		user1.setUserRole(UserRole.ADMIN);
		user1.setAuthProvider(AuthProvider.LOCAL);
		user1.setIsActive(true);
		user1.setIsVerified(true);

		
		User user2 = new User();
		user2.setEmail("admin@gmail.com");
		user2.setPassword("admin@123");
		user2.setUserRole(UserRole.ADMIN);
		user2.setIsVerified(true);
		user2.setIsActive(true);
		user2.setAuthProvider(AuthProvider.LOCAL);
		user2.setPhone("9404331578");


		boolean result = userDAO.saveUser(user1);


		if (result) {
			userId = user1.getUserId();

			System.out.println("------------------------------------");
			System.out.println("User Saved Successfully");
			System.out.println("Generated User Id : " + userId);
			System.out.println("------------------------------------");
		} else {
			System.out.println("Failed to Save User");
		}
		result = userDAO.saveUser(user2);
		if (result) {
			userId = user2.getUserId();

			System.out.println("------------------------------------");
			System.out.println("User Saved Successfully");
			System.out.println("Generated User Id : " + userId);
			System.out.println("------------------------------------");
		} else {
			System.out.println("Failed to Save User");
		}
	}

	// ========================= UPDATE =========================

	public void updateUserTest(Long userId) {

		User user = userDAO.getUserById(userId);

		if (user == null) {
			System.out.println("User Not Found.");
			return;
		}

		user.setEmail("customer@gmail.com");
		user.setPassword("customer@123");
		user.setPhone("7499784512");
		user.setUserRole(UserRole.CUSTOMER);
		user.setAuthProvider(AuthProvider.GOOGLE);
		user.setProviderUserId("GOOGLE123");
		user.setIsActive(true);
		user.setIsVerified(true);

		if (userDAO.updateUser(user)) {
			System.out.println("User Updated Successfully");
		} else {
			System.out.println("Failed to Update User");
		}
	}

	// ========================= GET BY ID =========================

	public void getUserByIdTest(Long userId) {

		User user = userDAO.getUserById(userId);

		if (user != null) {
			System.out.println(user);
		} else {
			System.out.println("User Not Found");
		}
	}

	// ========================= GET BY EMAIL =========================

	public void getUserByEmailTest(String email) {

		User user = userDAO.getUserByEmail(email);

		if (user != null) {
			System.out.println(user);
		} else {
			System.out.println("User Not Found");
		}
	}

	// ========================= GET BY PHONE =========================

	public void getUserByPhoneTest(String phone) {

		User user = userDAO.getUserByPhone(phone);

		if (user != null) {
			System.out.println(user);
		} else {
			System.out.println("User Not Found");
		}
	}

	// ========================= GET ALL =========================

	public void getAllUsersTest() {

		List<User> users = userDAO.getAllUsers();

		if (users == null || users.isEmpty()) {
			System.out.println("No Users Found");
			return;
		}

		System.out.println("========== USER LIST ==========");

		for (User user : users) {
			System.out.println(user);
		}
	}

	// ========================= GET BY ROLE =========================

	public void getUsersByRoleTest(UserRole role) {

		List<User> users = userDAO.getUsersByRole(role);

		if (users == null || users.isEmpty()) {
			System.out.println("No Users Found");
			return;
		}

		System.out.println("===== USERS WITH ROLE : " + role + " =====");

		for (User user : users) {
			System.out.println(user);
		}
	}

	// ========================= DELETE =========================

	public void deleteUserTest(Long userId) {

		if (userDAO.deleteUser(userId)) {
			System.out.println("User Deleted Successfully");
		} else {
			System.out.println("User Not Found");
		}
	}

}