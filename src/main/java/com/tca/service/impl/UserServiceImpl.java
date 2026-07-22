package com.tca.service.impl;

import com.tca.dao.impl.UserDAOImpl;
import com.tca.entity.User;
import com.tca.enums.AuthProvider;
import com.tca.enums.UserRole;
import com.tca.service.UserService;

public class UserServiceImpl implements UserService {

    private final UserDAOImpl userDAO = new UserDAOImpl();

    @Override
    public boolean registerWithLocal(User user) {
        if (user == null || user.getPhone() == null || user.getPhone().trim().isEmpty()) {
            return false;
        }

        if (userDAO.getUserByPhone(user.getPhone()) != null) {
            return false;
        }

        user.setUserRole(UserRole.CUSTOMER);
        user.setIsActive(true);
        user.setAuthProvider(AuthProvider.LOCAL);
        user.setIsVerified(true);

        return userDAO.saveUser(user);
    }

    @Override
    public boolean login(String phone, String password) {
        if (phone == null || password == null) {
            return false;
        }

        User user = userDAO.getUserByPhone(phone);

        if (user != null && Boolean.TRUE.equals(user.getIsActive())) {
            return password.equals(user.getPassword());
        }
        return false;
    }

    @Override
    public boolean loginWithSocial(User user) {
        if (user == null || user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            return false;
        }

        User existingUser = userDAO.getUserByEmail(user.getEmail());

        if (existingUser == null) {
            // First-time Firebase login -> Register automatically in DB
            user.setUserRole(UserRole.CUSTOMER);
            user.setIsActive(true);
            user.setAuthProvider(AuthProvider.GOOGLE);
            user.setIsVerified(true);

            return userDAO.saveUser(user);
        }

        return Boolean.TRUE.equals(existingUser.getIsActive());
    }

    @Override
    public boolean isPhoneExists(String phone) {
        if (phone == null || phone.trim().isEmpty()) return false;
        return userDAO.getUserByPhone(phone) != null;
    }

    @Override
    public boolean isEmailExists(String email) {
        if (email == null || email.trim().isEmpty()) return false;
        return userDAO.getUserByEmail(email) != null;
    }


    @Override
    public boolean changePassword(Long userId, String oldPassword, String newPassword) {
        if (userId == null || oldPassword == null || newPassword == null) {
            return false;
        }

        User user = userDAO.getUserById(userId);
        if (user != null && oldPassword.equals(user.getPassword())) {
            user.setPassword(newPassword);
            return userDAO.updateUser(user);
        }

        return false;
    }

    @Override
    public boolean forgotPassword(String phone, String newPassword) {
        if (phone == null || newPassword == null) {
            return false;
        }

        User user = userDAO.getUserByPhone(phone);
        if (user != null) {
            user.setPassword(newPassword);
            return userDAO.updateUser(user);
        }

        return false;
    }


    @Override
    public User getUserById(Long userId) {
        if (userId == null) return null;
        return userDAO.getUserById(userId);
    }

    @Override
    public User getUserByPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) return null;
        return userDAO.getUserByPhone(phone);
    }

    @Override
    public boolean activateUser(Long userId) {
        if (userId == null) return false;

        User user = userDAO.getUserById(userId);
        if (user != null) {
            user.setIsActive(true);
            return userDAO.updateUser(user);
        }
        return false;
    }

    @Override
    public boolean deActivateUser(Long userId) {
        if (userId == null) return false;

        User user = userDAO.getUserById(userId);
        if (user != null) {
            user.setIsActive(false);
            return userDAO.updateUser(user);
        }
        return false;
    }
}