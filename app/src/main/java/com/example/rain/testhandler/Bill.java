package com.example.rain.testhandler;

/**
 * Created by rain on 2016/6/7.
 */
public class Bill {
    public Customer customer;
    public float totalMoney;
    public SBill bills[];

    public Bill(Customer c, float t, SBill b[]) {
        customer = c;
        totalMoney = t;
        for(int i = 0; i < b.length; i++) {
            bills[i] = b[i];
        }
    }
}
