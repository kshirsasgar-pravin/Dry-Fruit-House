package com.tca.service.impl;

import com.tca.util.OTPUtil;

public class OTPServiceImpl {

    public String generateOTP() {
        return OTPUtil.generateOTP();
    }

    public boolean sendOTP(String phone, String otp) {
        // In production, integrate SMS API here (e.g., Twilio / Fast2SMS)
        System.out.println("==================================");
        System.out.println(" Sending OTP via SMS ");
        System.out.println(" Phone Number : " + phone);
        System.out.println(" OTP Code     : " + otp);
        System.out.println("==================================");

        return true;
    }
}