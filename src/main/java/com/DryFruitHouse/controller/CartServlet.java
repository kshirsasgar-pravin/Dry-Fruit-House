package com.DryFruitHouse.controller;

import com.DryFruitHouse.entity.Cart;
import com.DryFruitHouse.entity.User;
import com.DryFruitHouse.service.CartService;
import com.DryFruitHouse.service.impl.CartServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.security.spec.ECField;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String ACTION_ADD = "add";
    private static final String ACTION_REMOVE = "remove";
    private static final String ACTION_UPDATE = "update";
    private static final String ACTION_VIEW = "view";
    private static final String ACTION_CLEAR = "clear";

    private CartService cartService;

    @Override
    public void init() throws ServletException {
        super.init();
        cartService = new CartServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action == null || action.isEmpty()) {
            action = ACTION_VIEW;
        }

        switch (action) {
            case ACTION_VIEW:
                viewCart(req, resp);
                break;

            default:
                sendBadRequest(resp, "Invalid action parameter.");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action == null || action.isEmpty()) {
            sendBadRequest(resp, "Action parameter is missing.");
            return;
        }

        switch (action) {
            case ACTION_ADD:
                addToCart(req, resp);
                break;
            case ACTION_REMOVE:
                removeFromCart(req, resp);
                break;
            case ACTION_UPDATE:
                updateCartItem(req, resp);
                break;
            case ACTION_CLEAR:
                clearCart(resp, req);
                break;
            default:
                sendBadRequest(resp, "Invalid action parameter.");
        }
    }


    private void viewCart(
            HttpServletRequest req,
            HttpServletResponse resp)
             {
                 try {
                 HttpSession session = req.getSession(false);

            User user = (session != null)
                    ? (User) session.getAttribute("loggedInUser")
                    : null;

            if (user == null) {
                sendBadRequest(resp, "User not logged in.");
                return;
            }

            Long userId = user.getUserId();

            Cart cart = cartService.getCartByUserId(userId);

            req.setAttribute("cart", cart);
            req.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(req, resp);
        } catch (Exception e) {
                     try {
                         handleException(req,resp,e);
                     } catch (ServletException ex) {
                         throw new RuntimeException(ex);
                     } catch (IOException ex) {
                         throw new RuntimeException(ex);
                     }
                 }
    }

    private void addToCart(
            HttpServletRequest req,
            HttpServletResponse resp) throws ServletException, IOException {
           Long productId = parseLongParameter(req, "productId");
           Integer quantity = parseIntegerParameter(req, "quantity");

        try {
            HttpSession session = req.getSession(false);
            User user = (session != null)
                    ? (User) session.getAttribute("loggedInUser")
                    : null;

            if (user == null) {
                sendBadRequest(resp, "User not logged in.");
                return;
            }

            Long userId = user.getUserId();

            if (    userId == null ||
                    productId == null ||
                    quantity == null
            ) {
                sendBadRequest(resp, "Invalid user or product or quantity.");
                return;
            }

            boolean success = cartService.addToCart(userId, productId, quantity);

            if (!success) {
                resp.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Failed to add product to cart."

                );
                return;
            }
            resp.sendRedirect(
                    req.getContextPath()
                            + "/home"
            );
        } catch (Exception e) {
                handleException(req,resp,e);
        }
    }

    private void removeFromCart(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {
        Long productId = parseLongParameter(req, "productId");

        try {
            HttpSession session = req.getSession(false);
            User user = (session != null)
                    ? (User) session.getAttribute("loggedInUser")
                    : null;

            if (user == null) {
                sendBadRequest(resp, "User not logged in.");
                return;
            }

            Long userId = user.getUserId();

            if(cartService.removeFromCart(userId,productId)){
                resp.sendRedirect(
                        req.getContextPath() + "/cart?action=view"
                );
                return;
            }
            resp.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Failed to remove product from cart."
            );

        } catch (IOException e) {
            handleException(req,resp,e);
        }
    }

    private void clearCart(
            HttpServletResponse response,
            HttpServletRequest request
            ) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        try {
            User user = (session != null)
                    ? (User) session.getAttribute("loggedInUser")
                    : null;

            if (user == null) {
                sendBadRequest(response, "User not logged in.");
                return;
            }

            Long userId = user.getUserId();
            if(!cartService.clearCart(userId)) {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Failed to clear cart."
                );
                return;
            }
            response.sendRedirect(
                    request.getContextPath()
                            + "/home");
        } catch (IOException e) {
            handleException(request, response, e);
        }
    }

    private void updateCartItem(
            HttpServletRequest request,
            HttpServletResponse response
                ) throws
            IOException,ServletException  {
        try {
        Long productId = parseLongParameter(request, "productId");
        Integer quantity = parseIntegerParameter(request, "quantity");

            if(productId == null || quantity == null || quantity <= 0) {
                sendBadRequest(response, "Invalid product or quantity.");
                return;
            }
            HttpSession session = request.getSession(false);
            User user = (session != null)
                    ? (User) session.getAttribute("loggedInUser")
                    : null;

            if (user == null) {
                sendBadRequest(response, "User not logged in.");
                return;
            }

            Long userId = user.getUserId();
            if(!cartService.updateQuantity(userId, productId, quantity)) {
                response.sendError( HttpServletResponse.SC_BAD_REQUEST,"Failed to update cart item.");
            }
            else{
                response.sendRedirect(
                        request.getContextPath()
                                + "/cart?action=view"
                );
            }
        } catch (IOException ie) {
            handleException(request, response, ie);
        } catch (Exception e){
            handleException(request,response,e);
        }
    }

    private Long parseLongParameter(HttpServletRequest req, String paramName) {
        String paramValue = req.getParameter(paramName);
        if (paramValue == null || paramValue.isEmpty()) {
            return null;
        }
        try {
            return Long.parseLong(paramValue);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Integer parseIntegerParameter(HttpServletRequest req, String paramName) {
        String paramValue = req.getParameter(paramName);
        if (paramValue == null || paramValue.isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(paramValue);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    private void sendBadRequest(
            HttpServletResponse resp, 
            String message) 
            throws IOException {
        resp.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                message
        );
       
    }
    
    private void handleException(
            HttpServletRequest req,
            HttpServletResponse resp, 
            Exception e) 
            throws ServletException,IOException {
        log("Error processing request cart request", e);
        resp.sendError(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "An error occurred while processing your request."
        );
    }
    
}
