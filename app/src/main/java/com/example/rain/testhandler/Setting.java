package com.example.rain.testhandler;

/**
 * Created by rain on 2016/6/7.
 */
public class Setting {
    private float temperature;
    private int speed;
    private String mode;

    public Setting(float t, int s, String m) {
        temperature = t;
        speed = s;
        mode = m;
    }

    public void setTemperature(float temperature) {
        if(temperature >= 18 && temperature <= 30)
        this.temperature = temperature;
    }

    public float getTemperature() {
        return temperature;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        if(speed <= 3 && speed >= 0)
        this.speed = speed;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        if(mode == "off" || mode == "dry" || mode == "purge" )
        this.mode = mode;
    }

    public void temperatureAdd() {
        if(temperature < 30)
        temperature += 0.5;
    }

    public void temperatureSub() {
        if(temperature > 18)
        temperature -= 0.5;
    }

    public void speedAdd() {
        if(speed < 3)
        speed += 1;
    }

    public void speedSub() {
        if(speed > 0)
        speed -= 1;
    }

}
