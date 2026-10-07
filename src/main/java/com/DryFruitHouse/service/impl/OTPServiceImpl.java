package com.DryFruitHouse.service.impl;

import com.DryFruitHouse.service.OTPService;
import com.DryFruitHouse.util.OTPUtil;

public class OTPServiceImpl implements OTPService {



    @Override
    public String generateOTP(String phone) {
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