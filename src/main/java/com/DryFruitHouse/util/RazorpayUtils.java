package com.DryFruitHouse.util;

import org.json.JSONObject;

import com.razorpay.Utils;

public class RazorpayUtils {
           public static boolean verifySignature(String razorpayOrderId, 
        		                                     String razorpayPaymentId, 
        		                                     String razorpaySignature, 
        		                                     String secret) {
        	   try {
        		   JSONObject options = new JSONObject();
        		   options.put("rzaorpay_order_id", razorpayOrderId);
        		   options.put("razorpay_payment_Id", razorpayPaymentId);
        		   options.put("razorpaySignature", razorpaySignature);
        		   
        		   return Utils.verifyPaymentSignature(options, secret);
        	   }
        	   catch(Exception e) {
        		   e.printStackTrace();
        	   }
        	   return false;
           }
}
