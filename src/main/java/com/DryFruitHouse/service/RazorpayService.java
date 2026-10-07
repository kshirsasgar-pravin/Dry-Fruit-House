package com.DryFruitHouse.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Properties;

public class RazorpayService {

    private RazorpayClient razorpayClient;

    public RazorpayService() throws Exception{
        Properties properties = new Properties();

        InputStream inputStream =
                getClass()
                        .getClassLoader()
                        .getResourceAsStream("application.properties");
        properties.load(inputStream);

        String keyId = properties.getProperty("razorpay.key.id");
        String keySecret = properties.getProperty("razorpay.key.secret");

        razorpayClient = new RazorpayClient(keyId,keySecret);
    }


    public String createOrder(BigDecimal amount) throws Exception{
        JSONObject orderRequest = new JSONObject();

        orderRequest.put("amount",amount.multiply(BigDecimal.valueOf(100)));
        orderRequest.put("currency","INR");
        orderRequest.put("receipt","receipt_"+System.currentTimeMillis());

        Order order = razorpayClient.orders.create(orderRequest);

        return order.get("id");
    }


}
