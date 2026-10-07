package com.DryFruitHouse.service;

import com.DryFruitHouse.entity.User;

import java.util.List;

public interface UserService {

    boolean registerWithLocal(String phone , String password);

    User login(String phone, String password);

    boolean loginWithSocial(User user);

    boolean isPhoneExists(String phone);

    boolean isEmailExists(String email);

    boolean changePassword(Long userId, String oldPassword, String newPassword);

    boolean forgotPassword(String phone, String newPassword);

    User getUserById(Long userId);

    User getUserByPhone(String phone);

    boolean activateUser(Long userId);

    boolean deActivateUser(Long userId);

    User getUserByName(String userName);

    List<User> getActiveUser();

    List<User> getAllUesrs();

    boolean updateUser(User existingUser);
}