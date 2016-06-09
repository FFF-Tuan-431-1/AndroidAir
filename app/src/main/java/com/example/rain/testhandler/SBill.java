package com.example.rain.testhandler;

import java.util.Date;

/**
 * Created by rain on 2016/6/7.
 */
public class SBill {
    public Setting setting;
    public Date startTime, endTime;
    public float money;

    public SBill(Setting s, Date a, Date b, float m){
        setting = s;
        startTime = a;
        endTime = b;
        money = m;
    }
}
