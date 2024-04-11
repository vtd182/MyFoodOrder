package com.example.myfoodorder.event;

public class PriceUpdateEvent {
    public final String totalString;
    public final int totalPrice;

    public PriceUpdateEvent(String totalString, int totalPrice) {
        this.totalString = totalString;
        this.totalPrice = totalPrice;
    }
}
