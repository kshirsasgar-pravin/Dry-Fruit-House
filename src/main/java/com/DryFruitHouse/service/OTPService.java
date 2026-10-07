package com.DryFruitHouse.service;

public interface OTPService {

    String generateOTP(String phone);

    boolean sendOTP(String phone, String otp);


}
