package com.iot12369.easylifeandroid;

public class PaymentRequest {
    String channel;
    double totalPrice;
    String title;
    String body;
    User extra;
    long time_expire;

    public PaymentRequest(String channel, double totalPrice, String title, String body, String userid, long time_expire) {
        this.channel = channel;
        this.totalPrice = totalPrice;
        this.title = title;
        this.body = body;
        this.time_expire=time_expire;

        extra = new User(userid);
    }

    public class User {
        String user_id;

        User(String userid) {
            this.user_id = userid;
        }

    }
}
