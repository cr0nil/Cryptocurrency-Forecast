package com.forecast.app.cryptocurrencyForcast;

public class Cryptocurrency {
    private static final String TAG = "Cryptocurrency";
    double ask;
    double bid;
    double low;
    double high;
    String nameKrypto;

    public Cryptocurrency(double ask, double bid, double low, double high, String nameKrypto) {
        this.ask = ask;
        this.bid = bid;
        this.low = low;
        this.high = high;
        this.nameKrypto = nameKrypto;
    }


    public double getAsk() {
        return ask;
    }

    public void setAsk(double ask) {
        this.ask = ask;
    }

    public double getBid() {
        return bid;
    }

    public void setBid(double bid) {
        this.bid = bid;
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

    public String getNameKrypto() {
        return nameKrypto;
    }

    public void setNameKrypto(String nameKrypto) {
        this.nameKrypto = nameKrypto;
    }
}
