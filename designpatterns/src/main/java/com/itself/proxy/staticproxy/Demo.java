package com.itself.proxy.staticproxy;


import com.itself.proxy.Landlord;

/**
 * @Author xxw
 * @Date 2023/04/21
 */
public class Demo {
    public static void main(String[] args) {
        Landlord landlord = new HangZhouLandlord();
        LandlordProxy proxy = new LandlordProxy(landlord);
        proxy.apartmentToRent();
    }
}
