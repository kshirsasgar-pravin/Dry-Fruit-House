package com.DryFruitHouse.controller;

import com.DryFruitHouse.entity.User;
import com.DryFruitHouse.service.OTPService;
import com.DryFruitHouse.service.UserService;
import com.DryFruitHouse.service.impl.OTPServiceImpl;
import com.DryFruitHouse.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class AuthenticationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String ACTION_LOGIN = "login";
    private static final String ACTION_REGISTER = "register";
    private static final String ACTION_GOOGLE_LOGIN = "googleLogin";
    private static final String ACTION_LOGOUT = "logout";
    private static final String ACTION_CHANGE_PASSWORD = "changePassword";

    private UserServiceImpl  userService;
    @Override
    public void init() throws ServletException{
        userService = new UserServiceImpl();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
          String action = request.getParameter("action");

          if(action == null){
              action = ACTION_LOGIN;
          }

          switch (action){
              case ACTION_LOGIN:
                  showLoginPage(request,response);
                  break;


              case ACTION_REGISTER:
                      showRegistrationPage(request,response);
                      break;

              case ACTION_CHANGE_PASSWORD:
                  showChangePasswordPage(request,response);
                  break;

              default : sendBadRequest(response,"Valide action required");
          }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null || action.isBlank()) {
            sendBadRequest(response, "Valid action required");
            return;
        }

        switch (action) {

            case ACTION_LOGIN:
                login(request, response);
                break;

            case ACTION_REGISTER:
                registerUser(request, response);
                break;

            case ACTION_GOOGLE_LOGIN:
                googleLogin(request, response);
                break;

            case ACTION_CHANGE_PASSWORD:
                changePassword(request, response);
                break;

            case ACTION_LOGOUT:
               logout(request, response);
                break;

            default:
                sendBadRequest(response, "Invalid authentication action");
        }
    }

    private void login(
            HttpServletRequest request,
            HttpServletResponse response) throws ServletException,IOException {
            String phone = request.getParameter("phone");
            String password = request.getParameter("password");

            if(phone == null || phone.isBlank()){
                sendBadRequest(
                        response,
                        "Phone No. Required"
                );
                return;
            }
            if(password == null || password.isBlank()){
                sendBadRequest(
                        response,
                        "Password required"
                );
                return;
            }

            User user = userService.login(phone,password);

            if(user == null){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                        "Invalid Phone No. or Password"
                );
                return;
            }

            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser",user);

            response.sendRedirect(
                    request.getContextPath()+"/home"
            );

    }

    private void registerUser(
            HttpServletRequest request,
            HttpServletResponse response) throws ServletException,IOException{
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (!password.equals(confirmPassword)) {
            sendBadRequest(
                    response,
                    "Passwords do not match"
            );
            return;
        }

        OTPService otpService = new OTPServiceImpl();
        String generatedOTP = otpService.generateOTP(phone);
        try {
            if (generatedOTP == null) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Failed to generate OTP. Please try again later."
                );
                return;
            }
            if (otpService.sendOTP(phone, generatedOTP)) {
                HttpSession session = request.getSession();
                session.setAttribute("generatedOTP", generatedOTP);
                session.setAttribute("pendingPhone", phone);
                session.setAttribute("pendingPassword", password);

                response.sendRedirect(
                        request.getContextPath() + "/verify-otp.jsp"
                );

            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Failed to send OTP. Please try again later."
                );
            }


        } catch (IOException e) {
            handleException(response, e);
        }

        userService.registerWithLocal(phone,password);

    }

    private void googleLogin(
            HttpServletRequest request,
            HttpServletResponse response)throws ServletException,IOException {
    }

    private void changePassword(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Get the logged-in user's session
        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "User is not logged in"
            );
            return;
        }

        // Get the logged-in user from session
        User loggedInUser =
                (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "User is not logged in"
            );
            return;
        }

        // Get passwords from request
        String oldPassword =
                request.getParameter("oldPassword");

        String newPassword =
                request.getParameter("newPassword");

        String confirmPassword =
                request.getParameter("confirmPassword");

        // Validate old password
        if (oldPassword == null || oldPassword.isBlank()) {
            sendBadRequest(
                    response,
                    "Old password is required"
            );
            return;
        }

        // Validate new password
        if (newPassword == null || newPassword.isBlank()) {
            sendBadRequest(
                    response,
                    "New password is required"
            );
            return;
        }

        // Validate confirm password
        if (confirmPassword == null || confirmPassword.isBlank()) {
            sendBadRequest(
                    response,
                    "Confirm password is required"
            );
            return;
        }

        // Check new password and confirm password
        if (!newPassword.equals(confirmPassword)) {
            sendBadRequest(
                    response,
                    "New password and confirm password do not match"
            );
            return;
        }

        if (newPassword.length() < 8) {
            sendBadRequest(
                    response,
                    "Password must contain at least 8 characters"
            );
            return;
        }

        try {

            // Call service layer
            boolean changed = userService.changePassword(
                    loggedInUser.getUserId(),
                    oldPassword,
                    newPassword
            );

            if (!changed) {
                response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Old password is incorrect"
                );
                return;
            }

            // Password changed successfully
            response.sendRedirect(
                    request.getContextPath()
                            + "/authentication?action=changePassword&success=true"
            );

        } catch (Exception e) {
            handleException(response, e);
        }
    }

    private void logout(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        response.sendRedirect(
                request.getContextPath()
                        + "/authentication?action=login"
        );
    }

    

    private void showLoginPage(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp")
                .forward(request, response);
    }
    private void showRegistrationPage(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp")
                .forward(request, response);
    }

    private void showChangePasswordPage(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/views/auth/change-password.jsp")
                .forward(request, response);
    }

    private void sendBadRequest(
            HttpServletResponse response,
            String message)
            throws IOException {

        response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                message
        );
    }
    private void handleException(
            HttpServletResponse response,
            Exception e)
            throws ServletException, IOException {

        response.sendError(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "An error occurred while processing the request"
        );
    }

}
