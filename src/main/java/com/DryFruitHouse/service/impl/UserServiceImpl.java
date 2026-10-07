package com.DryFruitHouse.service.impl;

import com.DryFruitHouse.repository.impl.UserDAOImpl;
import com.DryFruitHouse.entity.User;
import com.DryFruitHouse.enums.AuthProvider;
import com.DryFruitHouse.enums.UserRole;
import com.DryFruitHouse.service.UserService;
import com.DryFruitHouse.util.PasswordUtil;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDAOImpl userDAO = new UserDAOImpl();


    @Override
    public boolean registerWithLocal(String phone , String password) {
        if (phone == null  || phone.trim().isEmpty()) {
            return false;
        }

        if (userDAO.getUserByPhone(phone) != null) {
            return false;
        }

        User user = new User();
        String hashedPassword = PasswordUtil.hashPassword(password);
        user.setPhone(phone);
        user.setPassword(hashedPassword);
        user.setUserRole(UserRole.CUSTOMER);
        user.setIsActive(true);
        user.setAuthProvider(AuthProvider.LOCAL);
        user.setIsVerified(true);

        return userDAO.saveUser(user);
    }

    @Override
    public User login(String phone, String password) {
        if (phone == null || password == null) {
            return null;
        }

        User user = userDAO.getUserByPhone(phone);

        if(user == null) return null;

        if(PasswordUtil.checkPassword(password,user.getPassword())){
            return user;
        }
        return user;
    }

    @Override
    public boolean loginWithSocial(User user) {
        if (user == null || user.getPhone() == null || user.getPhone().trim().isEmpty()) {
            return false;
        }

        User existingUser = userDAO.getUserByPhone(user.getPhone());

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

    @Override
    public User getUserByName(String userName) {
        return userDAO.getUserByName(userName);
    }

    @Override
    public List<User> getActiveUser() {
        return userDAO.getActiveUser();
    }

    @Override
    public List<User> getAllUesrs() {
        return userDAO.getAllUsers();
    }

    @Override
    public boolean updateUser(User existingUser) {
        return userDAO.updateUser(existingUser);
    }
}