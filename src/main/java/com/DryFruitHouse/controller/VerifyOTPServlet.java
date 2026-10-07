package com.DryFruitHouse.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/verify-otp")
public class VerifyOTPServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get the OTP entered by the user
        String enteredOTP = req.getParameter("otp");

        // Get the actual OTP from the session
        String actualOTP = (String) req.getSession().getAttribute("generatedOTP");

        // Compare the entered OTP with the actual OTP
        if (enteredOTP != null && enteredOTP.equals(actualOTP)) {
            // OTP is valid, proceed with the next steps (e.g., redirect to a success page)
            resp.sendRedirect(req.getContextPath() + "/home.jsp");
        } else {
            // OTP is invalid, set an error message and redirect back to the OTP entry page
            req.setAttribute("errorMessage", "Invalid OTP. Please try again.");
            req.getRequestDispatcher("/otp.jsp").forward(req, resp);
        }
    }
}
