package com.itself.proxy.dynamicsproxy;


import com.itself.proxy.Landlord;
import com.itself.proxy.staticproxy.HangZhouLandlord;

/**
 * @Author xxw
 * @Date 2023/04/21
 */
public class Demo {
    public static void main(String[] args) {
        Landlord landlord = new HangZhouLandlord();
        ProxyFactory proxy = new ProxyFactory(landlord);
        Landlord proxyInstance = (Landlord)proxy.getProxyInstance();
        proxyInstance.apartmentToRent();
        System.out.println(proxyInstance.getClass());
    }
}
