package com.project.aquascaper.data;

public class Sensor {

    private double CO2;
    private String Fan;
    private String LED;
    private String Solenoid;
    private double pH;
    private double suhu;
    private double turbidity;

    public Sensor(){}

    public double getCO2() {
        return CO2;
    }

    public void setCO2(double CO2) {
        this.CO2 = CO2;
    }

    public String getFan() {
        return Fan;
    }

    public void setFan(String fan) {
        Fan = fan;
    }

    public String getLED() {
        return LED;
    }

    public void setLED(String LED) {
        this.LED = LED;
    }

    public String getSolenoid() {
        return Solenoid;
    }

    public void setSolenoid(String solenoid) {
        Solenoid = solenoid;
    }

    public double getpH() {
        return pH;
    }

    public void setpH(double pH) {
        this.pH = pH;
    }

    public double getSuhu() {
        return suhu;
    }

    public void setSuhu(double suhu) {
        this.suhu = suhu;
    }

    public double getTurbidity() {
        return turbidity;
    }

    public void setTurbidity(double turbidity) {
        this.turbidity = turbidity;
    }
}
