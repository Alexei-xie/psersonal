package com.itself.proxy.staticproxy;


import com.itself.proxy.Landlord;

/**
 * 中介代理商
 */
public class LandlordProxy {

    public Landlord landlord;

    public LandlordProxy(Landlord landlord) {
        this.landlord = landlord;
    }

    public void apartmentToRent() {
        apartmentToRentBefore();
        landlord.apartmentToRent();
        apartmentToRentAfter();
    }

    public void apartmentToRentBefore() {
        System.out.println("出租房前,收取中介费");
    }

    public void apartmentToRentAfter() {
        System.out.println("出租房后,签订合同");
    }
}