package com.tca.service;

import com.tca.entity.User;

public interface UserService {

    // -------------------------------------------------------------
    // Authentication & Registration
    // -------------------------------------------------------------

    boolean registerWithLocal(User user);

    boolean login(String phone, String password);

    boolean loginWithSocial(User user);

    // -------------------------------------------------------------
    // Existence Checks
    // -------------------------------------------------------------

    boolean isPhoneExists(String phone);

    boolean isEmailExists(String email);

    // -------------------------------------------------------------
    // Password Management
    // -------------------------------------------------------------

    boolean changePassword(Long userId, String oldPassword, String newPassword);

    boolean forgotPassword(String phone, String newPassword);

    // -------------------------------------------------------------
    // User Account Administration
    // -------------------------------------------------------------

    User getUserById(Long userId);

    User getUserByPhone(String phone);

    boolean activateUser(Long userId);

    boolean deActivateUser(Long userId);
}