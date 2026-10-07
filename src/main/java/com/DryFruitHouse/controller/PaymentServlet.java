package com.DryFruitHouse.controller;

import com.DryFruitHouse.enums.PaymentMode;
import com.DryFruitHouse.service.OrderService;
import com.DryFruitHouse.service.PaymentService;
import com.DryFruitHouse.service.impl.OrderServiceImpl;
import com.DryFruitHouse.service.impl.PaymentServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet({"/payment","/verify"})
public class PaymentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private PaymentService paymentService = new PaymentServiceImpl();
    private OrderService orderService = new OrderServiceImpl();

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getServletPath();

        if (path.equals("/payment")){
            if(req.getParameter("paymentMode").equals(PaymentMode.COD)){



                orderService.placeOrder();
            }

            try{

                String razorpayOrder = paymentService.createRazorpayOrder()

            }catch (Exception e){
                e.printStackTrace();
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR
                "Failed to create payment");
            }

        }

    }
}
