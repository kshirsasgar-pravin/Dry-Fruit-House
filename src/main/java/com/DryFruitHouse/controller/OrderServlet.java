package com.DryFruitHouse.controller;

import com.DryFruitHouse.entity.Order;
import com.DryFruitHouse.entity.User;
import com.DryFruitHouse.service.OrderService;
import com.DryFruitHouse.service.impl.OrderServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.rmi.ServerError;
import java.util.List;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String ACTION_LIST = "list";
    private static final String ACTION_VIEW = "view";
    private static final String ACTION_SAVE = "save";
    private static final String ACTION_UPDATE = "update";
    private static final String ACTION_DELETE = "delete";

    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        orderService = new OrderServiceImpl();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException{
        String action = request.getParameter("action");
        if(action == null || action.isBlank()){
            action = ACTION_LIST;
        }

        try {

        }catch (Exception e){

        }
    }

    private void listOrders(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException,IOException{
        List<Order> orders = orderService.getAllOrders();
        if(orders == null || orders.isEmpty()){
            sendBadRequest(response,"Orders not found");
        }

        request.setAttribute("orders",orders);
        request.getRequestDispatcher("/WEB-INF/views/order/list.jsp");

    }

    private void viewOrder(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException,IOException{
        HttpSession session = request.getSession(false);
        User user = (User)session.getAttribute("loggedInUser");

        if(user == null) {
            sendBadRequest(response,"User not logged in ");
        }

        Long userId = user.getUserId();
        Long orderId = parseLongParameter(request,"orderId");

        if(userId == null ||
           orderId == null){
            sendBadRequest(response,"invalid user or order");
            return;
        }

       Order order = orderService.getOrderByOrderIdAndUserId(orderId,userId);

        if(order == null){
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Order not found "
            );
        }

        request.setAttribute("order",order);
        request.
                getRequestDispatcher("/WEB-INF/views/order/view.jsp").
                forward(
                        request,
                        response
                );

    }

    private void saveOrder(
            HttpServletRequest request,
            HttpServletResponse response
    )throws ServletException,IOException {
        HttpSession session = request.getSession(false);
        User user =(User) session.getAttribute("loggedInUser");
        Long userId = user.getUserId();
        Long productId = parseLongParameter(request,"productId");
        Long addressId = parseLongParameter(request,"addressId");
        if(userId == null||
           productId == null||
           addressId == null){
            sendBadRequest(response,"Invalid userId or productId");
            return;
        }

        Order order = new Order();
    }


    private Long parseLongParameter(
            HttpServletRequest request,
            String parameterName
    ) throws IOException{
        String value = request.getParameter(parameterName);
        if(value == null || value.isBlank()){
            return null;
        }

        try {
            return Long.parseLong(value.trim());

        }catch (NumberFormatException ne){
            return null;
        }
    }

    private void sendBadRequest(
            HttpServletResponse response,
            String message
    ) throws IOException{
        response.sendError(HttpServletResponse.SC_BAD_REQUEST,message);
    }

    private void handleException(
            HttpServletRequest request,
            HttpServletResponse response,
            Exception e) throws ServletException,IOException{
        log("Error while processing the Order Servlet request");

        response.sendError(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "An unexpected error occurred"
        );

    }


}
