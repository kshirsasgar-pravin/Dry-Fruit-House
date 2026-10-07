package com.DryFruitHouse.controller;

import com.DryFruitHouse.entity.User;
import com.DryFruitHouse.service.UserService;
import com.DryFruitHouse.service.impl.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/verify-update-otp")
public class VerifyAndUpdateOTPServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserServiceImpl();
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Session does not exist
        if (session == null) {
            response.sendRedirect(
                    request.getContextPath()
                            + "/login.jsp"
            );
            return;
        }

        String enteredOTP =
                request.getParameter("otp");


        String actualOTP =
                (String) session.getAttribute(
                        "generatedOTP"
                );

        if (enteredOTP == null
                || actualOTP == null
                || !enteredOTP.equals(actualOTP)) {

            request.setAttribute(
                    "errorMessage",
                    "Invalid OTP. Please try again."
            );

            request.getRequestDispatcher(
                    "/WEB-INF/views/user/verify-update-otp.jsp"
            ).forward(request, response);

            return;
        }

        Long userId =
                (Long) session.getAttribute(
                        "pendingUserId"
                );

        String userName =
                (String) session.getAttribute(
                        "pendingUserName"
                );

        String phone =
                (String) session.getAttribute(
                        "pendingUserPhone"
                );

        String email =
                (String) session.getAttribute(
                        "pendingUserEmail"
                );

        if (userId == null) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "User update session expired."
            );

            return;
        }

        try {

            User user =
                    userService.getUserById(userId);

            if (user == null) {

                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "User not found"
                );

                return;
            }

            /*
             * Set the pending values.
             *
             * These values were NOT updated before OTP
             * verification.
             */
            user.setName(userName);
            user.setPhone(phone);
            user.setEmail(email);

            // Update database
            boolean updated =
                    userService.updateUser(user);

            if (!updated) {

                response.sendError(
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Unable to update user"
                );

                return;
            }

            /*
             * OTP has been successfully used.
             *
             * Remove temporary data from session.
             */
            session.removeAttribute("generatedOTP");
            session.removeAttribute("otpDestination");
            session.removeAttribute("otpPurpose");

            session.removeAttribute("pendingUserId");
            session.removeAttribute("pendingUserName");
            session.removeAttribute("pendingUserPhone");
            session.removeAttribute("pendingUserEmail");

            session.removeAttribute("phoneChanged");
            session.removeAttribute("emailChanged");


            //update successful, redirect to user details page
            response.sendRedirect(
                    request.getContextPath()
                            + "/user?action=get&userId="
                            + userId
            );

        } catch (Exception e) {

            e.printStackTrace();

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "An error occurred while updating user"
            );
        }
    }
}