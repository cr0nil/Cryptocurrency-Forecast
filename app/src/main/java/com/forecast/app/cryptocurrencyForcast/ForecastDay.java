package com.forecast.app.cryptocurrencyForcast;

public class ForecastDay {

    private double value;
    private String date;

    public ForecastDay(double value, String date) {
        this.value = value;
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
