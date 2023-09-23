package com.itself.proxy.staticproxy;


import com.itself.proxy.Landlord;

/**
 * 房东房屋出租
 */
public class HangZhouLandlord implements Landlord {
    @Override
    public void apartmentToRent() {
        System.out.println("杭州房东出租房子");
    }
}