package com.tca.controller;

import com.tca.entity.User;
import com.tca.service.impl.OTPServiceImpl;
import com.tca.service.impl.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("{/AuthServlet,/auth}")
public class AuthServlet extends HttpServlet {

    private final UserServiceImpl userService = new UserServiceImpl();
    private final OTPServiceImpl otpService = new OTPServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("initiateRegister".equals(action)) {
            handleInitiateRegister(request, response);
        } else if ("verifyOtpAndRegister".equals(action)) {
            handleVerifyAndRegister(request, response);
        } else if ("login".equals(action)) {
            handleLogin(request, response);
        }
    }

    // -------------------------------------------------------------
    // 1. Check Phone & Send OTP
    // -------------------------------------------------------------
    private void handleInitiateRegister(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        String phone = request.getParameter("phone");
        String password = request.getParameter("password");

        if (userService.isPhoneExists(phone)) {
            request.setAttribute("error", "Phone number is already registered. Please log in.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        // Step 2: Generate & Send OTP
        String otp = otpService.generateOTP();
        otpService.sendOTP(phone, otp);

        HttpSession session = request.getSession();
        session.setAttribute("TEMP_PHONE", phone);
        session.setAttribute("TEMP_PASSWORD", password);
        session.setAttribute("TEMP_OTP", otp);

        request.getRequestDispatcher("/verify-otp.jsp").forward(request, response);
    }

    // -------------------------------------------------------------
    // 2. Verify OTP & Register User
    // -------------------------------------------------------------
    private void handleVerifyAndRegister(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String enteredOtp = request.getParameter("otp");

        if (session == null || session.getAttribute("TEMP_OTP") == null) {
            request.setAttribute("error", "Session expired. Please register again.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        String actualOtp = (String) session.getAttribute("TEMP_OTP");

        // Verify OTP match
        if (actualOtp.equals(enteredOtp)) {
            // Build User object from session data
            User user = new User();
            user.setPhone((String) session.getAttribute("TEMP_PHONE"));
            user.setPassword((String) session.getAttribute("TEMP_PASSWORD"));

            // Save user to Database
            boolean isSaved = userService.registerWithLocal(user);

            if (isSaved) {
                // Clear temporary OTP attributes
                session.removeAttribute("TEMP_PHONE");
                session.removeAttribute("TEMP_PASSWORD");
                session.removeAttribute("TEMP_OTP");

                // Set active user session (Auto Login)
                session.setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "/home.jsp");
            } else {
                request.setAttribute("error", "Failed to register user. Try again.");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "Invalid OTP code. Please try again.");
            request.getRequestDispatcher("/verify-otp.jsp").forward(request, response);
        }
    }

    // -------------------------------------------------------------
    // 3. Login with Phone and Password
    // -------------------------------------------------------------
    private void handleLogin(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        String phone = request.getParameter("phone");
        String password = request.getParameter("password");

        boolean isValid = userService.login(phone, password);

        if (isValid) {
            User loggedInUser = userService.getUserByPhone(phone);
            
            HttpSession session = request.getSession();
            session.setAttribute("user", loggedInUser);
            
            response.sendRedirect(request.getContextPath() + "/home.jsp");
        } else {
            request.setAttribute("error", "Invalid Phone number or Password.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}