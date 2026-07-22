package com.tca.util;

import java.security.SecureRandom;

public class OTPUtil {

    private static final SecureRandom random = new SecureRandom();

    public static String generateOTP() {
        int otp = 1000 + random.nextInt(9000); // Generates 6-digit number
        return String.valueOf(otp);
    }
}