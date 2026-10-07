package com.DryFruitHouse.controller;

import com.DryFruitHouse.entity.User;
import com.DryFruitHouse.service.OTPService;
import com.DryFruitHouse.service.UserService;
import com.DryFruitHouse.service.impl.OTPServiceImpl;
import com.DryFruitHouse.service.impl.UserServiceImpl;

import com.DryFruitHouse.util.OTPUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import static com.DryFruitHouse.util.OTPUtil.generateOTP;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String ACTION_LIST = "list";
    private static final String ACTION_ACTIVE = "active";
    private static final String ACTION_GET = "get";
    private static final String ACTION_GET_BY_NAME = "getByName";
    private static final String ACTION_ADD = "add";
    private static final String ACTION_UPDATE = "update";
    private static final String ACTION_DELETE = "delete";

    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserServiceImpl();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null || action.isBlank()) {
            action = ACTION_LIST;
        }

        switch (action) {

            case ACTION_LIST:
                listUsers(request, response);
                break;

            case ACTION_ACTIVE:
                activeUser(request, response);
                break;

            case ACTION_GET:
                getUserById(request, response);
                break;

            case ACTION_GET_BY_NAME:
                getUserByName(request, response);
                break;

            default:
                sendBadRequest(response, "Invalid user action");
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

            case ACTION_ADD:
                addUser(request, response);
                break;

            case ACTION_UPDATE:
                updateUser(request, response);
                break;

            case ACTION_DELETE:
                deleteUser(request, response);
                break;

            default:
                sendBadRequest(response, "Invalid user action");
        }
    }

    private void getUserByName(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String userName = request.getParameter("userName");

        if (userName == null || userName.isBlank()) {
            sendBadRequest(
                    response,
                    "Missing or empty 'userName' parameter"
            );
            return;
        }

        try {

            User user = userService.getUserByName(userName);

            if (user == null) {
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "User not found"
                );
                return;
            }

            request.setAttribute("user", user);

            request.getRequestDispatcher(
                    "/WEB-INF/views/user/view.jsp"
            ).forward(request, response);

        } catch (Exception e) {
            handleException(response, e);
        }
    }

    private void getUserById(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Long userId = parseLongParameter(
                request,
                "userId"
        );

        if (userId == null) {
            sendBadRequest(
                    response,
                    "Missing or invalid 'userId' parameter"
            );
            return;
        }

        try {

            User user = userService.getUserById(userId);

            if (user == null) {
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "User not found"
                );
                return;
            }

            request.setAttribute("user", user);

            request.getRequestDispatcher(
                    "/WEB-INF/views/user/view.jsp"
            ).forward(request, response);

        } catch (Exception e) {
            handleException(response, e);
        }
    }

    private void activeUser(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            List<User> activeUsers =
                    userService.getActiveUser();

            request.setAttribute(
                    "activeUsers",
                    activeUsers
            );

            request.getRequestDispatcher(
                    "/WEB-INF/views/user/active.jsp"
            ).forward(request, response);

        } catch (Exception e) {
            handleException(response, e);
        }
    }

    private void listUsers(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            List<User> users =
                    userService.getAllUesrs();

            if (users == null || users.isEmpty()) {
                request.setAttribute(
                        "message",
                        "No users found."
                );
            }

            request.setAttribute(
                    "users",
                    users
            );

            request.getRequestDispatcher(
                    "/WEB-INF/views/user/list.jsp"
            ).forward(request, response);

        } catch (Exception e) {
            handleException(response, e);
        }
    }

    private void deleteUser(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Long userId = parseLongParameter(
                request,
                "userId"
        );

        if (userId == null) {
            sendBadRequest(
                    response,
                    "Missing or invalid 'userId' parameter"
            );
            return;
        }

        try {

            if (!userService.deActivateUser(userId)) {

                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "User not found"
                );
                return;
            }

            response.sendRedirect(
                    request.getContextPath()
                            + "/user?action=list"
            );

        } catch (Exception e) {
            handleException(response, e);
        }
    }

    private void updateUser(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Long userId = parseLongParameter(
                request,
                "userId"
        );

        if (userId == null) {
            sendBadRequest(
                    response,
                    "Missing or invalid 'userId' parameter"
            );
            return;
        }

        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        if (userName == null || userName.isBlank()
                || email == null || email.isBlank()
                || phone == null || phone.isBlank()) {

            sendBadRequest(
                    response,
                    "Missing or empty parameters"
            );
            return;
        }

        try {

            // Get existing user
            User existingUser =
                    userService.getUserById(userId);

            if (existingUser == null) {
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "User not found"
                );
                return;
            }

            // Check what has changed
            boolean phoneChanged =
                    !phone.equals(existingUser.getPhone());

            boolean emailChanged =
                    !email.equals(existingUser.getEmail());

           // phone change or email change requires OTP verification
            if (phoneChanged || emailChanged) {

                HttpSession session =
                        request.getSession();

                /*
                 * Store all pending changes in session.
                 */
                session.setAttribute(
                        "pendingUserId",
                        userId
                );

                session.setAttribute(
                        "pendingUserName",
                        userName
                );

                session.setAttribute(
                        "pendingUserPhone",
                        phone
                );

                session.setAttribute(
                        "pendingUserEmail",
                        email
                );

                session.setAttribute(
                        "phoneChanged",
                        phoneChanged
                );

                session.setAttribute(
                        "emailChanged",
                        emailChanged
                );


                OTPService otpService =
                        new OTPServiceImpl();

                String destination;
                // set a destination for OTP based on what has changed
                if (phoneChanged) {
                    destination = phone;
                } else {
                    destination = email;
                }

                String generatedOTP =
                        otpService.generateOTP(destination);


                if (phoneChanged && emailChanged) {

                    sendBadRequest(
                            response,
                            "Please update phone or email separately"
                    );

                    return;
                }

                //stores a generated OTP in the session for later verification
                session.setAttribute(
                        "generatedOTP",
                        generatedOTP
                );


                 // Remember where OTP was sent.

                session.setAttribute(
                        "otpDestination",
                        destination
                );

                if (phoneChanged) {

                    session.setAttribute(
                            "otpPurpose",
                            "PHONE_UPDATE"
                    );

                } else {

                    session.setAttribute(
                            "otpPurpose",
                            "EMAIL_UPDATE"
                    );
                }
                otpService.sendOTP(
                        destination,
                        generatedOTP
                );

                 // REDIRECT TO OTP PAGE

                request.getRequestDispatcher(
                        "/WEB-INF/views/user/verify-update-otp.jsp"
                ).forward(request, response);

                return;
            }

            existingUser.setName(userName);

            boolean updated =
                    userService.updateUser(existingUser);

            if (!updated) {

                response.sendError(
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Unable to update user"
                );

                return;
            }

            response.sendRedirect(
                    request.getContextPath()
                            + "/user?action=list"
            );

        } catch (Exception e) {

            handleException(
                    response,
                    e
            );
        }
    }

    private void addUser(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

    }

    private Long parseLongParameter(
            HttpServletRequest request,
            String parameterName) {

        String parameterValue =
                request.getParameter(parameterName);

        if (parameterValue != null
                && !parameterValue.isBlank()) {

            try {

                return Long.parseLong(
                        parameterValue
                );

            } catch (NumberFormatException e) {

                return null;
            }
        }

        return null;
    }

    // =========================
    // BAD REQUEST
    // =========================

    private void sendBadRequest(
            HttpServletResponse response,
            String message)
            throws IOException {

        response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                message
        );
    }

    // =========================
    // EXCEPTION HANDLER
    // =========================

    private void handleException(
            HttpServletResponse response,
            Exception e)
            throws ServletException, IOException {

        e.printStackTrace();

        response.sendError(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "An error occurred while processing the request"
        );
    }
}