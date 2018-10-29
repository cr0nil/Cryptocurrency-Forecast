package com.forecast.app.cryptocurrencyForcast;

public class Cryptocurrency {
    private static final String TAG = "Cryptocurrency";
    double last;
    double low;
    double high;
    double vwap;
    double volume;

    public Cryptocurrency(double last, double low, double high, double vwap, double volume) {
        this.last = last;
        this.low = low;
        this.high = high;
        this.vwap = vwap;
        this.volume = volume;
    }

    public double getLast() {
        return last;
    }

    public void setLast(double last) {
        this.last = last;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getVwap() {
        return vwap;
    }

    public void setVwap(double vwap) {
        this.vwap = vwap;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }
}
