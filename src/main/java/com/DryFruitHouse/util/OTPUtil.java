package com.DryFruitHouse.util;

import java.security.SecureRandom;

public class OTPUtil {

    private static final SecureRandom random = new SecureRandom();

    public static String generateOTP() {
        int otp = 1000 + random.nextInt(9000); // Generates 4-digit number
        return String.valueOf(otp);
    }

    public static boolean verifyOTP(String enteredOTP, String actualOTP) {
        return enteredOTP != null && enteredOTP.equals(actualOTP);
    }
}