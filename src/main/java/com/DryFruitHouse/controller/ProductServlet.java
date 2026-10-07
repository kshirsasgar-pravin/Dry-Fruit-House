package com.DryFruitHouse.controller;

import com.DryFruitHouse.entity.Category;
import com.DryFruitHouse.entity.Product;
import com.DryFruitHouse.service.CategoryService;
import com.DryFruitHouse.service.ProductService;
import com.DryFruitHouse.service.impl.CategoryServiceImpl;
import com.DryFruitHouse.service.impl.ProductServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/product")
public class ProductServlet extends HttpServlet {


    private static final long serialVersionUID = 1L;

    private static final String ACTION_LIST = "list";
    private static final String ACTION_ACTIVE = "active";
    private static final String ACTION_GET = "get";
    private static final String ACTION_GET_BY_NAME = "getByName";
    private static final String ACTION_ADD = "add";
    private static final String ACTION_UPDATE = "update";
    private static final String ACTION_DELETE = "delete";

    private ProductService productService;
    private CategoryService categoryService;
    @Override
    public void init() throws ServletException {
        productService = new ProductServiceImpl();
        categoryService = new CategoryServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action == null || action.isBlank()) {
            action = ACTION_LIST;
        }

        try {
            switch (action) {
                case ACTION_LIST :
                    listProducts(req,resp);
                    break;

                case ACTION_GET_BY_NAME:
                    getProductByName(req,resp);
                    break;

                case ACTION_ACTIVE:
                    getActiveProduct(req,resp);
                    break;

                case ACTION_GET:
                    getProductById(req, resp);
                    break;

                default:
                    sendBadRequest(resp, "Invalid product action");

            }
        } catch (Exception e) {
            handleException(req, resp, e);
        }
    }

    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    )       throws ServletException,IOException{
        String action = request.getParameter("action");
        if(action == null || action.isBlank()){
            sendBadRequest(response,"Valid action required");
            return;
        }

        try {
            switch (action){
                case ACTION_ADD:
                    addProduct(request,response);
                    break;

                case ACTION_DELETE:
                    deleteProduct(request,response);
                    break;

                case ACTION_UPDATE:
                    udpateProduct(request,response);
                    break;

                default:
                    sendBadRequest(response,"Valid action required ");

            }
        }catch (Exception e){
            handleException(request,response,e);
        }
    }

    private void listProducts(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        List<Product> products = productService.getAllProducts();
        request.setAttribute("products", products);
        request.getRequestDispatcher("/WEB-INF/views/product/list.jsp").forward(request, response);
    }

    private void getActiveProduct(
            HttpServletRequest request ,
            HttpServletResponse response)
            throws ServletException,IOException{
        List<Product> products = productService.getActiveProducts();
        request.setAttribute("activeProducts" ,products);
        request.getRequestDispatcher("/WEB-INF/views/product/list.jsp").forward(request,response);
    }

    private void getProductById(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException,IOException{

        Long productId = parseLongParameter(
                request ,
                "productId"
        );
        if(productId == null) {
            sendBadRequest(
                    response,
                    "Valid productId is required");
            return;
        }
        Product product = productService.getProductById(productId);

        if(product == null) {
            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Product not found "
            );
            return;
        }

        request.setAttribute("product",product);
        request.getRequestDispatcher("/WEB-INF/views/product/view.jsp").forward(request,response);
    }

    private void getProductByName(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException,IOException{
        String productName = request.getParameter("productName");

        if(productName == null || productName.isBlank()){
            sendBadRequest(
                    response,
                    "Valid Product Name required");
            return;
        }

        Product product = productService.getProductByName(productName);

        if(product == null){
            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Product Not found");
            return;
        }

        request.setAttribute("product",product);
        request.getRequestDispatcher("/WEB-INF/views/product/view.jsp").forward(request,response);
    }

    private void addProduct(HttpServletRequest request,
                            HttpServletResponse response)
            throws ServletException,IOException{
        String productName = request.getParameter("productName");
        String productDescription = request.getParameter("productDescription");
        Integer productStockQuantity = Integer.parseInt(request.getParameter("stockQuantity"));
        String productImage = request.getParameter("productImage");

        BigDecimal productPrice;

        try {
            productPrice = new BigDecimal(request.getParameter("productPrice"));
        } catch (NumberFormatException e) {
            sendBadRequest(response, "Invalid product price");
            return;
        }

        BigDecimal productWeight;

        try {
            productWeight = new BigDecimal(request.getParameter("productWeight"));
        } catch (NumberFormatException e) {
            sendBadRequest(
                    response,
                    "Invalid product weight");
            return;
        }

        Long categoryId = parseLongParameter(
                request,
                "categoryId"
        );

            if(     productName == null ||
                    productName.isBlank() ||
                    productDescription == null ||
                    productDescription.isBlank() ||
                    productImage == null ||
                    productImage.isBlank()
        ){
            sendBadRequest(response,"Please provide All Information");
        }

        if(categoryId == null) {
            sendBadRequest(response,"Category Id required");
            return;
        }
        Category category = categoryService.getCategoryById(categoryId);
        if(category == null){
            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Category NOT FOUND");
            return;
        }

        Product product = new Product();

        product.setProductName(productName);
        product.setDescription(productDescription);
        product.setPrice(productPrice);
        product.setStockQuantity(productStockQuantity);
        product.setWeight(productWeight);
        product.setCategory(category);
        product.setProductImage(productImage);
        product.setUpdatedAt(LocalDateTime.now());
        product.setIsActive(true);

       boolean added = productService.addProduct(product);
       if(added) {
           response.sendRedirect(request.getContextPath()+"/product?action=list");
       }
       else{
           response.sendError(
                   HttpServletResponse.SC_CONFLICT,
                   "Product could not added"
           );
       }

    }

    private void udpateProduct(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException,IOException{
        Long productId = parseLongParameter(
                request,"productId"
        );

        Product product = productService.getProductById(productId);
        if(product == null ){
            sendBadRequest(
                    response,
                    "Valid product required");
            return;
        }

        String productName = request.getParameter("productName");
        String productDescription = request.getParameter("productDescription");
        BigDecimal productWeight = new BigDecimal(request.getParameter("productWeight"));
        BigDecimal productPrice = new BigDecimal(request.getParameter("productPrice"));
        Integer stockQuantity = Integer.parseInt(request.getParameter("stockQuantity"));
        String productImage = request.getParameter("productImage");
        boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));
        LocalDateTime createdAt = LocalDateTime.parse(request.getParameter("createdAt"));
        LocalDateTime updatedAt = LocalDateTime.parse(request.getParameter("updatedAt"));
        Long categoryId = parseLongParameter(request, "categoryId");

        if (categoryId == null) {
            sendBadRequest(
                    response,
                    "Category ID is required");
            return;
        }

        Category category = categoryService.getCategoryById(categoryId);

        if (category == null) {
            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Category not found"
            );
            return;
        }

        product.setProductName(productName);
        product.setDescription(productDescription);
        product.setPrice(productPrice);
        product.setIsActive(isActive);
        product.setStockQuantity(stockQuantity);
        product.setWeight(productWeight);
        product.setProductId(productId);
        product.setCreatedAt(createdAt);
        product.setUpdatedAt(LocalDateTime.now());
        product.setCategory(category);
        product.setProductImage(productImage);

        boolean updated = productService.updateProduct(product);
        if(updated){
            response.sendRedirect(
                    request.getContextPath()
                            +"/product?action=list");
        }
        else{
            response.sendError(
                    HttpServletResponse.SC_CONFLICT,
                    "Product coulden't Updated"
            );
        }

    }


    private void deleteProduct(
            HttpServletRequest request,
            HttpServletResponse response )
            throws ServletException,IOException{
        Long productId = parseLongParameter(request,"productId");
        if(productId == null) {
            sendBadRequest(response,"Valid productid required");
            return;
        }

        boolean deleted = productService.deleteProduct(productId);
        if(deleted) {
            response.sendRedirect(
                    request.getContextPath()
                            + "/product?action=list"
            );
        }else{
            response.sendError(
                    HttpServletResponse.SC_CONFLICT,
                    "Product Couldn't Delete"
            );
        }
    }

    private Long parseLongParameter(
            HttpServletRequest request,
            String parameterName) {

        String value = request.getParameter(parameterName);

        if (value == null || value.isBlank()) {
            return null;
        }

        try {
            return Long.parseLong(value.trim());
        } catch (NumberFormatException e) {
            return null;
        }
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
            HttpServletRequest request,
            HttpServletResponse response,
            Exception e)
            throws ServletException, IOException {

        log("Error while processing ProductServlet request", e);

        response.sendError(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "An unexpected error occurred"
        );
    }

}
